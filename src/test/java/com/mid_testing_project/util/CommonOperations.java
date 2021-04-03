/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.util;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

/**
 *
 * @author regis
 */
public class CommonOperations {
    public static final Operation INSERT_DATA = Operations.sequenceOf(
                                                    Operations.insertInto("PRISON")
                                                    .columns("ID","NAME")
                                                    .values("PR-01", "Mageragere")
                                                    .build(),
                                                    Operations.insertInto("PERSON")
                                                    .columns("ID", "NAME", "NATIONALID", "PHONE", "EMAIL", "VERSION")
                                                    .values("P-01", "LEONARD", "19090909090", "0784831585",
                                                            "ngaboleonard02@gmail.com", "1")
                                                    .values("P-02", "ARISTIDE", "19090909090", "0787011567",
                                                            "niyongaboaristide17@gmail.com", "1")
                                                    .values("P-03", "REGIS", "19090909090", "0789875147",
                                                            "rex90danny@gmail.com", "1")
                                                    .values("P-04", "NTWARI", "19090909090", "0789875147",
                                                            "rex90danny@gmail.com", "1")
                                                    .values("P-05", "PUPA", "19090909090", "078000000",
                                                            "pupa@gmail.com", "1")
                                                    .values("P-06", "PAPI", "19090909090", "078000000",
                                                            "papi@gmail.com", "1")
                                                    .build(),
                                                    Operations.insertInto("PRISONER")
                                                    .columns("ENTRYDATE", 
                                                            "PERIODOFPUNISHMENT", 
                                                            "CRIME", "PRISON_ID", "PRISONERSTATUS", 
                                                            "GUARDIANNAME", "TELEPHONE", "ID")
                                                    .values("2021-04-03", "18", "KILLER", "PR-01", 
                                                            "INCARCERATED", "Alice", "0789875147", 
                                                            "P-03")
                                                    .values("2021-04-01", "17", "THIEF", "PR-01", 
                                                            "INCARCERATED", "Bob", "0789875147", 
                                                            "P-04")
                                                    .build(),
                                                    Operations.insertInto("USERS")
                                                    .columns("USERNAME", "PASSWORD", "JOINEDDATE", 
                                                            "PRISON_ID", "STAFFROLE", "STAFFWORKINGSTATUS", "ID")
                                                    .values("LEO", "LEO","2019-09-09","PR-01","GUARD",
                                                            "ACTIVATED","P-01")
                                                    .values("ARISTIDE", "ARISTIDE","2019-09-09","PR-01","MANAGER",
                                                            "ACTIVATED","P-02")
                                                    .values("PUPA", "PUPA","2019-09-09","PR-01","ADMIN",
                                                            "ACTIVATED","P-05")
                                                    .values("PAPI", "PAPI","2019-09-09","PR-01","ADMIN",
                                                            "SUSPENDED","P-06")
                                                    .build(),
                                                    Operations.insertInto("VISITATIONTIME")
                                                    .columns("ID", "VISITSTARTTIME", "VISITENDTIME", 
                                                            "VISITATIONDAY", "VISITATIONTIMESTATUS", "VERSION")
                                                    .values("V-01", "8:00", "17:00", "TUESDAY", "IN_USE", "1")
                                                    .values("V-02", "9:00", "16:00", "FRIDAY", "NOT_IN_USE", "1")
                                                    .build(),
                                                    Operations.insertInto("VISITATION")
                                                    .columns("VISITORFIRSTNAME", "VISITORLASTNAME", 
                                                            "VISITORPHONENUMBER", "VISITOREMAIL", "VERSION", "ID",
                                                            "PRISONER_ID", "VISITATIONDATE", "VISITATIONTIME_ID")
                                                    .values("PASCY", "IRABARUTA", "0789160608", "pascyirabaruta456@gmail.com",
                                                            "1","VI-01", "P-03", "2021-04-03", "V-01")
                                                    .values("CHARITY", "MUTONI", "0783009892", "charitytoni0@gmail.com",
                                                            "1","VI-02", "P-04", "2021-04-04", "V-01")
                                                    .build()
                                                );
    public static final Operation DELETE_DATA = Operations.deleteAllFrom("VISITATION", "VISITATIONTIME","USERSTATUS", "USERS", "PRISONER", "PERSON", "PRISON");
}
