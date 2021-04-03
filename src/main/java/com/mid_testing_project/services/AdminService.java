/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.PrisonDao;
import com.mid_testing_project.dao.PrisonerStatusTrackDao;
import com.mid_testing_project.dao.UserDao;
import com.mid_testing_project.dao.UserStatusDao;
import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.PrisonerStatusTrack;
import com.mid_testing_project.interfaces.UserRepositoryInterface;
import com.mid_testing_project.domain.User;
import com.mid_testing_project.domain.UserRole;
import com.mid_testing_project.domain.UserStatus;
import com.mid_testing_project.domain.UserWorkingStatus;
import com.mid_testing_project.exceptions.AlreadyStaffFiredException;
import com.mid_testing_project.exceptions.InvalidPrisonException;
import com.mid_testing_project.exceptions.InvalidStaffException;
import com.mid_testing_project.exceptions.NotAdminException;
import com.mid_testing_project.exceptions.NotStaffActivatedException;
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
    private final RepositoryInterface prisonDao = new PrisonDao();
    
    public User addManager(String adminId, User staff, String comment, String prisonId) {
        Prison prison = (Prison) prisonDao.findById(prisonId);
        User admin = (User) adminDao.findById(adminId);
        if(admin == null){
            throw new InvalidStaffException("invalid user");
        }
        if(prison == null){
            throw new InvalidPrisonException("invalid prison");
        }
        if(!admin.getStaffRole().toString().equals("ADMIN")){
            throw new NotAdminException("not admin");
        }
        if(!admin.getStaffWorkingStatus().toString().equals("ACTIVATED")){
            throw new NotStaffActivatedException("staff not activated");
        }
        staff.setStaffRole(UserRole.MANAGER);
        staff.setJoinedDate(LocalDate.now());
        staff.setStaffWorkingStatus(UserWorkingStatus.ACTIVATED);
        staff.setPrison(prison);
        adminDao.save(staff);
        statusDao.save(new UserStatus(admin, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }

    public User fireManager(String userId, String comment, String adminId) {
        User admin = (User) adminDao.findById(adminId);
        User staff = (User) adminDao.findById(userId);
        if(admin == null){
            throw new InvalidStaffException("invalid admin");
        }
        if (staff == null) {
            throw new InvalidStaffException("invalid user");
        }
        if (staff.getStaffWorkingStatus().name().equalsIgnoreCase("FIRED")) {
            throw new AlreadyStaffFiredException("user already fired");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.FIRED);
        adminDao.update(staff);
        statusDao.save(new UserStatus(admin, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }

    public User suspendManager(String adminId, String userId, String comment) {
        User admin = (User) adminDao.findById(adminId);
        User staff = (User) adminDao.findById(userId);
        if (staff == null) {
            throw new InvalidStaffException("invalid user");
        }
        if (staff.getStaffWorkingStatus().name().equalsIgnoreCase("SUSPENDED")) {
            throw new AlreadyStaffFiredException("user already fired");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.SUSPENDED);
        adminDao.update(staff);
        statusDao.save(new UserStatus(admin, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }

    public Set<PrisonerStatusTrack> findAllPrisonerTrack() {
        return prisonerStatusTrackDao.findAll();
    }

    public Set<UserStatus> findAllUserTrack() {
        return statusDao.findAll();
    }
}
