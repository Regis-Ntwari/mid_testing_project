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
import java.time.LocalDate;

/**
 *
 * @author regis
 */
public class AdminService {
    private final StaffDao adminDao = new StaffDao();
    
    public Staff addManager(Staff staff){
        staff.setStaffRole(StaffRole.MANAGER);
        staff.setJoinedDate(LocalDate.now());
        staff.setStaffWorkingStatus(StaffWorkingStatus.ACTIVE);
        adminDao.save(staff);
        return staff;
    }
    public Staff fireManager(Staff staff){
        staff.setStaffWorkingStatus(StaffWorkingStatus.FIRED);
        adminDao.update(staff);
        return staff;
    }
    public Staff suspendManager(Staff staff){
        staff.setStaffWorkingStatus(StaffWorkingStatus.SUSPENDED);
        adminDao.update(staff);
        return staff;
    }
}
