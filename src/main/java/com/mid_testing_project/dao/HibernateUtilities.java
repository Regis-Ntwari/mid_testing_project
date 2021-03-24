/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.dao;

import com.mid_testing_project.domain.Administrator;
import com.mid_testing_project.domain.Person;
import com.mid_testing_project.domain.Prison;
import com.mid_testing_project.domain.Prisoner;
import com.mid_testing_project.domain.Staff;
import com.mid_testing_project.domain.Visitation;
import com.mid_testing_project.domain.VisitationTime;
import com.mid_testing_project.domain.Visitor;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

/**
 *
 * @author regis
 */
public class HibernateUtilities {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            Configuration configuration = new Configuration();
            
            Properties settings = new Properties();
            settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
            settings.put(Environment.DRIVER, "org.postgresql.Driver");
            settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/testing");
            settings.put(Environment.USER, "postgres");
            settings.put(Environment.PASS, "postgres");
            settings.put(Environment.HBM2DDL_AUTO, "update");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.FORMAT_SQL, "true");
            
            configuration.addProperties(settings);
            
            configuration.addAnnotatedClass(Administrator.class);
            configuration.addAnnotatedClass(Person.class);
            configuration.addAnnotatedClass(Prison.class);
            configuration.addAnnotatedClass(Prisoner.class);
            configuration.addAnnotatedClass(Staff.class);
            configuration.addAnnotatedClass(Visitation.class);
            configuration.addAnnotatedClass(VisitationTime.class);
            configuration.addAnnotatedClass(Visitor.class);
            
            sessionFactory = configuration.buildSessionFactory();
            
        }
        return sessionFactory;
    }
}
