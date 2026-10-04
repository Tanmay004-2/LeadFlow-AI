package com.leadflow.sales.service;

import com.leadflow.sales.entity.Appointment;
import com.leadflow.sales.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public boolean isTimeSlotAvailable(LocalDateTime requestedStart, LocalDateTime requestedEnd) {
        List<Appointment> overlaps = appointmentRepository.findOverlapping(requestedStart, requestedEnd);
        return overlaps.isEmpty();
    }

    public Appointment bookAppointment(UUID leadId, LocalDateTime startTime) {
        LocalDateTime endTime = startTime.plusMinutes(30);
        if (!isTimeSlotAvailable(startTime, endTime)) {
            throw new IllegalArgumentException("Time slot is not available");
        }
        Appointment apt = new Appointment();
        apt.setLeadId(leadId);
        apt.setStartTime(startTime);
        apt.setEndTime(endTime);
        return appointmentRepository.save(apt);
    }
}
