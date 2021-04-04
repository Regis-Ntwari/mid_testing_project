/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.HibernateUtilities;
import com.mid_testing_project.domain.User;
import com.mid_testing_project.exceptions.AlreadyStaffFiredException;
import com.mid_testing_project.exceptions.AlreadyStaffSuspendedException;
import com.mid_testing_project.exceptions.InvalidPrisonException;
import com.mid_testing_project.exceptions.InvalidStaffException;
import com.mid_testing_project.exceptions.NotStaffActivatedException;
import com.mid_testing_project.exceptions.NotStaffException;
import com.mid_testing_project.util.CommonOperations;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author regis
 */
public class ManagerServiceNGTest {

    private final ManagerService managerService = new ManagerService();

    public ManagerServiceNGTest() {
    }

    @BeforeTest
    public void initializeDatabase() {
        HibernateUtilities.getSessionFactory();
    }

    @BeforeMethod
    public void populateTables() {
        DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:postgresql://localhost:5432/testing",
                "postgres", "postgres"), CommonOperations.INSERT_DATA);
        dbSetup.launch();
    }

    @Test
    public void testAddGuard() {
        User guard = new User();
        guard.setEmail("niyo@gmail.com");
        guard.setName("NIYO");
        guard.setNationalId("12345678");
        guard.setPassword("NIYO");
        guard.setPhone("0789654321");
        guard.setUsername("NIYO");
        User returnedManager = managerService.addGuard("P-02", guard, "added manager", "PR-01");
        Assert.assertEquals(returnedManager.getVersion(), 1);
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testAddGuardInvalidManager() {
        User guard = new User();
        guard.setEmail("niyo@gmail.com");
        guard.setName("NIYO");
        guard.setNationalId("12345678");
        guard.setPassword("NIYO");
        guard.setPhone("0789654321");
        guard.setUsername("NIYO");
        managerService.addGuard("P-15", guard, "added manager", "PR-01");
    }

    @Test(expectedExceptions = NotStaffException.class)
    public void testAddGuardNotManager() {
        User guard = new User();
        guard.setEmail("niyo@gmail.com");
        guard.setName("NIYO");
        guard.setNationalId("12345678");
        guard.setPassword("NIYO");
        guard.setPhone("0789654321");
        guard.setUsername("NIYO");
        managerService.addGuard("P-05", guard, "added manager", "PR-01");
    }

    @Test(expectedExceptions = NotStaffActivatedException.class)
    public void testAddGuardManagerNotActivated() {
        User guard = new User();
        guard.setEmail("niyo@gmail.com");
        guard.setName("NIYO");
        guard.setNationalId("12345678");
        guard.setPassword("NIYO");
        guard.setPhone("0789654321");
        guard.setUsername("NIYO");
        managerService.addGuard("P-07", guard, "added manager", "PR-01");
    }

    @Test(expectedExceptions = InvalidPrisonException.class)
    public void testAddGuardInvalidPrison() {
        User guard = new User();
        guard.setEmail("niyo@gmail.com");
        guard.setName("NIYO");
        guard.setNationalId("12345678");
        guard.setPassword("NIYO");
        guard.setPhone("0789654321");
        guard.setUsername("NIYO");
        managerService.addGuard("P-02", guard, "added manager", "PR-02");
    }

    @Test
    public void testSuspendGuard() {
        User guard = managerService.suspendGuard("P-02", "P-01", "suspend guard");
        Assert.assertEquals(guard.getVersion(), 2);
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testSuspendGuardInvalidManager() {
        managerService.suspendGuard("P-14", "P-01", "suspend guard");
    }

    @Test(expectedExceptions = NotStaffException.class)
    public void testSuspendGuardNotManager() {
        managerService.suspendGuard("P-05", "P-01", "suspend guard");
    }

    @Test(expectedExceptions = NotStaffActivatedException.class)
    public void testSuspendGuardManagerNotActivated() {
        managerService.suspendGuard("P-07", "P-01", "suspend guard");
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testSuspendGuardInvalidGuard() {
        managerService.suspendGuard("P-02", "P-020", "suspend guard");
    }

    @Test(expectedExceptions = AlreadyStaffSuspendedException.class)
    public void testSuspendGuardAlreadySuspendedGuard() {
        managerService.suspendGuard("P-02", "P-11", "suspend guard");
    }

    @Test
    public void testFireGuard() {
        User guard = managerService.fireGuard("P-02", "P-01", "fired guard");
        Assert.assertEquals(guard.getVersion(), 2);
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testFireGuardInvalidManager() {
        managerService.fireGuard("P-14", "P-01", "fired guard");
    }

    @Test(expectedExceptions = NotStaffException.class)
    public void testFireGuardNotManager() {
        managerService.fireGuard("P-05", "P-01", "fired guard");
    }

    @Test(expectedExceptions = NotStaffActivatedException.class)
    public void testFireGuardManagerNotActivated() {
        managerService.fireGuard("P-07", "P-01", "fired guard");
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testFireGuardInvalidGuard() {
        managerService.fireGuard("P-02", "P-20", "fired guard");
    }
    @Test(expectedExceptions = AlreadyStaffFiredException.class)
    public void testFireGuardAlreadyFiredGuard(){
        managerService.fireGuard("P-02", "P-12", "fired guard");
    }

    @AfterMethod
    public void cleanTables() {
        DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:postgresql://localhost:5432/testing",
                "postgres", "postgres"), CommonOperations.DELETE_DATA);
        dbSetup.launch();
    }
}
