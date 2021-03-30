/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.StaffDao;
import com.mid_testing_project.domain.User;
import com.mid_testing_project.domain.UserRole;
import com.mid_testing_project.domain.UserWorkingStatus;
import com.mid_testing_project.exceptions.AlreadyStaffFiredException;
import com.mid_testing_project.exceptions.AlreadyStaffSuspendedException;
import com.mid_testing_project.exceptions.InvalidStaffException;

/**
 *
 * @author regis
 */
public class ManagerService {
    private final StaffDao staffDao = new StaffDao();
    
    public User addGuard(User staff){
        staff.setStaffRole(UserRole.GUARD);
        staff.setStaffWorkingStatus(UserWorkingStatus.ACTIVE);
        staffDao.save(staff);
        return staff;
    }
    public User suspendGuard(String staffId){
        User staff = staffDao.findById(staffId);
        if(staff == null){
            throw new InvalidStaffException("staff does not exist");
        }
        if(staff.getStaffWorkingStatus().name().equalsIgnoreCase("SUSPENDED")){
            throw new AlreadyStaffSuspendedException("staff is already suspended");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.SUSPENDED);
        staffDao.update(staff);
        return staff;
    }
    public User fireGuard(String staffId){
        User staff = staffDao.findById(staffId);
        if(staff == null){
            throw new InvalidStaffException("staff does not exist");
        }
        if(staff.getStaffWorkingStatus().name().equalsIgnoreCase("FIRED")){
            throw new AlreadyStaffFiredException("staff is already fired");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.FIRED);
        staffDao.update(staff);
        return staff;
    }
}
