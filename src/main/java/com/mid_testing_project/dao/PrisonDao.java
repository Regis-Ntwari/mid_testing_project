/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.interfaces.RepositoryInterface;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

/**
 *
 * @author regis
 */
public class PrisonDao implements RepositoryInterface<Prison>{
    private Session session;
    @Override
    public Prison save(Prison t){
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }
    @Override
    public Prison findById(String id){
        session = HibernateUtilities.getSessionFactory().openSession();
        Prison p = session.get(Prison.class, id);
        session.close();
        return p;
    }
    @Override
    public Set<Prison> findAll(){
        session = HibernateUtilities.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Prison> query = builder.createQuery(Prison.class);
        Root<Prison> root = query.from(Prison.class);
        
        query.select(root);
        Set<Prison> prisons = new HashSet<>(session.createQuery(query).list());
        session.close();
        return prisons;
    }

    @Override
    public Prison update(Prison t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    @Override
    public Prison delete(Prison t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }
}
