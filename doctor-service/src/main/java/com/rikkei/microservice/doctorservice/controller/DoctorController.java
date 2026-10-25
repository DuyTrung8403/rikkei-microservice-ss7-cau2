package com.rikkei.microservice.doctorservice.controller;

import com.rikkei.microservice.doctorservice.dto.BaseResponse;
import com.rikkei.microservice.doctorservice.dto.DoctorRequest;
import com.rikkei.microservice.doctorservice.dto.DoctorResponse;
import com.rikkei.microservice.doctorservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<DoctorResponse>>> getAllDoctors() {
        List<DoctorResponse> doctors = doctorService.getAllDoctors();
        BaseResponse<List<DoctorResponse>> baseResponse = BaseResponse.<List<DoctorResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách bác sĩ thành công")
                .data(doctors)
                .build();
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<DoctorResponse>> getDoctorById(@PathVariable Long id) {
        DoctorResponse response = doctorService.getDoctorById(id);
        BaseResponse<DoctorResponse> baseResponse = BaseResponse.<DoctorResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy thông tin bác sĩ thành công")
                .data(response)
                .build();
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<DoctorResponse>> createDoctor(@RequestBody DoctorRequest request) {
        DoctorResponse response = doctorService.createDoctor(request);
        BaseResponse<DoctorResponse> baseResponse = BaseResponse.<DoctorResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Thêm mới bác sĩ thành công")
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }
}
