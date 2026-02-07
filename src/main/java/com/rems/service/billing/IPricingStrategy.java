package com.rems.service.billing;

public interface IPricingStrategy {
    double calculateRent(double baseRate);
}
