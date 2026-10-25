package com.rikkei.microservice.appointmentservice.service;

import com.rikkei.microservice.appointmentservice.dto.AppointmentRequest;
import com.rikkei.microservice.appointmentservice.dto.AppointmentResponse;
import com.rikkei.microservice.appointmentservice.dto.BaseResponse;
import com.rikkei.microservice.appointmentservice.entity.Appointment;
import com.rikkei.microservice.appointmentservice.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final RestTemplate restTemplate;

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        // 1. Kiem tra Benh nhan co ton tai tren patient-service khong
        String patientUrl = "http://patient-service/api/v1/patients/" + request.getPatientId();
        try {
            ResponseEntity<BaseResponse> patientRes = restTemplate.getForEntity(patientUrl, BaseResponse.class);
            if (!patientRes.getStatusCode().is2xxSuccessful() || patientRes.getBody() == null || patientRes.getBody().getData() == null) {
                throw new IllegalArgumentException("Bệnh nhân với ID " + request.getPatientId() + " không tồn tại!");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Bệnh nhân với ID " + request.getPatientId() + " không tồn tại!");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi kết nối tới patient-service: " + e.getMessage());
        }

        // 2. Kiem tra Bac si co ton tai tren doctor-service khong
        String doctorUrl = "http://doctor-service/api/v1/doctors/" + request.getDoctorId();
        try {
            ResponseEntity<BaseResponse> doctorRes = restTemplate.getForEntity(doctorUrl, BaseResponse.class);
            if (!doctorRes.getStatusCode().is2xxSuccessful() || doctorRes.getBody() == null || doctorRes.getBody().getData() == null) {
                throw new IllegalArgumentException("Bác sĩ với ID " + request.getDoctorId() + " không tồn tại!");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Bác sĩ với ID " + request.getDoctorId() + " không tồn tại!");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi kết nối tới doctor-service: " + e.getMessage());
        }

        // 3. Neu hop le, tien hanh luu vao Database
        String status = (request.getStatus() != null && !request.getStatus().isBlank())
                ? request.getStatus()
                : "PENDING";

        Appointment appointment = Appointment.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .appointmentDate(request.getAppointmentDate())
                .reason(request.getReason())
                .status(status)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return mapToResponse(savedAppointment);
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .appointmentDate(appointment.getAppointmentDate())
                .reason(appointment.getReason())
                .status(appointment.getStatus())
                .build();
    }
}
