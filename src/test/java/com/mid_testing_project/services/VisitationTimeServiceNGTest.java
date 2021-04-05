/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.HibernateUtilities;
import com.mid_testing_project.domain.VisitationTime;
import com.mid_testing_project.exceptions.AlreadyNotInUseTimeException;
import com.mid_testing_project.exceptions.InvalidVisitationTimeException;
import com.mid_testing_project.util.CommonOperations;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import java.util.Set;
import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author regis
 */
public class VisitationTimeServiceNGTest {

    private final VisitationTimeService timeService = new VisitationTimeService();

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

    public VisitationTimeServiceNGTest() {
    }

    @Test
    public void testAddVisitationTime() {
        VisitationTime time = new VisitationTime();
        time.setVisitEndTime("17:00");
        time.setVisitStartTime("8:00");
        time.setVisitationDay("MONDAY");
        VisitationTime times = timeService.addVisitationTime(time);
        Assert.assertEquals(times.getVersion(), 1);
    }

    @Test
    public void testDisableVisitationTime() {
        VisitationTime time = timeService.disableVisitationTime("V-01");
        Assert.assertEquals(time.getVersion(), 2);
    }
    @Test(expectedExceptions = InvalidVisitationTimeException.class)
    public void testDisableVisitationTimeInvalidTimeId(){
        timeService.disableVisitationTime("V-03");
    }
    @Test(expectedExceptions = AlreadyNotInUseTimeException.class)
    public void testDisableVisitationTimeAlreadyDisabled(){
        timeService.disableVisitationTime("V-02");
    }

    @Test
    public void testFindAllInUseTime() {
        Set<VisitationTime> times = timeService.findAllInUseTime();
        Assert.assertEquals(times.size(), 1);
    }
    
    @Test
    public void testFindAllNotInUseTime(){
        Set<VisitationTime> times = timeService.findAllNotInUseTime();
        Assert.assertEquals(times.size(), 1);
    }
    @AfterMethod
    public void cleanTables() {
        DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:postgresql://localhost:5432/testing",
                "postgres", "postgres"), CommonOperations.DELETE_DATA);
        dbSetup.launch();
    }
}
