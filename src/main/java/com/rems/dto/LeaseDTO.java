package com.rems.dto;

import com.rems.model.enums.PaymentFrequency;
import java.time.LocalDate;

public class LeaseDTO {
    private int unitId;
    private int tenantId;
    private LocalDate startDate;
    private LocalDate endDate;
    private PaymentFrequency paymentFrequency;

    public LeaseDTO() {}
    public LeaseDTO(int unitId, int tenantId, LocalDate startDate,
                    LocalDate endDate, PaymentFrequency paymentFrequency) {
        this.unitId = unitId;
        this.tenantId = tenantId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentFrequency = paymentFrequency;
    }

    public int getUnitId()                          { return unitId; }
    public void setUnitId(int u)                    { this.unitId = u; }
    public int getTenantId()                        { return tenantId; }
    public void setTenantId(int t)                  { this.tenantId = t; }
    public LocalDate getStartDate()                 { return startDate; }
    public void setStartDate(LocalDate d)           { this.startDate = d; }
    public LocalDate getEndDate()                   { return endDate; }
    public void setEndDate(LocalDate d)             { this.endDate = d; }
    public PaymentFrequency getPaymentFrequency()   { return paymentFrequency; }
    public void setPaymentFrequency(PaymentFrequency f) { this.paymentFrequency = f; }
}
