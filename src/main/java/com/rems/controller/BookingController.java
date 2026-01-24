package com.rems.controller;

import com.rems.dto.AppointmentRequestDTO;
import com.rems.exception.TimeSlotOccupiedException;
import com.rems.model.Appointment;
import com.rems.service.ScheduleService;
import java.util.List;

public class BookingController {

    private final ScheduleService scheduleService = new ScheduleService();

    public Appointment requestViewing(AppointmentRequestDTO dto) {
        try {
            return scheduleService.bookAppointment(dto);
        } catch (TimeSlotOccupiedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Booking failed: " + e.getMessage(), e);
        }
    }

    public void cancelViewing(int appointmentId) {
        scheduleService.cancelAppointment(appointmentId);
    }

    public List<Appointment> getViewingsForUnit(int unitId) {
        return scheduleService.getAppointmentsForUnit(unitId);
    }

    public List<Appointment> getViewingsForAgent(int agentId) {
        return scheduleService.getAppointmentsForAgent(agentId);
    }
}
