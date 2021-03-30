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
public interface VisitationTimeInterface<T> extends RepositoryInterface<T> {
    Set<T> findAllInUseTime();
}
