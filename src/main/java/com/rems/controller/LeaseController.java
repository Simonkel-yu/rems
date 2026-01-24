package com.rems.controller;

import com.rems.dto.LeaseDTO;
import com.rems.model.Lease;
import com.rems.service.LeaseService;
import java.util.List;

public class LeaseController {

    private final LeaseService leaseService = new LeaseService();

    public Lease createLease(LeaseDTO dto) {
        return leaseService.createLease(dto);
    }

    public List<Lease> getActiveLeases() {
        return leaseService.getActiveLeases();
    }

    public List<Lease> getLeasesByTenant(int tenantId) {
        return leaseService.getLeasesByTenant(tenantId);
    }

    public void terminateLease(int leaseId) {
        leaseService.terminateLease(leaseId);
    }
}
