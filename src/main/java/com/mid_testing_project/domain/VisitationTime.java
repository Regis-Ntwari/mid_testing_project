/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author regis
 */
@Entity
public class VisitationTime implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String visitTime;
    private String visitationDay;
    @OneToMany(mappedBy = "visitationTime")
    private List<Visitation> visits = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private VisitationTimeStatus visitationTimeStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public List<Visitation> getVisits() {
        return visits;
    }

    public void setVisits(List<Visitation> visits) {
        this.visits = visits;
    }

    public VisitationTimeStatus getVisitationTimeStatus() {
        return visitationTimeStatus;
    }

    public void setVisitationTimeStatus(VisitationTimeStatus visitationTimeStatus) {
        this.visitationTimeStatus = visitationTimeStatus;
    }

    public String getVisitationDay() {
        return visitationDay;
    }

    public void setVisitationDay(String visitationDay) {
        this.visitationDay = visitationDay;
    }
    
}
