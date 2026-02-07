package com.rems.service.billing;

public class BiAnnualStrategy implements IPricingStrategy {
    // 2% discount for bi-annual payment
    @Override
    public double calculateRent(double baseRate) {
        return (baseRate * 6) * 0.98;
    }
}
