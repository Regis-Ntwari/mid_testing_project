/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.VisitationOccurrenceStatus;
import java.util.Set;

/**
 *
 * @author regis
 * @param <T>
 */
public interface VisitationInterface<T> extends RepositoryInterface<T> {
    Set<T> findAllByOccurrenceStatus(VisitationOccurrenceStatus status, Prison prison);
    Set<T> findAllByToday(Prison prison);
}
