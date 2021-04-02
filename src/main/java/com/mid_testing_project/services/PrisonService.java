/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.PrisonDao;
import com.mid_testing_project.dao.PrisonerDao;
import com.mid_testing_project.dao.PrisonerStatusTrackDao;
import com.mid_testing_project.interfaces.RepositoryInterface;
import com.mid_testing_project.dao.UserDao;
import com.mid_testing_project.interfaces.UserRepositoryInterface;
import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.Prisoner;
import com.mid_testing_project.domain.PrisonerStatus;
import com.mid_testing_project.domain.PrisonerStatusTrack;
import com.mid_testing_project.domain.User;
import com.mid_testing_project.exceptions.AlreadyReleasedPrisonerException;
import com.mid_testing_project.exceptions.InvalidPrisonException;
import com.mid_testing_project.exceptions.InvalidPrisonerException;
import com.mid_testing_project.exceptions.InvalidStaffException;
import com.mid_testing_project.interfaces.PrisonerDaoInterface;
import java.time.LocalDateTime;
import java.util.Set;

/**
 *
 * @author regis
 */
public class PrisonService {

    private final PrisonerDaoInterface prisonerDao = new PrisonerDao();
    private final RepositoryInterface prisonDao = new PrisonDao();
    private final RepositoryInterface prisonerTrackDao = new PrisonerStatusTrackDao();
    private final UserRepositoryInterface userDao = new UserDao();

    public Prisoner addPrisoner(String userId, Prisoner prisoner, String comment) {
        User staff = (User) userDao.findById(userId);
        if (staff == null) {
            throw new InvalidStaffException("staff does not exist");
        }
        prisoner.setPrisonerStatus(PrisonerStatus.INCARCERATED);
        Prisoner p = (Prisoner) prisonerDao.save(prisoner);
        prisonerTrackDao.save(
                new PrisonerStatusTrack(
                        prisoner,
                        prisoner.getPrisonerStatus(),
                        staff,
                        LocalDateTime.MIN,
                        comment));
        return p;
    }

    public Prisoner releasePrisoner(String userId, String prisonerId, String comment) {
        User staff = (User) userDao.findById(userId);
        Prisoner prisoner = (Prisoner) prisonerDao.findById(prisonerId);
        if (prisoner == null) {
            throw new InvalidPrisonerException("prisoner does not exist");
        }
        if (staff == null) {
            throw new InvalidStaffException("invalid user id");
        }
        if (prisoner.getPrisonerStatus().name().equalsIgnoreCase("RELEASED")) {
            throw new AlreadyReleasedPrisonerException("This prisoner is already released");
        }
        prisoner.setPrisonerStatus(PrisonerStatus.RELEASED);
        prisonerDao.update(prisoner);
        prisonerTrackDao.save(
                new PrisonerStatusTrack(
                        prisoner,
                        prisoner.getPrisonerStatus(),
                        staff,
                        LocalDateTime.MIN,
                        comment));
        return prisoner;
    }

    public Set<Prisoner> findAllPrisonersInCertainPrison(String prisonId) {
        Prison prison = (Prison) prisonDao.findById(prisonId);
        if (prison == null) {
            throw new InvalidPrisonException("prison does not exist");
        }
        return prisonerDao.findAllPrisonersInPrison(prison);
    }

    public Set<Prisoner> findAllPrisoners() {
        return prisonerDao.findAll();
    }
}
