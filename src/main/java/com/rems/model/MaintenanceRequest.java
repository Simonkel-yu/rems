package com.rems.model;

import com.rems.model.enums.MaintenancePriority;
import com.rems.model.enums.MaintenanceStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_requests")
public class MaintenanceRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenancePriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceStatus status = MaintenanceStatus.OPEN;

    @Column(nullable = false)
    private boolean chargeable = false;

    @Column(name = "charge_amount")
    private double chargeAmount = 0.0;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt = LocalDateTime.now();

    public MaintenanceRequest() {}

    public int getTicketId()                       { return ticketId; }
    public Unit getUnit()                           { return unit; }
    public void setUnit(Unit u)                     { this.unit = u; }
    public User getTenant()                         { return tenant; }
    public void setTenant(User t)                   { this.tenant = t; }
    public String getDescription()                  { return description; }
    public void setDescription(String d)            { this.description = d; }
    public MaintenancePriority getPriority()        { return priority; }
    public void setPriority(MaintenancePriority p)  { this.priority = p; }
    public MaintenanceStatus getStatus()            { return status; }
    public void setStatus(MaintenanceStatus s)      { this.status = s; }
    public boolean isChargeable()                   { return chargeable; }
    public void setChargeable(boolean c)            { this.chargeable = c; }
    public double getChargeAmount()                 { return chargeAmount; }
    public void setChargeAmount(double a)           { this.chargeAmount = a; }
    public LocalDateTime getSubmittedAt()           { return submittedAt; }
}
