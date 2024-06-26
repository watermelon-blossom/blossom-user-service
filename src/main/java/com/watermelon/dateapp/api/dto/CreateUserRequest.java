package com.watermelon.dateapp.api.dto;

import com.watermelon.dateapp.domain.user.Sex;
import com.watermelon.dateapp.domain.user.UserName;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "유저의 이름은 공백일 수 없습니다.")
        String userName,
        String sex,
        Integer age,
        Double lastLatitude,
        Double lastLongitude,
        String location
) {
}
