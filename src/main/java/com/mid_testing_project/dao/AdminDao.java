/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;


import com.mid_testing_project.domain.Administrator;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author regis
 */
public class AdminDao implements UserRepositoryInterface<Administrator>{

    private Session session;
    
    @Override
    public Administrator findByUsername(String username) {
        Administrator admin = null;
        try {
            session = HibernateUtilities.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Administrator> query = builder.createQuery(Administrator.class);
            Root<Administrator> root = query.from(Administrator.class);
            query.select(root).where(builder.equal(root.get("username"), username));
            Query<Administrator> q = session.createQuery(query);
            admin = q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }
    
    
}
