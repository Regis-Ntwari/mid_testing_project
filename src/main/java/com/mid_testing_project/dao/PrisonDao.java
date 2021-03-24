/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.Prison;
import org.hibernate.Session;

/**
 *
 * @author regis
 */
public class PrisonDao {
    public void save(Prison prison){
        Session session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(prison);
        session.getTransaction().commit();
        session.close();
    }
    public Prison findById(String id){
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Prison p = session.get(Prison.class, id);
        session.close();
        return p;
    }
}
