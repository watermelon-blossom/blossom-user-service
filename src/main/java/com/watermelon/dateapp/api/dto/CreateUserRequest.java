package com.watermelon.dateapp.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
	@Size(min = 6, max = 20, message = "아이디는 최소6자, 최대 20자 미만입니다")
	@NotBlank(message = "유저의 아이디는 공백일 수 없습니다.")
	String loginId,
	@Size(min = 6, max = 20, message = "비밀번호는 최소6자, 최대 20자 미만입니다")
	@Size(min = 6, max = 20)
	@NotBlank(message = "유저의 비밀번호는 공백일 수 없습니다.")
	String loginPassword,
	@NotBlank(message = "유저의 이름은 공백일 수 없습니다.")
	String userName,
	String sex,
	Integer age,
	Double lastLatitude,
	Double lastLongitude,
	String location
) {
}
