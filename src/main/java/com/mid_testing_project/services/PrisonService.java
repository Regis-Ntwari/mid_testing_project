/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.PrisonDao;
import com.mid_testing_project.dao.PrisonerDao;
import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.Prisoner;
import com.mid_testing_project.domain.PrisonerStatus;
import com.mid_testing_project.exceptions.AlreadyReleasedPrisonerException;
import com.mid_testing_project.exceptions.InvalidPrisonException;
import com.mid_testing_project.exceptions.InvalidPrisonerException;
import java.util.Set;

/**
 *
 * @author regis
 */
public class PrisonService {
    private final PrisonerDao prisonerDao = new PrisonerDao();
    private final PrisonDao prisonDao = new PrisonDao();
    
    public Prisoner addPrisoner(Prisoner prisoner){
        prisoner.setPrisonerStatus(PrisonerStatus.INCARCERATED);
        prisonerDao.save(prisoner);
        return prisoner;
    }
    public Prisoner releasePrisoner(String prisonerId){
        Prisoner prisoner = prisonerDao.findById(prisonerId);
        if(prisoner == null){
            throw new InvalidPrisonerException("prisoner does not exist");
        }
        if(prisoner.getPrisonerStatus().name().equalsIgnoreCase("RELEASED")){
            throw new AlreadyReleasedPrisonerException("This prisoner is already released");
        }
        prisoner.setPrisonerStatus(PrisonerStatus.RELEASED);
        prisonerDao.update(prisoner);
        return prisoner;
    }
    public Set<Prisoner> findAllPrisoners(String prisonId){
        Prison prison = prisonDao.findById(prisonId);
        if(prison == null){
            throw new InvalidPrisonException("prison does not exist");
        }
        return prisonerDao.findAllPrisonersInPrison(prison);
    }
}
