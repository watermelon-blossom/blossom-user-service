package com.watermelon.dateapp.api.controller;

import com.watermelon.dateapp.api.dto.UpdateUserRequest;
import com.watermelon.dateapp.global.error.ErrorType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.watermelon.dateapp.api.dto.CreateUserRequest;
import com.watermelon.dateapp.api.dto.SearchUserRequest;
import com.watermelon.dateapp.api.dto.UserResponse;
import com.watermelon.dateapp.global.support.ApiResponse;
import com.watermelon.dateapp.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/users")
	public ApiResponse<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
		UserResponse savedUserDto = userService.saveUser(request);
		return ApiResponse.success(savedUserDto);
	}

	@GetMapping("/users/{id}")
	public ApiResponse<UserResponse> getUser(
			@PathVariable
			@NotNull(message = "ID는 nul일 수 없습니다.")
			@Min(value = 1, message = "ID는 1 이상의 숫자여야 합니다.")
			Long id
	) {
		UserResponse findUser = userService.findById(id);
		return ApiResponse.success(findUser);
	}

	@PutMapping("/users/{id}")
	public ApiResponse<UserResponse> updateUser(
			@PathVariable
			@NotNull(message = "ID는 nul일 수 없습니다.")
			@Min(value = 1, message = "ID는 1 이상의 숫자여야 합니다.")
			Long id,
			@Valid @RequestBody
			UpdateUserRequest request
	) {
		UserResponse userResponse = userService.updateUser(id, request);
		return ApiResponse.success(userResponse);
	}

	@DeleteMapping("/users/{id}")
	public ApiResponse<UserResponse> deleteUser(
			@PathVariable
			@NotNull(message = "ID는 nul일 수 없습니다.")
			@Min(value = 1, message = "ID는 1 이상의 숫자여야 합니다.")
			Long id
	) {
		userService.deleteUser(id);
		return ApiResponse.success(null);
	}

	@GetMapping("/users")
	public ApiResponse<Page<UserResponse>> getUsers(
		SearchUserRequest searchRequest,
		Pageable pageable
	) {
		return ApiResponse.success(userService.searchUsers(searchRequest, pageable));
	}

}