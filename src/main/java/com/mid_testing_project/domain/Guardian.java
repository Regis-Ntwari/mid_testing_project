/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author regis
 */
@Embeddable
public class Guardian implements Serializable{
    private String guardianName;
    private String telephone;

    public Guardian() {
    }

    public Guardian(String guardianName, String telephone) {
        this.guardianName = guardianName;
        this.telephone = telephone;
    }
    
    

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    
}
