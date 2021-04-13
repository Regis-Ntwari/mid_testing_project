/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.PrisonDao;
import com.mid_testing_project.dao.PrisonerDao;
import com.mid_testing_project.dao.VisitationDao;
import com.mid_testing_project.interfaces.VisitationInterface;
import com.mid_testing_project.dao.VisitationTimeDao;
import com.mid_testing_project.interfaces.VisitationTimeInterface;
import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.Prisoner;
import com.mid_testing_project.domain.Visitation;
import com.mid_testing_project.domain.VisitationTime;
import com.mid_testing_project.domain.Visitor;
import com.mid_testing_project.exceptions.DateNotYetOccurredException;
import com.mid_testing_project.exceptions.InvalidPrisonException;
import com.mid_testing_project.exceptions.InvalidPrisonerException;
import com.mid_testing_project.exceptions.InvalidVisitationDateException;
import com.mid_testing_project.exceptions.OutOfBoundsVisitationTimeException;
import com.mid_testing_project.interfaces.PrisonerDaoInterface;
import com.mid_testing_project.interfaces.RepositoryInterface;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 *
 * @author regis
 */
public class VisitationService {

    private final VisitationInterface visitationDao = new VisitationDao();
    private final PrisonerDaoInterface prisonerDao = new PrisonerDao();
    private final VisitationTimeInterface visitationTimeDao = new VisitationTimeDao();
    private final RepositoryInterface prisonDao = new PrisonDao();

    public Visitation visit(Visitor visitor,
            String prisonerId) {
        Prisoner prisoner = (Prisoner) prisonerDao.findById(prisonerId);
        VisitationTime time = (VisitationTime) visitationTimeDao.findByInUseTime(prisoner.getPrison());
        if (prisoner == null) {
            throw new InvalidPrisonerException("prisoner does not exist");
        }
        LocalDateTime startTime = LocalDateTime.parse(
                LocalDate.now().toString() + "T" + time.getVisitStartTime());
        LocalDateTime endTime = LocalDateTime.parse(
                LocalDate.now().toString() + "T" + time.getVisitEndTime());
        if (!LocalDate.now().getDayOfWeek().toString().equals(time.getVisitationDay())) {
            throw new InvalidVisitationDateException("date is not enabled");
        }
        if (LocalDateTime.now().isAfter(endTime) || LocalDateTime.now().isBefore(startTime)) {
            throw new OutOfBoundsVisitationTimeException("time is out of boundaries");
        }
        Visitation visitation = new Visitation();
        visitation.setPrisoner(prisoner);
        visitation.setVisitor(visitor);
        visitation.setVisitationDate(LocalDate.now());
        visitation.setVisitationTime(time);
        visitationDao.save(visitation);
        return visitation;
    }

    public Set<Visitation> findAllVisitations(String prisonId) {
        Prison prison = (Prison) prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        return visitationDao.findAllVisitations(prison);
    }

    public Set<Visitation> findAllVisitationsOnCertainDate(LocalDate date, String prisonId) {
        Prison prison = (Prison) prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new DateNotYetOccurredException("date not yet occurred");
        }
        return visitationDao.findAllVisitationsByCertainDate(date, prison);
    }
}
