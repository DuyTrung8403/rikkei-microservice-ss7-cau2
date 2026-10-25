package com.rikkei.microservice.patientservice.controller;

import com.rikkei.microservice.patientservice.dto.BaseResponse;
import com.rikkei.microservice.patientservice.dto.PatientRequest;
import com.rikkei.microservice.patientservice.dto.PatientResponse;
import com.rikkei.microservice.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<BaseResponse<PatientResponse>> createPatient(@RequestBody PatientRequest request) {
        PatientResponse response = patientService.createPatient(request);
        BaseResponse<PatientResponse> baseResponse = BaseResponse.<PatientResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Thêm mới bệnh nhân thành công")
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PatientResponse>> getPatientById(@PathVariable Long id) {
        PatientResponse response = patientService.getPatientById(id);
        BaseResponse<PatientResponse> baseResponse = BaseResponse.<PatientResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy thông tin bệnh nhân thành công")
                .data(response)
                .build();
        return ResponseEntity.ok(baseResponse);
    }
}
