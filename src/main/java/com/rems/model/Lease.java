package com.rems.model;

import com.rems.model.enums.LeaseStatus;
import com.rems.model.enums.PaymentFrequency;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "leases")
public class Lease {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lease_id")
    private int leaseId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false, unique = true)
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_frequency", nullable = false)
    private PaymentFrequency paymentFrequency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaseStatus status = LeaseStatus.ACTIVE;

    public Lease() {}

    public int getLeaseId()                            { return leaseId; }
    public Unit getUnit()                               { return unit; }
    public void setUnit(Unit u)                         { this.unit = u; }
    public User getTenant()                             { return tenant; }
    public void setTenant(User t)                       { this.tenant = t; }
    public LocalDate getStartDate()                     { return startDate; }
    public void setStartDate(LocalDate d)               { this.startDate = d; }
    public LocalDate getEndDate()                       { return endDate; }
    public void setEndDate(LocalDate d)                 { this.endDate = d; }
    public PaymentFrequency getPaymentFrequency()       { return paymentFrequency; }
    public void setPaymentFrequency(PaymentFrequency f) { this.paymentFrequency = f; }
    public LeaseStatus getStatus()                      { return status; }
    public void setStatus(LeaseStatus s)                { this.status = s; }
}
