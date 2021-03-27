/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mid_testing_project.util;

import com.mid_testing_project.domain.Visitation;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author regis
 */
public class EmailUtil {

    private static final String FROM_EMAIL_ADDRESS = "pupa.news.app@gmail.com";
    private static final String FROM_PASSWORD = "pupa.news.app.123";
    private static final String HOST="smtp.gmail.com";
    
    private static Properties properties;
    private static Session session;
    
    
    static {
        properties = System.getProperties();
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(FROM_EMAIL_ADDRESS, FROM_PASSWORD);
            }
        });
        
        session.setDebug(true);
    }
    
    public static void sendApprovalEmail(Visitation visitor) {
        
        try {
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(visitor.getVisitor().getEmail()));
            
            message.setSubject("VISITATION UPDATE");
            
            message.setText("Your request visit has been submitted. Please continue checking your email");
            
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public static void sendDenialEmail(Visitation visitor){
        
        try {
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(visitor.getVisitor().getEmail()));
            
            message.setSubject("VISITATION UPDATE");
            
            message.setText("Your request visit has been cancelled. Please request for another visit");
            
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public static void sendAcceptanceEmail(Visitation visitor){
        
        try {
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(visitor.getVisitor().getEmail()));
            
            message.setSubject("VISITATION UPDATE");
            
            message.setText("Your request visit has been approved. Please be on time");
            
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
