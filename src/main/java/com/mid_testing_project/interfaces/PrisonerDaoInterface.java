/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.interfaces;

import com.mid_testing_project.domain.Prison;
import java.util.Set;

/**
 *
 * @author regis
 */
public interface PrisonerDaoInterface<T> extends RepositoryInterface<T> {
    Set<T> findAllPrisonersInPrison(Prison prison);
}
