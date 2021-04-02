/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author regis
 */
@Entity
public class PrisonerStatusTrack implements Serializable{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @ManyToOne
    private Prisoner prisoner;
    @Enumerated(EnumType.STRING)
    private PrisonerStatus prisonerStatus;
    @ManyToOne
    private User doneBy;
    private LocalDateTime dateOfAction;
    private String comment;

    public PrisonerStatusTrack() {
    }
    
    

    public PrisonerStatusTrack(Prisoner prisoner, PrisonerStatus prisonerStatus, User doneBy, LocalDateTime dateOfAction, String comment) {
        this.prisoner = prisoner;
        this.prisonerStatus = prisonerStatus;
        this.doneBy = doneBy;
        this.dateOfAction = dateOfAction;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Prisoner getPrisoner() {
        return prisoner;
    }

    public void setPrisoner(Prisoner prisoner) {
        this.prisoner = prisoner;
    }

    public PrisonerStatus getPrisonerStatus() {
        return prisonerStatus;
    }

    public void setPrisonerStatus(PrisonerStatus prisonerStatus) {
        this.prisonerStatus = prisonerStatus;
    }

    public User getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(User doneBy) {
        this.doneBy = doneBy;
    }

    public LocalDateTime getDateOfAction() {
        return dateOfAction;
    }

    public void setDateOfAction(LocalDateTime dateOfAction) {
        this.dateOfAction = dateOfAction;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
