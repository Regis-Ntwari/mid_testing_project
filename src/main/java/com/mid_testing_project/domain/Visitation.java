/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author regis
 */
@Entity
public class Visitation implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private Visitor visitor;
    @ManyToOne
    private Prisoner prisoner;
    private LocalDate visitationDate;
    @ManyToOne
    private VisitationTime visitationTime;
    @Version
    private long version = 1;

    public Visitation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Prisoner getPrisoner() {
        return prisoner;
    }

    public void setPrisoner(Prisoner prisoner) {
        this.prisoner = prisoner;
    }

    public LocalDate getVisitationDate() {
        return visitationDate;
    }

    public void setVisitationDate(LocalDate visitationDate) {
        this.visitationDate = visitationDate;
    }

    public VisitationTime getVisitationTime() {
        return visitationTime;
    }

    public void setVisitationTime(VisitationTime visitationTime) {
        this.visitationTime = visitationTime;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
    
}
