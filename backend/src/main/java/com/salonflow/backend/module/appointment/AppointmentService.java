package com.salonflow.backend.module.appointment;

import com.salonflow.backend.infra.TenantContext;
import com.salonflow.backend.infra.exception.BusinessException;
import com.salonflow.backend.module.appointment.dto.AppointmentRequest;
import com.salonflow.backend.module.appointment.dto.AppointmentResponse;
import com.salonflow.backend.module.customer.Customer;
import com.salonflow.backend.module.customer.CustomerRepository;
import com.salonflow.backend.module.professional.Professional;
import com.salonflow.backend.module.professional.ProfessionalRepository;
import com.salonflow.backend.module.service.ServiceRepository;
import com.salonflow.backend.module.serviceduration.ServiceDuration;
import com.salonflow.backend.module.serviceduration.ServiceDurationRepository;
import com.salonflow.backend.module.serviceduration.dto.ServiceDurationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private static final int BUFFER_MINUTES = 5;

    private final AppointmentRepository appointmentRepository;
    private final ProfessionalRepository professionalRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceDurationRepository serviceDurationRepository;
    private final CustomerRepository customerRepository;
    private final com.salonflow.backend.module.blockedslots.BlockedSlotsRepository blockedSlotRepository;

    @Transactional
    public AppointmentResponse create(AppointmentRequest request) {
        UUID tenantId = TenantContext.getCurrentTenant();


        if (request.scheduledAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException(
                    "Its not possible create a appointment in the past",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

        Professional professional = professionalRepository
                .findByIdAndTenantId(request.professionalId(), tenantId)
                .orElseThrow(() -> new BusinessException(
                        "Professional not found",
                        HttpStatus.NOT_FOUND
                ));

        if (!professional.getActive()) {
            throw new BusinessException(
                    "Professional inactive",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }


        com.salonflow.backend.module.service.Service service = serviceRepository
                .findByIdAndTenantId(request.serviceId(), tenantId)
                .orElseThrow(() -> new BusinessException(
                        "Service not found",
                        HttpStatus.NOT_FOUND
                ));


        ServiceDuration serviceDuration = serviceDurationRepository
                .findByProfessionalIdAndServiceIdAndTenantId(
                        request.professionalId(), request.serviceId(), tenantId)
                .orElseThrow(() -> new BusinessException(
                        "This professional does not the service requested",
                        HttpStatus.UNPROCESSABLE_ENTITY
                ));

        LocalDateTime start = request.scheduledAt();
        LocalDateTime end = start
                .plusMinutes(serviceDuration.getDurationMinutes())
                .plusMinutes(BUFFER_MINUTES);

        List<Appointment> conflicts = appointmentRepository.findConflicting(
                request.professionalId(), tenantId, start, end
        );

        if (!conflicts.isEmpty()) {
            throw new BusinessException(
                    "This professional has no available time slot",
                    HttpStatus.CONFLICT
            );
        }

        List<com.salonflow.backend.module.blockedslots.BlockedSlots> blockedConflicts =
                blockedSlotRepository.findOverLapping(request.professionalId(), tenantId, start, end);

        if (!blockedConflicts.isEmpty()) {
            throw new BusinessException(
                    "Professional unavailable at this time",
                    HttpStatus.CONFLICT
            );
        }


        Customer customer = customerRepository
                .findByPhoneAndTenantId(request.customerPhone(), tenantId)
                .map(existing -> {
                    if (!existing.getName().equals(request.customerName())) {
                        existing.setName(request.customerName());
                        return customerRepository.save(existing);
                    }
                    return existing;
                })
                .orElseGet(() -> customerRepository.save(
                        Customer.builder()
                                .name(request.customerName())
                                .phone(request.customerPhone())
                                .build()
                ));

        Appointment appointment = Appointment.builder()
                .professional(professional)
                .customer(customer)
                .service(service)
                .scheduledAt(request.scheduledAt())
                .durationMinutes(serviceDuration.getDurationMinutes())
                .price(serviceDuration.getPrice())
                .status(AppointmentStatus.CONFIRMED)
                .notes(request.notes())
                .build();

        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAll() {
        return appointmentRepository
                .findAllByTenantIdOrderByScheduledAtDesc(TenantContext.getCurrentTenant())
                .stream()
                .map(AppointmentResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findByProfessional(UUID professionalId) {
        return appointmentRepository
                .findAllByProfessionalIdAndTenantIdOrderByScheduledAtAsc(
                        professionalId, TenantContext.getCurrentTenant())
                .stream()
                .map(AppointmentResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponse findById(UUID id) {
        return appointmentRepository
                .findByIdAndTenantId(id, TenantContext.getCurrentTenant())
                .map(AppointmentResponse::from)
                .orElseThrow(() -> new BusinessException(
                        "Appointment not found",
                        HttpStatus.NOT_FOUND
                ));
    }

    @Transactional
    public AppointmentResponse cancel(UUID id) {
        Appointment appointment = appointmentRepository
                .findByIdAndTenantId(id, TenantContext.getCurrentTenant())
                .orElseThrow(() -> new BusinessException(
                        "Appointment not found",
                        HttpStatus.NOT_FOUND
                ));

        if (appointment.getStatus() == AppointmentStatus.CANCELED) {
            throw new BusinessException(
                    "Appointment its already cancelled",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

        appointment.setStatus(AppointmentStatus.CANCELED);
        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    @Transactional(readOnly = true)
    public ServiceDurationResponse findByIdAndService (UUID professionalId, UUID serviceId){

        return serviceDurationRepository.findByProfessionalIdAndServiceIdAndTenantId(
                professionalId, serviceId, TenantContext.getCurrentTenant())
                .map(ServiceDurationResponse::from)
                .orElseThrow(() -> new BusinessException(
                        "This professional does not do the service request",
                        HttpStatus.NOT_FOUND
                ));
    }
}

