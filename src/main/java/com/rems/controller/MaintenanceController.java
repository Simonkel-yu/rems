package com.rems.controller;

import com.rems.model.MaintenanceRequest;
import com.rems.model.enums.MaintenancePriority;
import com.rems.model.enums.MaintenanceStatus;
import com.rems.service.MaintenanceService;
import java.util.List;

public class MaintenanceController {

    private final MaintenanceService maintenanceService = new MaintenanceService();

    public MaintenanceRequest submitRequest(int unitId, int tenantId,
                                             String description,
                                             MaintenancePriority priority) {
        return maintenanceService.submitRequest(unitId, tenantId, description, priority);
    }

    public List<MaintenanceRequest> getQueue() {
        return maintenanceService.getQueue();
    }

    public List<MaintenanceRequest> getMyRequests(int tenantId) {
        return maintenanceService.getRequestsByTenant(tenantId);
    }

    public MaintenanceRequest updateStatus(int ticketId, MaintenanceStatus status) {
        return maintenanceService.updateStatus(ticketId, status);
    }

    public MaintenanceRequest applyCharge(int ticketId, double amount) {
        return maintenanceService.applyCharge(ticketId, amount);
    }
}
