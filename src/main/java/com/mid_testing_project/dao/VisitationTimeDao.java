/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.VisitationTime;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

/**
 *
 * @author regis
 */
public class VisitationTimeDao {

    private Session session;

    public void save(VisitationTime prison) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(prison);
        session.getTransaction().commit();
        session.close();
    }

    public void update(VisitationTime prison) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(prison);
        session.getTransaction().commit();
        session.close();
    }

    public VisitationTime findById(String id) {
        session = HibernateUtilities.getSessionFactory().openSession();
        VisitationTime time = session.get(VisitationTime.class, id);
        session.close();
        return time;
    }

    public VisitationTime findInUseTime() {
        session = HibernateUtilities.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<VisitationTime> query = builder.createQuery(VisitationTime.class);
        Root<VisitationTime> root = query.from(VisitationTime.class);

        query.select(root).where(builder.equal(root.get("visitationTimeStatus"), "IN_USE"));

        VisitationTime time = session.createQuery(query).uniqueResult();
        session.close();
        return time;
    }
}
