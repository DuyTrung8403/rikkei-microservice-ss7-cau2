package com.rikkei.microservice.doctorservice.service;

import com.rikkei.microservice.doctorservice.dto.DoctorRequest;
import com.rikkei.microservice.doctorservice.dto.DoctorResponse;
import com.rikkei.microservice.doctorservice.entity.Doctor;
import com.rikkei.microservice.doctorservice.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ với ID: " + id));
        return mapToResponse(doctor);
    }

    public DoctorResponse createDoctor(DoctorRequest request) {
        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .specialization(request.getSpecialization())
                .experienceYears(request.getExperienceYears())
                .email(request.getEmail())
                .status(request.getStatus())
                .build();

        Doctor savedDoctor = doctorRepository.save(doctor);
        return mapToResponse(savedDoctor);
    }

    private DoctorResponse mapToResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialization(doctor.getSpecialization())
                .experienceYears(doctor.getExperienceYears())
                .email(doctor.getEmail())
                .status(doctor.getStatus())
                .build();
    }
}
