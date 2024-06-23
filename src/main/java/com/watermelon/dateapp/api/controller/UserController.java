package com.watermelon.dateapp.api.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request,
		UriComponentsBuilder uriBuilder) {
		UserResponse savedUserDto = userService.saveUser(request);
		URI location = uriBuilder.path("/user/{id}")
			.buildAndExpand(savedUserDto.id())
			.toUri();
		return ResponseEntity.created(location).body(savedUserDto);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
		return userService.findById(id)
			.map(userDto -> ResponseEntity.ok().body(userDto))
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/users")
	public ApiResponse<Page<UserResponse>> getUsers(
		SearchUserRequest searchRequest,
		Pageable pageable
	) {
		return ApiResponse.success(userService.searchUsers(searchRequest, pageable));
	}

}