package com.watermelon.dateapp.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    BAD_REQUEST(400, "400-01", "잘못된 요청입니다."),
    INVALID_REQUEST_PARAMETER(400, "400-02", "잘못된 요청 파라미터 입니다."),
    UNAUTHENTICATED(401, "401-01", "인증되지 않은 사용자입니다."),
    INVALID_TOKEN(401, "401-02", "유효하지 않은 토큰입니다."),
    UNAUTHORIZED(403, "403-01", "권한이 없는 사용자입니다."),

    USER_NOT_FOUND(404, "404-01", "사용자를 찾을 수 없습니다."),
    FILE_NOT_FOUND(404, "404-02", "파일을 찾을 수 없습니다."),
    IMAGE_FILE_NOT_EXIST(404, "404-03", "이미지 파일이 존재하지 않습니다."),
    LOCATION_NOT_FOUND(404, "404-04", "지역을 찾을 수 없습니다."),

    METHOD_NOT_ALLOWED(405, "405-01", "지원하지 않는 Http Method 입니다."),
    INTERNAL_PROCESSING_ERROR(500, "500-01", "내부 시스템 에러가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;
}