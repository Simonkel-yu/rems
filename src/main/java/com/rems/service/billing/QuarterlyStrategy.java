package com.rems.service.billing;

public class QuarterlyStrategy implements IPricingStrategy {
    @Override
    public double calculateRent(double baseRate) {
        return baseRate * 3;
    }
}
