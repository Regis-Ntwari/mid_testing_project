/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author regis
 */
@Entity
public class Prisoner extends Person implements Serializable{
    private LocalDate entryDate;
    private int periodOfPunishment;
    private String crime;
    @ManyToOne
    private Prison prison;

    public Prisoner() {
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public int getPeriodOfPunishment() {
        return periodOfPunishment;
    }

    public void setPeriodOfPunishment(int periodOfPunishment) {
        this.periodOfPunishment = periodOfPunishment;
    }

    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime = crime;
    }

    public Prison getPrison() {
        return prison;
    }

    public void setPrison(Prison prison) {
        this.prison = prison;
    }
    
    
}
