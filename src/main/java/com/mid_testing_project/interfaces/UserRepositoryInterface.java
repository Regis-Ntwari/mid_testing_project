/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.interfaces;

import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.UserRole;
import com.mid_testing_project.domain.UserWorkingStatus;
import java.util.Set;


/**
 *
 * @author regis
 * @param <T>
 */
public interface UserRepositoryInterface<T> extends RepositoryInterface<T>{
    T findByUsername(String username);
    Set<T> findStaffByStaffRole(UserRole role, UserWorkingStatus status);
    Set<T> findStaffByStaffRoleInCertainPrison(UserRole role, UserWorkingStatus status, Prison prison);
}
