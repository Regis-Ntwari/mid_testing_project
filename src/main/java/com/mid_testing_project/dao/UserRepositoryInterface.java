/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

/**
 *
 * @author regis
 */
public interface UserRepositoryInterface<T> {
    T findByUsername(String username);
}
