/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.PrisonDao;
import com.mid_testing_project.dao.UserDao;
import com.mid_testing_project.dao.UserStatusDao;
import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.User;
import com.mid_testing_project.domain.UserRole;
import com.mid_testing_project.domain.UserStatus;
import com.mid_testing_project.domain.UserWorkingStatus;
import com.mid_testing_project.exceptions.AlreadyStaffFiredException;
import com.mid_testing_project.exceptions.AlreadyStaffSuspendedException;
import com.mid_testing_project.exceptions.InvalidPrisonException;
import com.mid_testing_project.exceptions.InvalidStaffException;
import com.mid_testing_project.exceptions.NotStaffActivatedException;
import com.mid_testing_project.exceptions.NotStaffException;
import com.mid_testing_project.interfaces.RepositoryInterface;
import com.mid_testing_project.interfaces.UserRepositoryInterface;
import java.time.LocalDateTime;
import java.util.Set;

/**
 *
 * @author regis
 */
public class ManagerService {

    private final UserRepositoryInterface staffDao = new UserDao();
    private final RepositoryInterface statusDao = new UserStatusDao();
    private final RepositoryInterface prisonDao = new PrisonDao();
    
    public User addGuard(String managerId, User staff, String comment, String prisonId) {
        User manager = (User) staffDao.findById(managerId);
        Prison prison = (Prison) prisonDao.findById(prisonId);
        if (manager == null) {
            throw new InvalidStaffException("invalid manager");
        }
        if (!manager.getStaffRole().toString().equals("MANAGER")) {
            throw new NotStaffException("user not manager");
        }
        if (!manager.getStaffWorkingStatus().toString().equals("ACTIVATED")) {
            throw new NotStaffActivatedException("user not activated");
        }
        if (prison == null) {
            throw new InvalidPrisonException("invalid prison");
        }
        staff.setStaffRole(UserRole.GUARD);
        staff.setStaffWorkingStatus(UserWorkingStatus.ACTIVATED);
        staff.setPrison(prison);
        staffDao.save(staff);
        statusDao.save(new UserStatus(manager, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }

    public User suspendGuard(String managerId, String staffId, String comment) {
        User manager = (User) staffDao.findById(managerId);
        User staff = (User) staffDao.findById(staffId);
        if (manager == null) {
            throw new InvalidStaffException("invalid manager");
        }
        if (!manager.getStaffRole().toString().equals("MANAGER")) {
            throw new NotStaffException("user not manager");
        }
        if (!manager.getStaffWorkingStatus().toString().equals("ACTIVATED")) {
            throw new NotStaffActivatedException("user not activated");
        }
        if (staff == null) {
            throw new InvalidStaffException("staff does not exist");
        }
        if (staff.getStaffWorkingStatus().name().equalsIgnoreCase("SUSPENDED")) {
            throw new AlreadyStaffSuspendedException("staff is already suspended");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.SUSPENDED);
        staffDao.update(staff);
        statusDao.save(new UserStatus(manager, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }

    public User fireGuard(String managerId, String staffId, String comment) {
        User manager = (User) staffDao.findById(managerId);
        User staff = (User) staffDao.findById(staffId);
        if (manager == null) {
            throw new InvalidStaffException("invalid manager");
        }
        if (!manager.getStaffRole().toString().equals("MANAGER")) {
            throw new NotStaffException("user not manager");
        }
        if (!manager.getStaffWorkingStatus().toString().equals("ACTIVATED")) {
            throw new NotStaffActivatedException("user not activated");
        }
        if (staff == null) {
            throw new InvalidStaffException("staff does not exist");
        }
        if (staff.getStaffWorkingStatus().name().equalsIgnoreCase("FIRED")) {
            throw new AlreadyStaffFiredException("staff is already fired");
        }
        staff.setStaffWorkingStatus(UserWorkingStatus.FIRED);
        staffDao.update(staff);
        statusDao.save(new UserStatus(manager, staff, comment, LocalDateTime.now(), staff.getStaffRole(), staff.getStaffWorkingStatus()));
        return staff;
    }
    public Set<User> findAllActiveGuards(String prisonId){
        Prison prison = (Prison) prisonDao.findById(prisonId);
        return staffDao.findStaffByStaffRoleInCertainPrison(UserRole.GUARD, UserWorkingStatus.ACTIVATED, prison);
    }
    public Set<User> findAllFiredGuards(String prisonId){
        Prison prison = (Prison) prisonDao.findById(prisonId);
        return staffDao.findStaffByStaffRoleInCertainPrison(UserRole.GUARD, UserWorkingStatus.FIRED, prison);
    }
    public Set<User> findAllSuspendedGuards(String prisonId){
        Prison prison = (Prison) prisonDao.findById(prisonId);
        return staffDao.findStaffByStaffRoleInCertainPrison(UserRole.GUARD, UserWorkingStatus.SUSPENDED, prison);
    }
}
