/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author regis
 */
@Entity
public class Administrator extends Person{
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    public Administrator() {
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
    
    
}
