package com.rems.service;

import com.rems.model.enums.PaymentFrequency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BillingServiceTest {

    private BillingService billingService;

    @BeforeEach
    void setUp() {
        billingService = new BillingService();
    }

    // TC-03: Annual rent applies 5% discount
    @Test
    void testCalculateRent_annual_appliesDiscount() {
        double result = billingService.calculateRent(1000.00, PaymentFrequency.ANNUAL);
        assertEquals(11400.00, result, 0.01);
    }

    // TC-10: Monthly rent with utilities sums correctly
    @Test
    void testCalculateTotal_monthly_correctSum() {
        double baseRent = billingService.calculateRent(1000.00, PaymentFrequency.MONTHLY);
        double total = billingService.calculateTotal(baseRent, 50.00, 100.00, 20.00);
        assertEquals(1170.00, total, 0.01);
    }

    // TC-11: Annual discount is applied (not returning raw 12000)
    @Test
    void testCalculateRent_annual_notFullPrice() {
        double result = billingService.calculateRent(1000.00, PaymentFrequency.ANNUAL);
        assertNotEquals(12000.00, result);
        assertEquals(11400.00, result, 0.01);
    }

    // TC-12: Multi-unit discount applied for 3 leases
    @Test
    void testMultiUnitDiscount_threeLeases_appliesDiscount() {
        double discounted = billingService.applyMultiUnitDiscount(1000.00, 3);
        assertEquals(940.00, discounted, 0.01); // 6% off
    }

    // TC-13: calculateTotal returns correct amount
    @Test
    void testCalculateTotal_correctAmount() {
        double total = billingService.calculateTotal(1000.00, 50.00, 100.00, 20.00);
        assertEquals(1170.00, total, 0.01);
    }

    // Quarterly: 3 months no discount
    @Test
    void testCalculateRent_quarterly() {
        double result = billingService.calculateRent(1000.00, PaymentFrequency.QUARTERLY);
        assertEquals(3000.00, result, 0.01);
    }

    // Bi-annual: 2% discount
    @Test
    void testCalculateRent_biAnnual_appliesDiscount() {
        double result = billingService.calculateRent(1000.00, PaymentFrequency.BI_ANNUAL);
        assertEquals(5880.00, result, 0.01);
    }
}
