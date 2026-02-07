package com.rems.dto;

import com.rems.model.enums.InvoiceStatus;
import java.time.LocalDate;

public class InvoiceDTO {
    private int invoiceId;
    private int leaseId;
    private LocalDate billingPeriod;
    private double baseRent;
    private double waterCharge;
    private double electricityCharge;
    private double wasteCharge;
    private double totalAmount;
    private InvoiceStatus status;

    public InvoiceDTO() {}

    public int getInvoiceId()                  { return invoiceId; }
    public void setInvoiceId(int i)            { this.invoiceId = i; }
    public int getLeaseId()                    { return leaseId; }
    public void setLeaseId(int l)              { this.leaseId = l; }
    public LocalDate getBillingPeriod()        { return billingPeriod; }
    public void setBillingPeriod(LocalDate d)  { this.billingPeriod = d; }
    public double getBaseRent()                { return baseRent; }
    public void setBaseRent(double r)          { this.baseRent = r; }
    public double getWaterCharge()             { return waterCharge; }
    public void setWaterCharge(double w)       { this.waterCharge = w; }
    public double getElectricityCharge()       { return electricityCharge; }
    public void setElectricityCharge(double e) { this.electricityCharge = e; }
    public double getWasteCharge()             { return wasteCharge; }
    public void setWasteCharge(double w)       { this.wasteCharge = w; }
    public double getTotalAmount()             { return totalAmount; }
    public void setTotalAmount(double t)       { this.totalAmount = t; }
    public InvoiceStatus getStatus()           { return status; }
    public void setStatus(InvoiceStatus s)     { this.status = s; }
}
