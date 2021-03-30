/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.Visitation;
import com.mid_testing_project.domain.VisitationOccurrenceStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author regis
 */
public class VisitationDao implements VisitationInterface<Visitation> {

    private Session session;

    @Override
    public void save(Visitation t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Visitation t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Visitation t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Set<Visitation> findAll() {
        List<Visitation> visitations = new ArrayList<>();
        try {
            session = HibernateUtilities.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Visitation> query = builder.createQuery(Visitation.class);
            Root<Visitation> root = query.from(Visitation.class);
            query.select(root);
            Query<Visitation> q = session.createQuery(query);

            visitations = q.list();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return new HashSet<>(visitations);
    }

    @Override
    public Visitation findById(String id) {
        session = HibernateUtilities.getSessionFactory().openSession();
        Visitation visitation = session.get(Visitation.class, id);
        session.close();
        return visitation;
    }

    @Override
    public Set<Visitation> findAllByOccurrenceStatus(VisitationOccurrenceStatus status, Prison prison) {
        session = HibernateUtilities.getSessionFactory().openSession();
        CriteriaBuilder cb =  session.getCriteriaBuilder();
        CriteriaQuery<Visitation> q = cb.createQuery(Visitation.class);
        Root<Visitation> root = q.from(Visitation.class);
        root.fetch("prisoner", JoinType.LEFT);
        q.select(root).where(cb.and(cb.equal(root.get("prisoner").get("prison"), prison), 
                cb.equal(root.get("occurrenceStatus"), status)));
        
        Query<Visitation> vd = session.createQuery(q);
        
        List<Visitation> visitations = vd.getResultList();
        session.close();
        return new HashSet<>(visitations);
    }
    
    @Override
    public Set<Visitation> findAllByToday(Prison prison){
        session = HibernateUtilities.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Visitation> query = builder.createQuery(Visitation.class);
        Root<Visitation> root = query.from(Visitation.class);
        
        root.fetch("prisoner", JoinType.LEFT);
        query.select(root).where(builder.and(builder.equal(root.get("prisoner").get("prison"), prison),
                                                builder.equal(root.get("visitationDate"), LocalDate.now()), builder.equal(root.get("requestStatus"), "APPROVED")));
        
        List<Visitation> visits = session.createQuery(query).getResultList();
        return new HashSet<>(visits);
    }
}
