package com.rikkei.microservice.appointmentservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(int status, String message, T data) {
        return BaseResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> error(int status, String message) {
        return BaseResponse.<T>builder()
                .status(status)
                .message(message)
                .data(null)
                .build();
    }
}
