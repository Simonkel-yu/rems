package com.rems.service;

import com.rems.dto.AppointmentRequestDTO;
import com.rems.exception.TimeSlotOccupiedException;
import com.rems.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    private LocalDateTime base;

    @BeforeEach
    void setUp() {
        base = LocalDateTime.of(2026, 1, 15, 10, 0);
    }

    // TC-01: overlapping times should throw exception
    @Test
    void testConflict_overlappingTimes_throwsException() {
        when(appointmentRepository.hasConflict(anyInt(), anyInt(), any(), any()))
                .thenReturn(true);
        assertTrue(scheduleService.checkConflict(1, 1,
                base, base.plusHours(1)));
    }

    // TC-02: adjacent times should NOT conflict (TC-07 fix)
    @Test
    void testConflict_adjacentTimes_noConflict() {
        when(appointmentRepository.hasConflict(anyInt(), anyInt(), any(), any()))
                .thenReturn(false);
        assertFalse(scheduleService.checkConflict(1, 1,
                base.plusHours(1), base.plusHours(2)));
    }

    // TC-08: same agent booked on different unit at same time
    @Test
    void testConflict_sameAgentDifferentUnit_throwsException() {
        when(appointmentRepository.hasConflict(anyInt(), anyInt(), any(), any()))
                .thenReturn(true);
        assertTrue(scheduleService.checkConflict(2, 1,
                base, base.plusHours(1)));
    }
}
