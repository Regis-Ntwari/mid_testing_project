/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.VisitationTimeDao;
import com.mid_testing_project.domain.VisitationTime;
import com.mid_testing_project.domain.VisitationTimeStatus;
import com.mid_testing_project.exceptions.AlreadyNotInUseTimeException;
import com.mid_testing_project.exceptions.InvalidVisitationTimeException;
import com.mid_testing_project.interfaces.VisitationTimeInterface;

/**
 *
 * @author regis
 */
public class VisitationTimeService {

    private final VisitationTimeInterface timeDao = new VisitationTimeDao();

    public VisitationTime addVisitationTime(VisitationTime time) {
        time.setVisitationTimeStatus(VisitationTimeStatus.IN_USE);
        time.setVisitationDay(time.getVisitationDay().toUpperCase());
        timeDao.save(time);
        return time;
    }

    public VisitationTime disableVisitationTime(String timeId) {
        VisitationTime time = (VisitationTime) timeDao.findById(timeId);
        if (time == null) {
            throw new InvalidVisitationTimeException("time does not exist");
        }
        if(time.getVisitationTimeStatus().name().equalsIgnoreCase("NOT_IN_USE")){
            throw new AlreadyNotInUseTimeException("time is not in use");
        }
        time.setVisitationTimeStatus(VisitationTimeStatus.NOT_IN_USE);
        timeDao.update(time);
        return time;
    }
}
