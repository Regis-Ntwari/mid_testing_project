/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.PrisonerStatusTrackDao;
import com.mid_testing_project.dao.UserDao;
import com.mid_testing_project.dao.UserStatusDao;
import com.mid_testing_project.interfaces.UserRepositoryInterface;
import com.mid_testing_project.domain.User;
import com.mid_testing_project.domain.UserRole;
import com.mid_testing_project.domain.UserStatus;
import com.mid_testing_project.domain.UserWorkingStatus;
import com.mid_testing_project.exceptions.AlreadyStaffFiredException;
import com.mid_testing_project.exceptions.InvalidStaffException;
import com.mid_testing_project.interfaces.RepositoryInterface;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 *
 * @author regis
 */
public class AdminService {
    private final UserRepositoryInterface adminDao = new UserDao();
    private final RepositoryInterface statusDao = new UserStatusDao();
    private final RepositoryInterface prisonerStatusTrackDao = new PrisonerStatusTrackDao();
    
    public User addManager(String adminId, String userId, String comment){
        User admin = (User) adminDao.findById(adminId);
        User staff = (User) adminDao.findByUsername(userId);
        if(staff == null){
            throw new InvalidStaffException("invalid user");
        }
        staff.setStaffRole(UserRole.MANAGER);
        staff.setJoinedDate(LocalDate.now());
        staff.setStaffWorkingStatus(UserWorkingStatus.ACTIVE);
        adminDao.save(staff);
        statusDao.save(new UserStatus(admin, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }
    public User fireManager(String userId, String comment, String adminId){
        User admin = (User) adminDao.findById(adminId);
        User staff = (User) adminDao.findByUsername(userId);
        if(staff == null){
            throw new InvalidStaffException("invalid user");
        }
        if(staff.getStaffWorkingStatus().name().equalsIgnoreCase("FIRED")){
            throw new AlreadyStaffFiredException("user already fired");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.FIRED);
        adminDao.update(staff);
        statusDao.save(new UserStatus(admin, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }
    public User suspendManager(String adminId, String userId, String comment){
        User admin = (User) adminDao.findById(adminId);
        User staff = (User) adminDao.findById(userId);
        if(staff == null){
            throw new InvalidStaffException("invalid user");
        }
        if(staff.getStaffWorkingStatus().name().equalsIgnoreCase("SUSPENDED")){
            throw new AlreadyStaffFiredException("user already fired");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.SUSPENDED);
        adminDao.update(staff);
        statusDao.save(new UserStatus(admin, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }
    public Set<UserStatus> findAllPrisonerTrack(){
        return prisonerStatusTrackDao.findAll();
    }
    public Set<UserStatus> findAllUserTrack(){
        return statusDao.findAll();
    }
}
