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
import com.mid_testing_project.exceptions.InvalidStaffException;
import java.time.LocalDate;

/**
 *
 * @author regis
 */
public class AdminService {
    private final StaffDao adminDao = new StaffDao();
    
    public User addManager(String userId){
        User staff = adminDao.findByUsername(userId);
        if(staff == null){
            throw new InvalidStaffException("invalid user");
        }
        staff.setStaffRole(UserRole.MANAGER);
        staff.setJoinedDate(LocalDate.now());
        staff.setStaffWorkingStatus(UserWorkingStatus.ACTIVE);
        adminDao.save(staff);
        return staff;
    }
    public User fireManager(String userId){
        User staff = adminDao.findByUsername(userId);
        if(staff == null){
            throw new InvalidStaffException("invalid user");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.FIRED);
        adminDao.update(staff);
        return staff;
    }
    public User suspendManager(String userId){
        User staff = adminDao.findByUsername(userId);
        if(staff == null){
            throw new InvalidStaffException("invalid user");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.SUSPENDED);
        adminDao.update(staff);
        return staff;
    }
}
