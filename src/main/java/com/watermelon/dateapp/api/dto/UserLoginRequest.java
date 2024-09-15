package com.watermelon.dateapp.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
	@NotBlank
	String loginId,
	@NotBlank
	String loginPassword
) {
}
