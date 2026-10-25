package com.rikkei.microservice.appointmentservice.controller;

import com.rikkei.microservice.appointmentservice.dto.AppointmentRequest;
import com.rikkei.microservice.appointmentservice.dto.AppointmentResponse;
import com.rikkei.microservice.appointmentservice.dto.BaseResponse;
import com.rikkei.microservice.appointmentservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/appointments", "/api/v1/appointment"})
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<BaseResponse<AppointmentResponse>> createAppointment(@RequestBody AppointmentRequest request) {
        AppointmentResponse response = appointmentService.createAppointment(request);
        BaseResponse<AppointmentResponse> baseResponse = BaseResponse.<AppointmentResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo lịch hẹn khám thành công")
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<AppointmentResponse>>> getAllAppointments() {
        List<AppointmentResponse> appointments = appointmentService.getAllAppointments();
        BaseResponse<List<AppointmentResponse>> baseResponse = BaseResponse.<List<AppointmentResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách lịch hẹn thành công")
                .data(appointments)
                .build();
        return ResponseEntity.ok(baseResponse);
    }
}
