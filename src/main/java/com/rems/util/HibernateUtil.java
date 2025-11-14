package com.rems.util;

import com.rems.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Mall.class)
                    .addAnnotatedClass(Unit.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Appointment.class)
                    .addAnnotatedClass(Lease.class)
                    .addAnnotatedClass(Invoice.class)
                    .addAnnotatedClass(MaintenanceRequest.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
