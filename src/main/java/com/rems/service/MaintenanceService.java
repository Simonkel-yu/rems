package com.rems.service;

import com.rems.model.MaintenanceRequest;
import com.rems.model.Unit;
import com.rems.model.User;
import com.rems.model.enums.MaintenancePriority;
import com.rems.model.enums.MaintenanceStatus;
import com.rems.repository.MaintenanceRepository;
import com.rems.repository.UnitRepository;
import com.rems.repository.UserRepository;
import java.util.List;

public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository = new MaintenanceRepository();
    private final UnitRepository unitRepository = new UnitRepository();
    private final UserRepository userRepository = new UserRepository();

    public MaintenanceRequest submitRequest(int unitId, int tenantId,
                                             String description,
                                             MaintenancePriority priority) {
        Unit unit = unitRepository.findById(unitId);
        if (unit == null)
            throw new RuntimeException("Unit not found: " + unitId);

        User tenant = userRepository.findById(tenantId);
        if (tenant == null)
            throw new RuntimeException("Tenant not found: " + tenantId);

        MaintenanceRequest request = new MaintenanceRequest();
        request.setUnit(unit);
        request.setTenant(tenant);
        request.setDescription(description);
        request.setPriority(priority);
        request.setStatus(MaintenanceStatus.OPEN);
        maintenanceRepository.save(request);
        return request;
    }

    // Returns queue: EMERGENCY tickets always first, then ROUTINE FIFO
    public List<MaintenanceRequest> getQueue() {
        return maintenanceRepository.findAll();
    }

    public List<MaintenanceRequest> getRequestsByTenant(int tenantId) {
        return maintenanceRepository.findByTenantId(tenantId);
    }

    public MaintenanceRequest updateStatus(int ticketId, MaintenanceStatus status) {
        MaintenanceRequest request = maintenanceRepository.findById(ticketId);
        if (request == null)
            throw new RuntimeException("Ticket not found: " + ticketId);
        request.setStatus(status);
        maintenanceRepository.update(request);
        return request;
    }

    public MaintenanceRequest applyCharge(int ticketId, double amount) {
        MaintenanceRequest request = maintenanceRepository.findById(ticketId);
        if (request == null)
            throw new RuntimeException("Ticket not found: " + ticketId);
        request.setChargeable(true);
        request.setChargeAmount(amount);
        maintenanceRepository.update(request);
        return request;
    }
}
