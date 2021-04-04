/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.HibernateUtilities;
import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.PrisonerStatusTrack;
import com.mid_testing_project.domain.User;
import com.mid_testing_project.domain.UserStatus;
import com.mid_testing_project.exceptions.AlreadyStaffFiredException;
import com.mid_testing_project.exceptions.AlreadyStaffSuspendedException;
import com.mid_testing_project.exceptions.InvalidPrisonException;
import com.mid_testing_project.exceptions.InvalidStaffException;
import com.mid_testing_project.exceptions.NotStaffException;
import com.mid_testing_project.exceptions.NotStaffActivatedException;
import com.mid_testing_project.util.CommonOperations;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import java.util.Set;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author regis
 */
public class AdminServiceNGTest {

    private final AdminService adminService = new AdminService();

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

    public AdminServiceNGTest() {
    }

    @Test
    public void testAddManager() {
        User manager = new User();
        manager.setEmail("niyo@gmail.com");
        manager.setName("NIYO");
        manager.setNationalId("12345678");
        manager.setPassword("NIYO");
        manager.setPhone("0789654321");
        manager.setUsername("NIYO");
        User returnedManager = adminService.addManager("P-05", manager, "added manager", "PR-01");
        Assert.assertEquals(returnedManager.getVersion(), 1);
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testAddManagerInvalidAdmin() {
        User manager = new User();
        manager.setEmail("niyo@gmail.com");
        manager.setName("NIYO");
        manager.setNationalId("12345678");
        manager.setPassword("NIYO");
        manager.setPhone("0789654321");
        manager.setUsername("NIYO");
        adminService.addManager("P-09", manager, "added manager", "PR-01");
    }

    @Test(expectedExceptions = InvalidPrisonException.class)
    public void testAddManagerInvalidPrison() {
        User manager = new User();
        manager.setEmail("niyo@gmail.com");
        manager.setName("NIYO");
        manager.setNationalId("12345678");
        manager.setPassword("NIYO");
        manager.setPhone("0789654321");
        manager.setUsername("NIYO");
        adminService.addManager("P-05", manager, "added manager", "PR-02");
    }

    @Test(expectedExceptions = NotStaffException.class)
    public void testAddManagerNotAdmin() {
        User manager = new User();
        manager.setEmail("niyo@gmail.com");
        manager.setName("NIYO");
        manager.setNationalId("12345678");
        manager.setPassword("NIYO");
        manager.setPhone("0789654321");
        manager.setUsername("NIYO");
        adminService.addManager("P-02", manager, "added manager", "PR-01");
    }

    @Test(expectedExceptions = NotStaffActivatedException.class)
    public void testAddManagerAdminNotActivated() {
        User manager = new User();
        manager.setEmail("niyo@gmail.com");
        manager.setName("NIYO");
        manager.setNationalId("12345678");
        manager.setPassword("NIYO");
        manager.setPhone("0789654321");
        manager.setUsername("NIYO");
        adminService.addManager("P-06", manager, "added manager", "PR-01");
    }

    @Test
    public void testFireManager() {
        User firedManager = adminService.fireManager("P-02", "fired manager", "P-05");
        Assert.assertEquals(firedManager.getVersion(), 2);
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testFireManagerInvalidAdmin() {
        adminService.fireManager("P-02", "fired manager", "P-08");
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testFireManagerInvalidManager() {
        adminService.fireManager("P-08", "fired manager", "P-05");
    }

    @Test(expectedExceptions = NotStaffActivatedException.class)
    public void testFireManagerAdminNotActivated() {
        adminService.fireManager("P-02", "fired manager", "P-06");
    }

    @Test(expectedExceptions = NotStaffException.class)
    public void testFireManagerNotAdmin() {
        adminService.fireManager("P-02", "fired manager", "P-02");
    }

    @Test(expectedExceptions = AlreadyStaffFiredException.class)
    public void testFireManagerAlreadyFired() {
        adminService.fireManager("P-07", "fired manager", "P-05");
    }

    @Test
    public void testSuspendManager() {
        User suspendedManager = adminService.suspendManager("P-05", "P-02", "suspended manager");
        Assert.assertEquals(suspendedManager.getVersion(), 2);
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testSuspendManagerInvalidAdmin() {
        adminService.suspendManager("P-08", "P-02", "suspended manager");
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testSuspendManagerInvalidManager() {
        adminService.suspendManager("P-05", "P-09", "suspended manager");
    }

    @Test(expectedExceptions = NotStaffActivatedException.class)
    public void testSuspendManagerAdminNotActivated() {
        adminService.suspendManager("P-06", "P-02", "suspended manager");
    }

    @Test(expectedExceptions = NotStaffException.class)
    public void testSuspendManagerNotAdmin() {
        adminService.suspendManager("P-01", "P-02", "suspended manager");
    }

    @Test(expectedExceptions = AlreadyStaffSuspendedException.class)
    public void testSuspendManagerAlreadySuspended() {
        adminService.suspendManager("P-05", "P-10", "suspended manager");
    }

    @Test
    public void testAddPrison() {
        Prison prison = new Prison();
        prison.setName("GUATANAMO");
        Prison p = adminService.addPrison(prison, "P-05");
        Assert.assertEquals(p.getVersion(), 1);
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testAddPrisonInvalidAdmin() {
        Prison prison = new Prison();
        prison.setName("GUATANAMO");
        adminService.addPrison(prison, "P-20");
    }

    @Test(expectedExceptions = NotStaffException.class)
    public void testAddPrisonNotAdmin() {
        Prison prison = new Prison();
        prison.setName("GUATANAMO");
        adminService.addPrison(prison, "P-02");
    }

    @Test(expectedExceptions = NotStaffActivatedException.class)
    public void testAddPrisonAdminNotActivated() {
        Prison prison = new Prison();
        prison.setName("GUATANAMO");
        adminService.addPrison(prison, "P-06");
    }

    @Test
    public void testFindAllPrisonerTrack() {
        Set<PrisonerStatusTrack> tracks = adminService.findAllPrisonerTrack();
        Assert.assertEquals(tracks.size(), 0);
    }

    @Test
    public void testFindAllUserTrack() {
        Set<UserStatus> tracks = adminService.findAllUserTrack();
        Assert.assertEquals(tracks.size(), 0);
    }

    @AfterMethod
    public void cleanTables() {
        DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:postgresql://localhost:5432/testing",
                "postgres", "postgres"), CommonOperations.DELETE_DATA);
        dbSetup.launch();
    }

}
