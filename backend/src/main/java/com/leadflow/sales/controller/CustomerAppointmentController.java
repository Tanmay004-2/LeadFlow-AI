package com.leadflow.sales.controller;

     import com.leadflow.chat.entity.Lead;
     import com.leadflow.chat.repository.LeadRepository;
     import com.leadflow.core.api.ApiResponse;
     import com.leadflow.core.tenant.TenantContext;
     import com.leadflow.sales.entity.Appointment;
     import com.leadflow.sales.service.AppointmentService;
     import com.leadflow.security.AuthUser;






     import lombok.Data;
     import lombok.RequiredArgsConstructor;
     import org.springframework.format.annotation.DateTimeFormat;
     import org.springframework.http.ResponseEntity;
     import org.springframework.security.core.annotation.AuthenticationPrincipal;
     import org.springframework.web.bind.annotation.*;

     import java.time.LocalDate;
     import java.time.LocalDateTime;
     import java.time.LocalTime;
     import java.util.ArrayList;
     import java.util.List;

     @RestController
     @RequestMapping("/api/v1/customer/appointments")
     @RequiredArgsConstructor
     public class CustomerAppointmentController {

           private final AppointmentService appointmentService;
           private final LeadRepository leadRepository;

           @GetMapping("/slots")
           public ResponseEntity<ApiResponse<List<LocalDateTime>>> getAvailableSlots(
                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                   @AuthenticationPrincipal AuthUser authUser) {

                TenantContext.setTenantId(authUser.getBusinessId());
                try {
                    List<LocalDateTime> availableSlots = new ArrayList<>();
                    LocalTime time = LocalTime.of(9, 0);
                    LocalTime endOfDay = LocalTime.of(17, 0);

                    while (time.isBefore(endOfDay)) {
                        LocalDateTime slotStart = LocalDateTime.of(date, time);
                        LocalDateTime slotEnd = slotStart.plusMinutes(30);
                        if (appointmentService.isTimeSlotAvailable(slotStart, slotEnd)) {
                            availableSlots.add(slotStart);
                        }
                        time = time.plusMinutes(30);
                    }
                    return ResponseEntity.ok(ApiResponse.success(availableSlots, "Available slots retrieved"));
                } finally {
                    TenantContext.clear();
                }
           }

           @PostMapping
           public ResponseEntity<ApiResponse<Appointment>> bookAppointment(
                   @RequestBody AppointmentRequest request,
                   @AuthenticationPrincipal AuthUser authUser) {

                TenantContext.setTenantId(authUser.getBusinessId());
                try {






                     Lead lead = leadRepository.findBySessionId(authUser.getId())
                             .orElseThrow(() -> new SecurityException("Invalid session context"));

                    Appointment appointment = appointmentService.bookAppointment(lead.getId(), request.getStartTime());
                    return ResponseEntity.ok(ApiResponse.success(appointment, "Appointment booked successfully"));
                } finally {
                    TenantContext.clear();
                }
           }

           @Data
           public static class AppointmentRequest {
               private LocalDateTime startTime;
           }
     }
