package com.rems.service;

import com.rems.dto.AppointmentRequestDTO;
import com.rems.exception.TimeSlotOccupiedException;
import com.rems.model.Appointment;
import com.rems.model.Unit;
import com.rems.model.User;
import com.rems.repository.AppointmentRepository;
import com.rems.repository.UnitRepository;
import com.rems.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleService {

    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final UnitRepository unitRepository = new UnitRepository();
    private final UserRepository userRepository = new UserRepository();

    // TC-07 fix: uses strict < and > (not <=) so adjacent slots are allowed
    public boolean checkConflict(int unitId, int agentId,
                                  LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.hasConflict(unitId, agentId, start, end);
    }

    public Appointment bookAppointment(AppointmentRequestDTO dto) {
        Unit unit = unitRepository.findById(dto.getUnitId());
        if (unit == null)
            throw new RuntimeException("Unit not found: " + dto.getUnitId());

        User tenant = userRepository.findById(dto.getTenantId());
        if (tenant == null)
            throw new RuntimeException("Tenant not found: " + dto.getTenantId());

        User agent = userRepository.findById(dto.getAgentId());
        if (agent == null)
            throw new RuntimeException("Agent not found: " + dto.getAgentId());

        if (checkConflict(dto.getUnitId(), dto.getAgentId(),
                          dto.getStartTime(), dto.getEndTime())) {
            throw new TimeSlotOccupiedException(
                "Time slot unavailable. Please select another time.");
        }

        Appointment appointment = new Appointment();
        appointment.setUnit(unit);
        appointment.setTenant(tenant);
        appointment.setAgent(agent);
        appointment.setStartTime(dto.getStartTime());
        appointment.setEndTime(dto.getEndTime());
        appointmentRepository.save(appointment);
        return appointment;
    }

    public List<Appointment> getAppointmentsForUnit(int unitId) {
        return appointmentRepository.findByUnitId(unitId);
    }

    public List<Appointment> getAppointmentsForAgent(int agentId) {
        return appointmentRepository.findByAgentId(agentId);
    }

    public void cancelAppointment(int appointmentId) {
        Appointment appt = appointmentRepository.findById(appointmentId);
        if (appt == null)
            throw new RuntimeException("Appointment not found: " + appointmentId);
        appt.setStatus(com.rems.model.enums.AppointmentStatus.CANCELLED);
        appointmentRepository.update(appt);
    }
}
