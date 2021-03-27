/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.StaffDao;
import com.mid_testing_project.domain.Staff;
import com.mid_testing_project.domain.StaffRole;
import com.mid_testing_project.domain.StaffWorkingStatus;
import com.mid_testing_project.exceptions.AlreadyStaffFiredException;
import com.mid_testing_project.exceptions.AlreadyStaffSuspendedException;
import com.mid_testing_project.exceptions.InvalidStaffException;

/**
 *
 * @author regis
 */
public class ManagerService {
    private final StaffDao staffDao = new StaffDao();
    
    public Staff addGuard(Staff staff){
        staff.setStaffRole(StaffRole.GUARD);
        staff.setStaffWorkingStatus(StaffWorkingStatus.ACTIVE);
        staffDao.save(staff);
        return staff;
    }
    public Staff suspendGuard(String staffId){
        Staff staff = staffDao.findById(staffId);
        if(staff == null){
            throw new InvalidStaffException("staff does not exist");
        }
        if(staff.getStaffWorkingStatus().name().equalsIgnoreCase("SUSPENDED")){
            throw new AlreadyStaffSuspendedException("staff is already suspended");
        }
        staff.setStaffWorkingStatus(StaffWorkingStatus.SUSPENDED);
        staffDao.update(staff);
        return staff;
    }
    public Staff fireGuard(String staffId){
        Staff staff = staffDao.findById(staffId);
        if(staff == null){
            throw new InvalidStaffException("staff does not exist");
        }
        if(staff.getStaffWorkingStatus().name().equalsIgnoreCase("FIRED")){
            throw new AlreadyStaffFiredException("staff is already fired");
        }
        staff.setStaffWorkingStatus(StaffWorkingStatus.FIRED);
        staffDao.update(staff);
        return staff;
    }
}
