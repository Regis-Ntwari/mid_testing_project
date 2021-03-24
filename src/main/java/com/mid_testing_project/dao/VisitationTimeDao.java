/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.VisitationTime;
import org.hibernate.Session;

/**
 *
 * @author regis
 */
public class VisitationTimeDao {
    public void save(VisitationTime prison){
        Session session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(prison);
        session.getTransaction().commit();
        session.close();
    }
}
