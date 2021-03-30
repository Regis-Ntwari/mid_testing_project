/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.VisitationTime;
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
public class VisitationTimeDao implements VisitationTimeInterface<VisitationTime>{

    private Session session;

    @Override
    public void save(VisitationTime t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }
    
    @Override
    public void update(VisitationTime t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public VisitationTime findById(String id) {
        session = HibernateUtilities.getSessionFactory().openSession();
        VisitationTime time = session.get(VisitationTime.class, id);
        session.close();
        return time;
    }

    @Override
    public void delete(VisitationTime t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Set<VisitationTime> findAll() {
        session = HibernateUtilities.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<VisitationTime> query = builder.createQuery(VisitationTime.class);
        Root<VisitationTime> root = query.from(VisitationTime.class);
        
        query.select(root);
        
        List<VisitationTime> allVisitationTimes = session.createQuery(query).getResultList();
        session.close();
        return new HashSet<>(allVisitationTimes);
    }

    @Override
    public Set<VisitationTime> findAllInUseTime() {
        session = HibernateUtilities.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<VisitationTime> query = builder.createQuery(VisitationTime.class);
        Root<VisitationTime> root = query.from(VisitationTime.class);
        
        query.select(root).where(builder.equal(root.get("visitationTimeStatus"), "IN_USE"));
        
        List<VisitationTime> allInUseTimes = session.createQuery(query).getResultList();
        session.close();
        return new HashSet<>(allInUseTimes);
    }

    
}
