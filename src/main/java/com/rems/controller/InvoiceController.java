package com.rems.controller;

import com.rems.model.Invoice;
import com.rems.service.BillingService;
import java.time.LocalDate;
import java.util.List;

public class InvoiceController {

    private final BillingService billingService = new BillingService();

    public Invoice generateInvoice(int leaseId, LocalDate billingPeriod,
                                    double water, double electricity, double waste,
                                    int tenantActiveLeaseCount) {
        return billingService.generateInvoice(leaseId, billingPeriod,
                water, electricity, waste, tenantActiveLeaseCount);
    }

    public Invoice markAsPaid(int invoiceId) {
        return billingService.markAsPaid(invoiceId);
    }

    public List<Invoice> getInvoicesForLease(int leaseId) {
        return billingService.getInvoicesForLease(leaseId);
    }

    public List<Invoice> getAllInvoices() {
        return billingService.getAllInvoices();
    }
}
