package com.watermelon.dateapp.api.dto;

import com.watermelon.dateapp.security.JwtToken;

public record UserLoginResponse(
	JwtToken jwtToken
) {
}
