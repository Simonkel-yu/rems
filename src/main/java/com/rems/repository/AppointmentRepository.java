package com.rems.repository;

import com.rems.model.Appointment;
import com.rems.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentRepository {

    public void save(Appointment appointment) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(appointment);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Appointment findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Appointment.class, id);
        }
    }

    public List<Appointment> findByUnitId(int unitId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Appointment a WHERE a.unit.unitId = :unitId", Appointment.class)
                .setParameter("unitId", unitId)
                .list();
        }
    }

    public List<Appointment> findByAgentId(int agentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Appointment a WHERE a.agent.userId = :agentId", Appointment.class)
                .setParameter("agentId", agentId)
                .list();
        }
    }

    public boolean hasConflict(int unitId, int agentId, LocalDateTime start, LocalDateTime end) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                "SELECT COUNT(a) FROM Appointment a WHERE " +
                "(a.unit.unitId = :unitId OR a.agent.userId = :agentId) AND " +
                "a.startTime < :end AND a.endTime > :start AND " +
                "a.status != 'CANCELLED'", Long.class)
                .setParameter("unitId", unitId)
                .setParameter("agentId", agentId)
                .setParameter("start", start)
                .setParameter("end", end)
                .uniqueResult();
            return count != null && count > 0;
        }
    }

    public List<Appointment> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Appointment", Appointment.class).list();
        }
    }

    public void update(Appointment appointment) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(appointment);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
