package com.watermelon.dateapp.api.controller;

import java.net.URI;

import com.watermelon.dateapp.api.dto.UpdateUserRequest;
import com.watermelon.dateapp.global.error.ErrorType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
	public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request, UriComponentsBuilder uriBuilder) {
		try {
			UserResponse savedUserDto = userService.saveUser(request);
			URI location = uriBuilder.path("/user/{id}")
					.buildAndExpand(savedUserDto.id())
					.toUri();
			return ResponseEntity.created(location).body(ApiResponse.success(savedUserDto));
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.badRequest().body(ApiResponse.error(ErrorType.BAD_REQUEST, exception.getMessage(), null));
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> getUser(
			@PathVariable
			@NotNull(message = "ID는 nul일 수 없습니다.")
			@Min(value = 1, message = "ID는 1 이상의 숫자여야 합니다.")
			Long id
	) {
		return userService.findById(id)
				.map(userResponse -> ResponseEntity.ok().body(ApiResponse.success(userResponse)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ErrorType.USER_NOT_FOUND, "해당 ID의 User는 존재하지 않습니다.", null)));
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> updateUser(
			@PathVariable
			@NotNull(message = "ID는 nul일 수 없습니다.")
			@Min(value = 1, message = "ID는 1 이상의 숫자여야 합니다.")
			Long id,
			@Valid @RequestBody
			UpdateUserRequest request
	) {
		return userService.updateUser(id, request)
				.map(userDto -> ResponseEntity.ok().body(ApiResponse.success(userDto)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ErrorType.USER_NOT_FOUND, "해당 ID의 User는 존재하지 않습니다.", null)));
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> deleteUser(
			@PathVariable
			@NotNull(message = "ID는 nul일 수 없습니다.")
			@Min(value = 1, message = "ID는 1 이상의 숫자여야 합니다.")
			Long id
	) {
		try {
			userService.deleteUser(id);
			return ResponseEntity.ok().body(ApiResponse.success(null));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ErrorType.USER_NOT_FOUND, "해당 ID의 User는 존재하지 않습니다.", null));
		}

	}

	@GetMapping("/users")
	public ApiResponse<Page<UserResponse>> getUsers(
		SearchUserRequest searchRequest,
		Pageable pageable
	) {
		return ApiResponse.success(userService.searchUsers(searchRequest, pageable));
	}

}