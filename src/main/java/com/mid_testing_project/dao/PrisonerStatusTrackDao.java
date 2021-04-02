/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.interfaces.RepositoryInterface;
import com.mid_testing_project.domain.PrisonerStatusTrack;
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
public class PrisonerStatusTrackDao implements RepositoryInterface<PrisonerStatusTrack>{

    private Session session;
    @Override
    public PrisonerStatusTrack save(PrisonerStatusTrack t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    @Override
    public PrisonerStatusTrack update(PrisonerStatusTrack t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    @Override
    public PrisonerStatusTrack delete(PrisonerStatusTrack t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    @Override
    public Set<PrisonerStatusTrack> findAll() {
        session = HibernateUtilities.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PrisonerStatusTrack> query = builder.createQuery(PrisonerStatusTrack.class);
        Root<PrisonerStatusTrack> root = query.from(PrisonerStatusTrack.class);
        
        query.select(root);
        
        List<PrisonerStatusTrack> all = session.createQuery(query).getResultList();
        session.close();
        return new HashSet<>(all);
    }

    @Override
    public PrisonerStatusTrack findById(String id) {
        session = HibernateUtilities.getSessionFactory().openSession();
        PrisonerStatusTrack pst = session.get(PrisonerStatusTrack.class, id);
        session.close();
        return pst;
    }
    
}
