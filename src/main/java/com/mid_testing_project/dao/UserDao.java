/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.interfaces.UserRepositoryInterface;
import com.mid_testing_project.domain.User;
import com.mid_testing_project.domain.UserRole;
import com.mid_testing_project.domain.UserWorkingStatus;
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
public class UserDao implements UserRepositoryInterface<User> {

    private Session session;

    @Override
    public User save(User t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    @Override
    public User update(User t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    @Override
    public User delete(User t) {
        session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    @Override
    public Set<User> findAll() {
        List<User> staffs = new ArrayList<>();
        try {
            session = HibernateUtilities.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root);
            Query<User> q = session.createQuery(query);

            staffs = q.list();
            session.close();
        } catch (HibernateException e) {
        }
        return new HashSet<>(staffs);
    }

    @Override
    public User findById(String id) {
        session = HibernateUtilities.getSessionFactory().openSession();
        User staff = session.get(User.class, id);
        session.close();
        return staff;
    }

    @Override
    public User findByUsername(String username) {
        User staff = null;
        try {
            session = HibernateUtilities.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.get("username"), username));
            Query<User> q = session.createQuery(query);
            staff = q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;

    }

    @Override
    public Set<User> findStaffByStaffRole(UserRole role, UserWorkingStatus status) {
        List<User> users = new ArrayList<>();
        try {
            session = HibernateUtilities.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.and(builder.equal(root.get("staffRole"), role),
                                                builder.equal(root.get("staffWorkingStatus"), status)));
            Query<User> q = session.createQuery(query);
            users = q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>(users);
    }

    @Override
    public Set<User> findStaffByStaffRoleInCertainPrison(UserRole role, UserWorkingStatus status, Prison prison) {
        List<User> users = new ArrayList<>();
        try {
            session = HibernateUtilities.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.and(builder.equal(root.get("staffRole"), role),
                                                builder.equal(root.get("staffWorkingStatus"), status),
                                                builder.equal(root.get("prison"), prison)));
            Query<User> q = session.createQuery(query);
            users = q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>(users);
    }

}
