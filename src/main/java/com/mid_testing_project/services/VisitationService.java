/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.PrisonDao;
import com.mid_testing_project.dao.PrisonerDao;
import com.mid_testing_project.dao.VisitationDao;
import com.mid_testing_project.dao.VisitationInterface;
import com.mid_testing_project.dao.VisitationTimeDao;
import com.mid_testing_project.dao.VisitationTimeInterface;
import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.Prisoner;
import com.mid_testing_project.domain.Visitation;
import com.mid_testing_project.domain.VisitationOccurrenceStatus;
import com.mid_testing_project.domain.VisitationTime;
import com.mid_testing_project.domain.Visitor;
import com.mid_testing_project.exceptions.AlreadyOccurredVisitationException;
import com.mid_testing_project.exceptions.InvalidPrisonException;
import com.mid_testing_project.exceptions.InvalidVisitationDateException;
import com.mid_testing_project.exceptions.InvalidVisitationException;
import com.mid_testing_project.exceptions.InvalidVisitationTimeException;
import com.mid_testing_project.exceptions.InvalidVisitorException;
import com.mid_testing_project.util.EmailUtil;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author regis
 */
public class VisitationService {

    private final VisitationInterface visitationDao = new VisitationDao();
    private final PrisonerDao prisonerDao = new PrisonerDao();
    private final VisitationTimeInterface visitationTimeDao = new VisitationTimeDao();
    private final PrisonDao prisonDao = new PrisonDao();

    public Visitation visit(Visitor visitor,
            String prisonerId,
            LocalDate visitationDate,
            String visitationTimeId) {
        Prisoner prisoner = prisonerDao.findById(prisonerId);
        VisitationTime time = (VisitationTime) visitationTimeDao.findById(visitationTimeId);
        if (visitor == null) {
            throw new InvalidVisitorException("visitor does not exist");
        }
        if (prisoner == null) {
            throw new InvalidVisitorException("prisoner does not exist");
        }
        if (visitationDate.isBefore(LocalDate.now())) {
            throw new InvalidVisitationDateException("visitation date is invalid");
        }
        if (time == null) {
            throw new InvalidVisitationTimeException("time does not exist");
        }
        if (!visitationDate.getDayOfWeek().toString().equals(time.getVisitationDay())) {
            throw new InvalidVisitationDateException("date is not enabled");
        }
        Visitation visitation = new Visitation();
        visitation.setPrisoner(prisoner);
        visitation.setVisitor(visitor);
        visitation.setVisitationDate(visitationDate);
        visitation.setVisitationRequestDate(LocalDate.now());
        visitation.setVisitationTime(time);
        visitation.setOccurrenceStatus(VisitationOccurrenceStatus.NOT_OCCURRED);
        visitationDao.save(visitation);
        if (!visitation.getVisitor().getVisitorEmail().equalsIgnoreCase("")) {
            EmailUtil.sendApprovalEmail(visitation);
        }
        return visitation;
    }

    public Visitation occurVisitation(String visitationId) {
        Visitation visitation = (Visitation) visitationDao.findById(visitationId);
        if (visitation == null) {
            throw new InvalidVisitationException("visitation does not exist");
        }
        if (visitation.getOccurrenceStatus().name().equalsIgnoreCase("OCCURRED")) {
            throw new AlreadyOccurredVisitationException("visitation already occurred");
        }
        visitation.setOccurrenceStatus(VisitationOccurrenceStatus.OCCURRED);
        visitationDao.update(visitation);
        return visitation;
    }

    public Set<Visitation> findAllOccurredVisitations(String prisonId) {
        Prison prison = prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        return visitationDao.findAllByOccurrenceStatus(VisitationOccurrenceStatus.OCCURRED, prison);
    }

    public Set<Visitation> findAllNonOccurredVisitations(String prisonId) {
        Prison prison = prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        return visitationDao.findAllByOccurrenceStatus(VisitationOccurrenceStatus.NOT_OCCURRED, prison);
    }

    public Set<Visitation> findAllTodayVisitations(String prisonId) {
        Prison prison = prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        return visitationDao.findAllByToday(prison);
    }
}
