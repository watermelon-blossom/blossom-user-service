package com.watermelon.dateapp.controller;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "유저의 이름은 공백일 수 없습니다.")
        String userName
) {
}
