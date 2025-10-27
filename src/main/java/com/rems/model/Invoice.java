package com.rems.model;

import com.rems.model.enums.InvoiceStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lease_id", nullable = false)
    private Lease lease;

    @Column(name = "billing_period", nullable = false)
    private LocalDate billingPeriod;

    @Column(name = "base_rent", nullable = false)
    private double baseRent;

    @Column(name = "water_charge")
    private double waterCharge;

    @Column(name = "electricity_charge")
    private double electricityCharge;

    @Column(name = "waste_charge")
    private double wasteCharge;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status = InvoiceStatus.UNPAID;

    public Invoice() {}

    public int getInvoiceId()                  { return invoiceId; }
    public Lease getLease()                     { return lease; }
    public void setLease(Lease l)               { this.lease = l; }
    public LocalDate getBillingPeriod()         { return billingPeriod; }
    public void setBillingPeriod(LocalDate d)   { this.billingPeriod = d; }
    public double getBaseRent()                 { return baseRent; }
    public void setBaseRent(double r)           { this.baseRent = r; }
    public double getWaterCharge()              { return waterCharge; }
    public void setWaterCharge(double w)        { this.waterCharge = w; }
    public double getElectricityCharge()        { return electricityCharge; }
    public void setElectricityCharge(double e)  { this.electricityCharge = e; }
    public double getWasteCharge()              { return wasteCharge; }
    public void setWasteCharge(double w)        { this.wasteCharge = w; }
    public double getTotalAmount()              { return totalAmount; }
    public void setTotalAmount(double t)        { this.totalAmount = t; }
    public InvoiceStatus getStatus()            { return status; }
    public void setStatus(InvoiceStatus s)      { this.status = s; }
}
