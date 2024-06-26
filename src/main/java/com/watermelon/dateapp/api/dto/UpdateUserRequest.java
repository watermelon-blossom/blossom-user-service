package com.watermelon.dateapp.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "생성된 유저의 이름은 공백일 수 없습니다.")
        String userName,
        Double lastLatitude,
        Double lastLongitude,
        String location
) {
}
