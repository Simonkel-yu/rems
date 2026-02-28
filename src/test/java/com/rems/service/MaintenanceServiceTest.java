package com.rems.service;

import com.rems.model.MaintenanceRequest;
import com.rems.model.enums.MaintenancePriority;
import com.rems.model.enums.MaintenanceStatus;
import com.rems.repository.MaintenanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MaintenanceServiceTest {

    @Mock
    private MaintenanceRepository maintenanceRepository;

    @InjectMocks
    private MaintenanceService maintenanceService;

    // TC-05: Emergency ticket appears first in queue
    @Test
    void testQueue_emergencyFirst() {
        MaintenanceRequest routine = new MaintenanceRequest();
        routine.setPriority(MaintenancePriority.ROUTINE);

        MaintenanceRequest emergency = new MaintenanceRequest();
        emergency.setPriority(MaintenancePriority.EMERGENCY);

        // Repository returns emergency first (sorted by DB query)
        when(maintenanceRepository.findAll())
                .thenReturn(Arrays.asList(emergency, routine));

        List<MaintenanceRequest> queue = maintenanceService.getQueue();
        assertEquals(MaintenancePriority.EMERGENCY, queue.get(0).getPriority());
    }

    // Status update works correctly
    @Test
    void testUpdateStatus_setsCorrectly() {
        MaintenanceRequest request = new MaintenanceRequest();
        request.setPriority(MaintenancePriority.ROUTINE);
        request.setStatus(MaintenanceStatus.OPEN);

        when(maintenanceRepository.findById(1)).thenReturn(request);

        maintenanceService.updateStatus(1, MaintenanceStatus.IN_PROGRESS);
        assertEquals(MaintenanceStatus.IN_PROGRESS, request.getStatus());
    }

    // Charge applied correctly
    @Test
    void testApplyCharge_setsChargeableAndAmount() {
        MaintenanceRequest request = new MaintenanceRequest();
        request.setPriority(MaintenancePriority.ROUTINE);

        when(maintenanceRepository.findById(1)).thenReturn(request);

        maintenanceService.applyCharge(1, 250.00);
        assertTrue(request.isChargeable());
        assertEquals(250.00, request.getChargeAmount(), 0.01);
    }
}
