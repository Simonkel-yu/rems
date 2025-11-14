package com.rems.repository;

import com.rems.model.Unit;
import com.rems.model.enums.UnitStatus;
import com.rems.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UnitRepository {

    public void save(Unit unit) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(unit);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Unit findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Unit.class, id);
        }
    }

    public List<Unit> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Unit", Unit.class).list();
        }
    }

    public List<Unit> findAvailable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Unit u WHERE u.status = :status", Unit.class)
                .setParameter("status", UnitStatus.AVAILABLE)
                .list();
        }
    }

    public List<Unit> findByFilters(Integer tier, Double minSize, Double maxSize, Double maxRate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM Unit u WHERE u.status = 'AVAILABLE'");
            if (tier != null)    hql.append(" AND u.tier = :tier");
            if (minSize != null) hql.append(" AND u.sizeSqft >= :minSize");
            if (maxSize != null) hql.append(" AND u.sizeSqft <= :maxSize");
            if (maxRate != null) hql.append(" AND u.baseRate <= :maxRate");

            var query = session.createQuery(hql.toString(), Unit.class);
            if (tier != null)    query.setParameter("tier", tier);
            if (minSize != null) query.setParameter("minSize", minSize);
            if (maxSize != null) query.setParameter("maxSize", maxSize);
            if (maxRate != null) query.setParameter("maxRate", maxRate);
            return query.list();
        }
    }

    public void update(Unit unit) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(unit);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void delete(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Unit unit = session.get(Unit.class, id);
            if (unit != null) session.remove(unit);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
