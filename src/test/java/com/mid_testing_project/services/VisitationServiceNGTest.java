/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.services;

import com.mid_testing_project.dao.HibernateUtilities;
import com.mid_testing_project.domain.Visitation;
import com.mid_testing_project.domain.Visitor;
import com.mid_testing_project.exceptions.DateNotYetOccurredException;
import com.mid_testing_project.exceptions.InvalidPrisonException;
import com.mid_testing_project.exceptions.InvalidPrisonerException;
import com.mid_testing_project.exceptions.InvalidVisitationDateException;
import com.mid_testing_project.exceptions.InvalidVisitationTimeException;
import com.mid_testing_project.exceptions.OutOfBoundsVisitationTimeException;
import com.mid_testing_project.util.CommonOperations;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import java.time.LocalDate;
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
public class VisitationServiceNGTest {

    private final VisitationService visitationService = new VisitationService();

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

    public VisitationServiceNGTest() {
    }

    @Test
    public void testVisit() {
        Visitation visit = visitationService.visit(new Visitor("Tony", "Langton", "0789765432", "tony@gmail.com"),
                "P-04");
        Assert.assertEquals(visit.getVersion(), 1);
    }

    @Test(expectedExceptions = InvalidPrisonerException.class)
    public void testVisitInvalidPrisoner() {
        visitationService.visit(new Visitor("Tony", "Langton", "0789765432", "tony@gmail.com"),
                "P-22");
    }
    @Test(expectedExceptions = OutOfBoundsVisitationTimeException.class)
    public void testVisitOutOfBoundsVisitation(){
        visitationService.visit(new Visitor("Tony", "Langton", "0789765432", "tony@gmail.com"), 
                "P-04");
    }
    @Test(expectedExceptions = InvalidVisitationDateException.class)
    public void testVisitNotVisitationDay(){
        visitationService.visit(new Visitor("Tony", "Langton", "0789765432", "tony@gmail.com"), 
                "P-04");
    }

    @Test
    public void testFindAllVisitations() {
        Set<Visitation> visits = visitationService.findAllVisitations("PR-01");
        Assert.assertEquals(visits.size(), 2);
    }
    @Test(expectedExceptions = InvalidPrisonException.class)
    public void testFindAllVisitationsInvalidPrison(){
        visitationService.findAllVisitations("PR-04");
    }

    @Test
    public void testFindAllVisitationsOnCertainDate() {
        Set<Visitation> visits = visitationService.findAllVisitationsOnCertainDate(
                LocalDate.parse("2021-04-03"), "PR-01");
        Assert.assertEquals(visits.size(), 1);
    }
    @Test(expectedExceptions = InvalidPrisonException.class)
    public void testFindAllVisitationsOnCertainDateInvalidPrison(){
        visitationService.findAllVisitationsOnCertainDate(
                LocalDate.parse("2021-04-03"), "PR-04");
    }
    @Test(expectedExceptions = DateNotYetOccurredException.class)
    public void testFindAllVisitationsOnCertainDateInvalidDate(){
        visitationService.findAllVisitationsOnCertainDate(
                LocalDate.parse("2021-06-12"), "PR-01");
    }

    @AfterMethod
    public void cleanTables() {
        DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:postgresql://localhost:5432/testing",
                "postgres", "postgres"), CommonOperations.DELETE_DATA);
        dbSetup.launch();
    }
}
