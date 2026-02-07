package com.rems.service.billing;

public class AnnualStrategy implements IPricingStrategy {
    // TC-11 fix: 5% discount applied correctly
    @Override
    public double calculateRent(double baseRate) {
        return (baseRate * 12) * 0.95;
    }
}
