package com.rems.model;

import com.rems.model.enums.AppointmentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appt_id")
    private int apptId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private User agent;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    public Appointment() {}

    public int getApptId()                    { return apptId; }
    public Unit getUnit()                      { return unit; }
    public void setUnit(Unit u)                { this.unit = u; }
    public User getTenant()                    { return tenant; }
    public void setTenant(User t)              { this.tenant = t; }
    public User getAgent()                     { return agent; }
    public void setAgent(User a)               { this.agent = a; }
    public LocalDateTime getStartTime()        { return startTime; }
    public void setStartTime(LocalDateTime s)  { this.startTime = s; }
    public LocalDateTime getEndTime()          { return endTime; }
    public void setEndTime(LocalDateTime e)    { this.endTime = e; }
    public AppointmentStatus getStatus()       { return status; }
    public void setStatus(AppointmentStatus s) { this.status = s; }
}
