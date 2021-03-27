/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.PrisonDao;
import com.mid_testing_project.dao.PrisonerDao;
import com.mid_testing_project.dao.VisitationDao;
import com.mid_testing_project.dao.VisitationTimeDao;
import com.mid_testing_project.dao.VisitorDao;
import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.Prisoner;
import com.mid_testing_project.domain.Visitation;
import com.mid_testing_project.domain.VisitationOccurrenceStatus;
import com.mid_testing_project.domain.VisitationRequestStatus;
import com.mid_testing_project.domain.VisitationTime;
import com.mid_testing_project.domain.Visitor;
import com.mid_testing_project.exceptions.AlreadyApprovedVisitationException;
import com.mid_testing_project.exceptions.AlreadyCancelledVisitationException;
import com.mid_testing_project.exceptions.AlreadyOccurredVisitationException;
import com.mid_testing_project.exceptions.InvalidPrisonException;
import com.mid_testing_project.exceptions.InvalidVisitationDateException;
import com.mid_testing_project.exceptions.InvalidVisitationException;
import com.mid_testing_project.exceptions.InvalidVisitorException;
import com.mid_testing_project.util.EmailUtil;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author regis
 */
public class VisitationService {

    private final VisitationDao visitationDao = new VisitationDao();
    private final VisitorDao visitorDao = new VisitorDao();
    private final PrisonerDao prisonerDao = new PrisonerDao();
    private final VisitationTimeDao visitationTimeDao = new VisitationTimeDao();
    private final PrisonDao prisonDao = new PrisonDao();

    public Visitation visit(String visitorId, String prisonerId, LocalDate visitationDate) {
        Visitor visitor = visitorDao.findById(visitorId);
        Prisoner prisoner = prisonerDao.findById(prisonerId);
        VisitationTime time = visitationTimeDao.findInUseTime();
        if (visitor == null) {
            throw new InvalidVisitorException("visitor does not exist");
        }
        if (prisoner == null) {
            throw new InvalidVisitorException("prisoner does not exist");
        }
        if (visitationDate.isBefore(LocalDate.now())) {
            throw new InvalidVisitationDateException("visitation date is invalid");
        }
        Visitation visitation = new Visitation();
        visitation.setPrisoner(prisoner);
        visitation.setVisitor(visitor);
        visitation.setVisitationDate(visitationDate);
        visitation.setVisitationRequestDate(LocalDate.now());
        visitation.setVisitationTime(time);
        visitation.setOccurrenceStatus(VisitationOccurrenceStatus.NOT_OCCURRED);
        visitation.setRequestStatus(VisitationRequestStatus.PENDING);
        visitationDao.save(visitation);
        EmailUtil.sendAcceptanceEmail(visitation);
        return visitation;

    }

    public Visitation cancelVisitation(String visitationId) {
        Visitation visitation = visitationDao.findById(visitationId);
        if (visitation == null) {
            throw new InvalidVisitationException("visitation does not exist");
        }
        if (visitation.getRequestStatus().name().equalsIgnoreCase("CANCELLED")) {
            throw new AlreadyCancelledVisitationException("visitation already cancelled");
        }
        visitation.setRequestStatus(VisitationRequestStatus.CANCELLED);
        visitationDao.update(visitation);
        EmailUtil.sendDenialEmail(visitation);
        return visitation;
    }

    public Visitation approveVisitation(String visitationId) {
        Visitation visitation = visitationDao.findById(visitationId);
        if (visitation == null) {
            throw new InvalidVisitationException("visitation does not exist");
        }
        if (visitation.getRequestStatus().name().equalsIgnoreCase("APPROVED")) {
            throw new AlreadyApprovedVisitationException("Already approved visitation");
        }
        visitation.setRequestStatus(VisitationRequestStatus.APPROVED);
        visitationDao.update(visitation);
        EmailUtil.sendApprovalEmail(visitation);
        return visitation;
    }

    public Visitation occurVisitation(String visitationId) {
        Visitation visitation = visitationDao.findById(visitationId);
        if (visitation == null) {
            throw new InvalidVisitationException("visitation does not exist");
        }
        if (visitation.getOccurrenceStatus().name().equalsIgnoreCase("OCCURRED")) {
            throw new AlreadyOccurredVisitationException("visitation already occurred");
        }
        visitation.setRequestStatus(VisitationRequestStatus.APPROVED);
        visitationDao.update(visitation);
        return visitation;
    }

    public Set<Visitation> findAllOccurredVisitations(String prisonId) {
        Prison prison = prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        return visitationDao.findAllByOccurenceStatus(VisitationOccurrenceStatus.OCCURRED, prison);
    }

    public Set<Visitation> findAllNonOccurredVisitations(String prisonId) {
        Prison prison = prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        return visitationDao.findAllByOccurenceStatus(VisitationOccurrenceStatus.NOT_OCCURRED, prison);
    }

    public Set<Visitation> findAllPendingVisitations(String prisonId) {
        Prison prison = prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        return visitationDao.findAllByRequestStatus(VisitationRequestStatus.PENDING, prison);
    }

    public Set<Visitation> findAllApprovedVisitations(String prisonId) {
        Prison prison = prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        return visitationDao.findAllByRequestStatus(VisitationRequestStatus.APPROVED, prison);
    }

    public Set<Visitation> findAllCancelledVisitations(String prisonId) {
        Prison prison = prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        return visitationDao.findAllByRequestStatus(VisitationRequestStatus.CANCELLED, prison);
    }

    public Set<Visitation> findAllTodayVisitations(String prisonId) {
        Prison prison = prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        return visitationDao.findAllByToday(prison);
    }
}
