package com.watermelon.dateapp.security;

import lombok.Builder;

@Builder
public record JwtToken(
	String grantType,
	String accessToken,
	String refreshToken
	) {
}