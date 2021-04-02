/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.interfaces;


/**
 *
 * @author regis
 * @param <T>
 */
public interface UserRepositoryInterface<T> extends RepositoryInterface<T>{
    T findByUsername(String username);

}
