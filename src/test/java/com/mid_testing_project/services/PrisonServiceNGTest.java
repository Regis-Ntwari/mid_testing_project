/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.HibernateUtilities;
import com.mid_testing_project.domain.Guardian;
import com.mid_testing_project.domain.Prisoner;
import com.mid_testing_project.exceptions.InvalidPrisonException;
import com.mid_testing_project.exceptions.InvalidPrisonerException;
import com.mid_testing_project.exceptions.InvalidStaffException;
import com.mid_testing_project.exceptions.NotStaffActivatedException;
import com.mid_testing_project.exceptions.NotStaffException;
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
public class PrisonServiceNGTest {

    private final PrisonService prisonService = new PrisonService();

    public PrisonServiceNGTest() {
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
    public void testAddPrisoner() {
        Prisoner prisoner = new Prisoner();
        prisoner.setCrime("THIEF");
        prisoner.setEmail("rex90danny@gmail.com");
        prisoner.setGuardian(new Guardian("HEHE", "0789875147"));
        prisoner.setName("Danny");
        prisoner.setNationalId("123456789");
        prisoner.setPeriodOfPunishment(10);
        prisoner.setPhone("0789999999");
        Prisoner returnedPrisoner = prisonService.addPrisoner("P-02", prisoner, "added prisoner", "PR-01");
        Assert.assertEquals(returnedPrisoner.getVersion(), 1);
    }

    @Test(expectedExceptions = InvalidPrisonException.class)
    public void testAddPrisonerInvalidPrison() {
        Prisoner prisoner = new Prisoner();
        prisoner.setCrime("THIEF");
        prisoner.setEmail("rex90danny@gmail.com");
        prisoner.setGuardian(new Guardian("HEHE", "0789875147"));
        prisoner.setName("Danny");
        prisoner.setNationalId("123456789");
        prisoner.setPeriodOfPunishment(10);
        prisoner.setPhone("0789999999");
        prisonService.addPrisoner("P-02", prisoner, "added prisoner", "PR-02");

    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testAddPrisonerInvalidManager() {
        Prisoner prisoner = new Prisoner();
        prisoner.setCrime("THIEF");
        prisoner.setEmail("rex90danny@gmail.com");
        prisoner.setGuardian(new Guardian("HEHE", "0789875147"));
        prisoner.setName("Danny");
        prisoner.setNationalId("123456789");
        prisoner.setPeriodOfPunishment(10);
        prisoner.setPhone("0789999999");
        prisonService.addPrisoner("P-20", prisoner, "added prisoner", "PR-01");
    }

    @Test(expectedExceptions = NotStaffException.class)
    public void testAddPrisonerNotManager() {
        Prisoner prisoner = new Prisoner();
        prisoner.setCrime("THIEF");
        prisoner.setEmail("rex90danny@gmail.com");
        prisoner.setGuardian(new Guardian("HEHE", "0789875147"));
        prisoner.setName("Danny");
        prisoner.setNationalId("123456789");
        prisoner.setPeriodOfPunishment(10);
        prisoner.setPhone("0789999999");
        prisonService.addPrisoner("P-01", prisoner, "added prisoner", "PR-01");
    }

    @Test(expectedExceptions = NotStaffActivatedException.class)
    public void testAddPrisonerManagerNotActivated() {
        Prisoner prisoner = new Prisoner();
        prisoner.setCrime("THIEF");
        prisoner.setEmail("rex90danny@gmail.com");
        prisoner.setGuardian(new Guardian("HEHE", "0789875147"));
        prisoner.setName("Danny");
        prisoner.setNationalId("123456789");
        prisoner.setPeriodOfPunishment(10);
        prisoner.setPhone("0789999999");
        prisonService.addPrisoner("P-10", prisoner, "added prisoner", "PR-01");
    }

    @Test
    public void testReleasePrisoner() {
        Prisoner prisoner = prisonService.releasePrisoner("P-02", "P-04", "released prisoner");
        Assert.assertEquals(prisoner.getVersion(), 2);
    }

    @Test(expectedExceptions = InvalidPrisonerException.class)
    public void testReleasePrisonerInvalidPrisoner() {
        prisonService.releasePrisoner("P-02", "P-20", "released prisoner");
    }

    @Test(expectedExceptions = InvalidStaffException.class)
    public void testReleasePrisonerInvalidManager() {
        prisonService.releasePrisoner("P-22", "P-04", "released prisoner");
    }

    @Test(expectedExceptions = NotStaffException.class)
    public void testReleasePrisonerNotManager() {
        prisonService.releasePrisoner("P-01", "P-04", "released prisoner");
    }

    @Test(expectedExceptions = NotStaffActivatedException.class)
    public void testReleasePrisonerManagerNotActivated() {
        prisonService.releasePrisoner("P-10", "P-04", "released prisoner");
    }

    @Test
    public void testFindAllPrisonersInCertainPrison() {
        Set<Prisoner> prisoners = prisonService.findAllPrisonersInCertainPrison("PR-01");
        Assert.assertEquals(prisoners.size(), 2);
    }
    @Test(expectedExceptions = InvalidPrisonException.class)
    public void testFindAllPrisonersInCertainPrisonInvalidPrison(){
        prisonService.findAllPrisonersInCertainPrison("PR-02");
    }

    @Test
    public void testFindAllPrisoners() {
        Set<Prisoner> prisoners = prisonService.findAllPrisoners();
        Assert.assertEquals(prisoners.size(), 2);
    }

    @AfterMethod
    public void cleanTables() {
        DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:postgresql://localhost:5432/testing",
                "postgres", "postgres"), CommonOperations.DELETE_DATA);
        dbSetup.launch();

    }
}
