package com.watermelon.dateapp.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.watermelon.dateapp.api.dto.UserLoginRequest;
import com.watermelon.dateapp.api.dto.UserLoginResponse;
import com.watermelon.dateapp.global.support.ApiResponse;
import com.watermelon.dateapp.security.JwtToken;
import com.watermelon.dateapp.service.UserLoginService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserLoginController {
	private final UserLoginService userLoginService;

	@PostMapping("/users/login")
	public ApiResponse<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
		JwtToken result = userLoginService.login(request.loginId(), request.loginPassword());
		return ApiResponse.success(new UserLoginResponse(result));
	}
}
