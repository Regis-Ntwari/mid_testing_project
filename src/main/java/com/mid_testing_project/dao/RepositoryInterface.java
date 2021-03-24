/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import java.util.Set;

/**
 *
 * @author regis
 * @param <T>
 */
public interface RepositoryInterface<T> {
    void save(T t);
    void update(T t);
    void delete(T t);
    Set<T> findAll();
    T findById(String id);
}
