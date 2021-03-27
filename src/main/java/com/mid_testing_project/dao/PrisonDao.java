/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.Prison;
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
public class PrisonDao {
    private Session session;
    public void save(Prison prison){
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(prison);
        session.getTransaction().commit();
        session.close();
    }
    public Prison findById(String id){
        session = HibernateUtilities.getSessionFactory().openSession();
        Prison p = session.get(Prison.class, id);
        session.close();
        return p;
    }
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
}
