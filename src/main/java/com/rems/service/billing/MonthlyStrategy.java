package com.rems.service.billing;

public class MonthlyStrategy implements IPricingStrategy {
    @Override
    public double calculateRent(double baseRate) {
        return baseRate;
    }
}
