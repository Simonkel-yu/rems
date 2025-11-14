package com.rems;

import com.rems.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        System.out.println("REMS - Real Estate Management System");
        System.out.println("Connecting to database...");
        try {
            HibernateUtil.getSessionFactory();
            System.out.println("Database connection successful.");
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
