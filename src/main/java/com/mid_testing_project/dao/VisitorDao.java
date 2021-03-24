/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.Staff;
import com.mid_testing_project.domain.Visitor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author regis
 */
public class VisitorDao implements RepositoryInterface<Visitor>{
    
    private Session session;

    @Override
    public void save(Visitor t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Visitor t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Visitor t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Set<Visitor> findAll() {
        List<Visitor> visitors = new ArrayList<>();
        try {
            session = HibernateUtilities.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Visitor> query = builder.createQuery(Visitor.class);
            Root<Visitor> root = query.from(Visitor.class);
            
            
            query.select(root);
            Query<Visitor> q = session.createQuery(query);
            visitors = q.list();
            session.close();
        } catch (HibernateException e) {
        }
        return new HashSet<>(visitors);
    }

    @Override
    public Visitor findById(String id) {
        session = HibernateUtilities.getSessionFactory().openSession();
        Visitor visitor = session.get(Visitor.class, id);
        session.close();
        return visitor;
    }
    
}
