/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.exceptions;

/**
 *
 * @author regis
 */
public class InvalidVisitationDateException extends RuntimeException{

    public InvalidVisitationDateException(String string) {
        super(string);
    }
    
}
