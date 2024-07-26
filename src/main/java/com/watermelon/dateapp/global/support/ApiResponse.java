package com.watermelon.dateapp.global.support;

import com.watermelon.dateapp.global.error.ErrorMessage;
import com.watermelon.dateapp.global.error.ErrorType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private int statusCode;
    private String code;
    private T data;
    private ErrorMessage error;
    private String debug;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, null, data, null, null);
    }

    public static <T> ApiResponse<T> error(ErrorType errorType, String errorData, String debug) {
        return new ApiResponse<>(
            errorType.getStatus(), errorType.getCode(), null, new ErrorMessage(errorType, errorData), debug);
    }
}