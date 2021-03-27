/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.Prisoner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author regis
 */
public class PrisonerDao implements RepositoryInterface<Prisoner>{
    
    Session session = HibernateUtilities.getSessionFactory().openSession();

    @Override
    public void save(Prisoner t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Prisoner t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Prisoner t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Set<Prisoner> findAll() {
        List<Prisoner> prisoners = new ArrayList<>();
        try {
            session = HibernateUtilities.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Prisoner> query = builder.createQuery(Prisoner.class);
            Root<Prisoner> root = query.from(Prisoner.class);
            query.select(root);
            Query<Prisoner> q = session.createQuery(query);
            
            prisoners = q.list();
            session.close();
        } catch (HibernateException e) {
        }
        return new HashSet<>(prisoners);
    }

    @Override
    public Prisoner findById(String id) {
        session = HibernateUtilities.getSessionFactory().openSession();
        Prisoner prisoner = session.get(Prisoner.class, id);
        session.close();
        return  prisoner;
    }
    
    public Set<Prisoner> findAllPrisonersInPrison(Prison prison){
        session = HibernateUtilities.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Prisoner> query = builder.createQuery(Prisoner.class);
        Root<Prisoner> root = query.from(Prisoner.class);
        
        query.select(root).where(builder.equal(root.get("prison"), prison));
        Set<Prisoner> prisoners = new HashSet<>(session.createQuery(query).list());
        session.close();
        return prisoners;
    }
    
}
