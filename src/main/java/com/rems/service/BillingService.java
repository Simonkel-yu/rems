package com.rems.service;

import com.rems.model.Invoice;
import com.rems.model.Lease;
import com.rems.model.enums.InvoiceStatus;
import com.rems.model.enums.PaymentFrequency;
import com.rems.repository.InvoiceRepository;
import com.rems.repository.LeaseRepository;
import com.rems.service.billing.*;
import java.time.LocalDate;
import java.util.List;

public class BillingService {

    private final InvoiceRepository invoiceRepository = new InvoiceRepository();
    private final LeaseRepository leaseRepository = new LeaseRepository();

    public IPricingStrategy getStrategy(PaymentFrequency frequency) {
        return switch (frequency) {
            case MONTHLY    -> new MonthlyStrategy();
            case QUARTERLY  -> new QuarterlyStrategy();
            case BI_ANNUAL  -> new BiAnnualStrategy();
            case ANNUAL     -> new AnnualStrategy();
        };
    }

    public double calculateRent(double baseRate, PaymentFrequency frequency) {
        return getStrategy(frequency).calculateRent(baseRate);
    }

    // Multi-unit discount: 3% off base rate per extra unit beyond the first
    public double applyMultiUnitDiscount(double baseRate, int activeLeaseCount) {
        if (activeLeaseCount <= 1) return baseRate;
        double discount = 0.03 * (activeLeaseCount - 1);
        discount = Math.min(discount, 0.10); // cap at 10%
        return baseRate * (1 - discount);
    }

    public double calculateTotal(double baseRent, double water,
                                  double electricity, double waste) {
        return baseRent + water + electricity + waste;
    }

    public Invoice generateInvoice(int leaseId, LocalDate billingPeriod,
                                    double water, double electricity, double waste,
                                    int tenantActiveLeaseCount) {
        Lease lease = leaseRepository.findById(leaseId);
        if (lease == null)
            throw new RuntimeException("Lease not found: " + leaseId);

        double baseRate = lease.getUnit().getBaseRate();
        double discountedRate = applyMultiUnitDiscount(baseRate, tenantActiveLeaseCount);
        double baseRent = calculateRent(discountedRate, lease.getPaymentFrequency());
        double total = calculateTotal(baseRent, water, electricity, waste);

        Invoice invoice = new Invoice();
        invoice.setLease(lease);
        invoice.setBillingPeriod(billingPeriod);
        invoice.setBaseRent(baseRent);
        invoice.setWaterCharge(water);
        invoice.setElectricityCharge(electricity);
        invoice.setWasteCharge(waste);
        invoice.setTotalAmount(total);
        invoice.setStatus(InvoiceStatus.UNPAID);
        invoiceRepository.save(invoice);
        return invoice;
    }

    public Invoice markAsPaid(int invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId);
        if (invoice == null)
            throw new RuntimeException("Invoice not found: " + invoiceId);
        invoice.setStatus(InvoiceStatus.PAID);
        invoiceRepository.update(invoice);
        return invoice;
    }

    public List<Invoice> getInvoicesForLease(int leaseId) {
        return invoiceRepository.findByLeaseId(leaseId);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }
}
