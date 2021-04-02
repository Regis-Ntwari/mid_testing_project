/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.UserStatus;
import com.mid_testing_project.interfaces.RepositoryInterface;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

/**
 *
 * @author regis
 */
public class UserStatusDao implements RepositoryInterface<UserStatus>{

    private Session session;
    @Override
    public UserStatus save(UserStatus t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    @Override
    public UserStatus update(UserStatus t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    @Override
    public UserStatus delete(UserStatus t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    @Override
    public Set<UserStatus> findAll() {
        session = HibernateUtilities.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserStatus> query = builder.createQuery(UserStatus.class);
        Root<UserStatus> root = query.from(UserStatus.class);
        
        query.select(root);
        
        List<UserStatus> all = session.createQuery(query).getResultList();
        session.close();
        return new HashSet<>(all);
    }

    @Override
    public UserStatus findById(String id) {
        session = HibernateUtilities.getSessionFactory().openSession();
        UserStatus status = session.get(UserStatus.class, id);
        session.close();
        return status;
    }
    
}
