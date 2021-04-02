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
public class UserStatus implements Serializable{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUD", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @ManyToOne
    private User doneBy;
    @ManyToOne
    private User doneTo;
    private String comment;
    private LocalDateTime dateOfAction;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Enumerated(EnumType.STRING)
    private UserWorkingStatus userWorkingStatus;

    public UserStatus() {
    }

    
    public UserStatus(User doneBy, User doneTo, String comment, LocalDateTime dateOfAction, UserRole userRole, UserWorkingStatus userWorkingStatus) {
        this.doneBy = doneBy;
        this.doneTo = doneTo;
        this.comment = comment;
        this.dateOfAction = dateOfAction;
        this.userRole = userRole;
        this.userWorkingStatus = userWorkingStatus;
    }

    public String getId() {
        return id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserWorkingStatus getUserWorkingStatus() {
        return userWorkingStatus;
    }

    public void setUserWorkingStatus(UserWorkingStatus userWorkingStatus) {
        this.userWorkingStatus = userWorkingStatus;
    }

    
    public void setId(String id) {
        this.id = id;
    }

    public User getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(User doneBy) {
        this.doneBy = doneBy;
    }

    public User getDoneTo() {
        return doneTo;
    }

    public void setDoneTo(User doneTo) {
        this.doneTo = doneTo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDateOfAction() {
        return dateOfAction;
    }

    public void setDateOfAction(LocalDateTime dateOfAction) {
        this.dateOfAction = dateOfAction;
    }
    
    
}
