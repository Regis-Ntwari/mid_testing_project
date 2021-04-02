/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.UserDao;
import com.mid_testing_project.dao.UserStatusDao;
import com.mid_testing_project.domain.User;
import com.mid_testing_project.domain.UserRole;
import com.mid_testing_project.domain.UserStatus;
import com.mid_testing_project.domain.UserWorkingStatus;
import com.mid_testing_project.exceptions.AlreadyStaffFiredException;
import com.mid_testing_project.exceptions.AlreadyStaffSuspendedException;
import com.mid_testing_project.exceptions.InvalidStaffException;
import com.mid_testing_project.interfaces.RepositoryInterface;
import com.mid_testing_project.interfaces.UserRepositoryInterface;
import java.time.LocalDateTime;

/**
 *
 * @author regis
 */
public class ManagerService {
    private final UserRepositoryInterface staffDao = new UserDao();
    private final RepositoryInterface statusDao = new UserStatusDao();
    
    public User addGuard(String managerId, User staff, String comment){
        User manager = (User) staffDao.findById(managerId);
        staff.setStaffRole(UserRole.GUARD);
        staff.setStaffWorkingStatus(UserWorkingStatus.ACTIVE);
        staffDao.save(staff);
        statusDao.save(new UserStatus(manager, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }
    public User suspendGuard(String managerId, String staffId, String comment){
        User manager = (User) staffDao.findById(staffId);
        User staff = (User) staffDao.findById(staffId);
        if(staff == null){
            throw new InvalidStaffException("staff does not exist");
        }
        if(staff.getStaffWorkingStatus().name().equalsIgnoreCase("SUSPENDED")){
            throw new AlreadyStaffSuspendedException("staff is already suspended");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.SUSPENDED);
        staffDao.update(staff);
        statusDao.save(new UserStatus(manager, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }
    public User fireGuard(String managerId, String staffId, String comment){
        User manager = (User) staffDao.findById(managerId);
        User staff = (User) staffDao.findById(staffId);
        if(staff == null){
            throw new InvalidStaffException("staff does not exist");
        }
        if(staff.getStaffWorkingStatus().name().equalsIgnoreCase("FIRED")){
            throw new AlreadyStaffFiredException("staff is already fired");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.FIRED);
        staffDao.update(staff);
        statusDao.save(new UserStatus(manager, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }
}
