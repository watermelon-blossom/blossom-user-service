package com.watermelon.dateapp.controller;

import com.watermelon.dateapp.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserResponse(
        @NotNull(message = "생성된 유저의 ID는 공백일 수 없습니다.")
        Long id,
        @NotBlank(message = "생성된 유저의 이름은 공백일 수 없습니다.")
        String userName
) {
    public UserResponse(User user) {
        this(user.getId(), user.getUserName());
    }
}
