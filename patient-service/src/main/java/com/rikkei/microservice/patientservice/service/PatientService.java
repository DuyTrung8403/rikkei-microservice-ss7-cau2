package com.rikkei.microservice.patientservice.service;

import com.rikkei.microservice.patientservice.dto.PatientRequest;
import com.rikkei.microservice.patientservice.dto.PatientResponse;
import com.rikkei.microservice.patientservice.entity.Patient;
import com.rikkei.microservice.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientResponse createPatient(PatientRequest request) {
        Patient patient = Patient.builder()
                .fullName(request.getFullName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .medicalHistory(request.getMedicalHistory())
                .build();

        Patient savedPatient = patientRepository.save(patient);

        return mapToResponse(savedPatient);
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + id));
        return mapToResponse(patient);
    }

    private PatientResponse mapToResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .fullName(patient.getFullName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .phoneNumber(patient.getPhoneNumber())
                .address(patient.getAddress())
                .medicalHistory(patient.getMedicalHistory())
                .build();
    }
}
