package com.rems.repository;

import com.rems.model.MaintenanceRequest;
import com.rems.model.enums.MaintenancePriority;
import com.rems.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class MaintenanceRepository {

    public void save(MaintenanceRequest request) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(request);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public MaintenanceRequest findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MaintenanceRequest.class, id);
        }
    }

    public List<MaintenanceRequest> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM MaintenanceRequest m ORDER BY " +
                "CASE WHEN m.priority = 'EMERGENCY' THEN 0 ELSE 1 END, m.submittedAt ASC",
                MaintenanceRequest.class).list();
        }
    }

    public List<MaintenanceRequest> findByTenantId(int tenantId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM MaintenanceRequest m WHERE m.tenant.userId = :tenantId", MaintenanceRequest.class)
                .setParameter("tenantId", tenantId)
                .list();
        }
    }

    public void update(MaintenanceRequest request) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(request);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
