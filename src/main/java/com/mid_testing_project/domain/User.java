/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author regis
 */
@Entity
@Table(name = "users")
public class User extends Person implements Serializable{
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private LocalDate joinedDate;
    @ManyToOne(optional = false)
    private Prison prison;
    @Enumerated(EnumType.STRING)
    private UserRole staffRole;
    @Enumerated(EnumType.STRING)
    private UserWorkingStatus staffWorkingStatus;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public Prison getPrison() {
        return prison;
    }

    public void setPrison(Prison prison) {
        this.prison = prison;
    }

    public UserRole getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(UserRole staffRole) {
        this.staffRole = staffRole;
    }

    public UserWorkingStatus getStaffWorkingStatus() {
        return staffWorkingStatus;
    }

    public void setStaffWorkingStatus(UserWorkingStatus staffWorkingStatus) {
        this.staffWorkingStatus = staffWorkingStatus;
    }
    
}
