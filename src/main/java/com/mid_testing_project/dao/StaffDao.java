/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.Staff;
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
public class StaffDao implements RepositoryInterface<Staff>, UserRepositoryInterface<Staff> {

    private Session session;

    @Override
    public void save(Staff t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Staff t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Staff t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Set<Staff> findAll() {
        List<Staff> staffs = new ArrayList<>();
        try {
            session = HibernateUtilities.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Staff> query = builder.createQuery(Staff.class);
            Root<Staff> root = query.from(Staff.class);
            query.select(root);
            Query<Staff> q = session.createQuery(query);
            
            staffs = q.list();
            session.close();
        } catch (HibernateException e) {
        }
        return new HashSet<>(staffs);
    }

    @Override
    public Staff findById(String id) {
        session = HibernateUtilities.getSessionFactory().openSession();
        Staff staff = session.get(Staff.class, id);
        session.close();
        return staff;
    }

    @Override
    public Staff findByUsername(String username) {
        Staff staff = null;
        try {
            session = HibernateUtilities.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Staff> query = builder.createQuery(Staff.class);
            Root<Staff> root = query.from(Staff.class);
            query.select(root).where(builder.equal(root.get("username"), username));
            Query<Staff> q = session.createQuery(query);
            staff = q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;

    }

}
