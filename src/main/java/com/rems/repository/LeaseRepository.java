package com.rems.repository;

import com.rems.model.Lease;
import com.rems.model.enums.LeaseStatus;
import com.rems.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class LeaseRepository {

    public void save(Lease lease) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(lease);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Lease findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Lease.class, id);
        }
    }

    public List<Lease> findByTenantId(int tenantId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Lease l WHERE l.tenant.userId = :tenantId", Lease.class)
                .setParameter("tenantId", tenantId)
                .list();
        }
    }

    public List<Lease> findActive() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Lease l WHERE l.status = :status", Lease.class)
                .setParameter("status", LeaseStatus.ACTIVE)
                .list();
        }
    }

    public int countActiveLeasesByTenant(int tenantId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                "SELECT COUNT(l) FROM Lease l WHERE l.tenant.userId = :tenantId AND l.status = 'ACTIVE'", Long.class)
                .setParameter("tenantId", tenantId)
                .uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }

    public void update(Lease lease) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(lease);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
