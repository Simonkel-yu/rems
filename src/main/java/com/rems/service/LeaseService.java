package com.rems.service;

import com.rems.dto.LeaseDTO;
import com.rems.model.Lease;
import com.rems.model.Unit;
import com.rems.model.User;
import com.rems.model.enums.LeaseStatus;
import com.rems.model.enums.UnitStatus;
import com.rems.repository.LeaseRepository;
import com.rems.repository.UnitRepository;
import com.rems.repository.UserRepository;
import java.util.List;

public class LeaseService {

    private final LeaseRepository leaseRepository = new LeaseRepository();
    private final UnitRepository unitRepository = new UnitRepository();
    private final UserRepository userRepository = new UserRepository();

    public Lease createLease(LeaseDTO dto) {
        Unit unit = unitRepository.findById(dto.getUnitId());
        if (unit == null)
            throw new RuntimeException("Unit not found: " + dto.getUnitId());
        if (unit.getStatus() != UnitStatus.AVAILABLE)
            throw new RuntimeException("Unit is not available for leasing.");

        User tenant = userRepository.findById(dto.getTenantId());
        if (tenant == null)
            throw new RuntimeException("Tenant not found: " + dto.getTenantId());

        Lease lease = new Lease();
        lease.setUnit(unit);
        lease.setTenant(tenant);
        lease.setStartDate(dto.getStartDate());
        lease.setEndDate(dto.getEndDate());
        lease.setPaymentFrequency(dto.getPaymentFrequency());
        lease.setStatus(LeaseStatus.ACTIVE);
        leaseRepository.save(lease);

        unit.setStatus(UnitStatus.LEASED);
        unitRepository.update(unit);

        return lease;
    }

    public List<Lease> getActiveLeases() {
        return leaseRepository.findActive();
    }

    public List<Lease> getLeasesByTenant(int tenantId) {
        return leaseRepository.findByTenantId(tenantId);
    }

    public void terminateLease(int leaseId) {
        Lease lease = leaseRepository.findById(leaseId);
        if (lease == null)
            throw new RuntimeException("Lease not found: " + leaseId);
        lease.setStatus(LeaseStatus.TERMINATED);
        leaseRepository.update(lease);

        Unit unit = lease.getUnit();
        unit.setStatus(UnitStatus.AVAILABLE);
        unitRepository.update(unit);
    }

    public int countActiveLeasesByTenant(int tenantId) {
        return leaseRepository.countActiveLeasesByTenant(tenantId);
    }
}
