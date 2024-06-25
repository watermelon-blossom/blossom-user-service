package com.watermelon.dateapp.controller;

import com.watermelon.dateapp.global.error.ErrorType;
import com.watermelon.dateapp.global.support.ApiResponse;
import com.watermelon.dateapp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
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

    @GetMapping("/user/{id}")
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

    @PutMapping("/user/{id}")
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

    @DeleteMapping("/user/{id}")
    public ResponseEntity<UserResponse> deleteUser(
            @PathVariable
            @NotNull(message = "ID는 nul일 수 없습니다.")
            @Min(value = 1, message = "ID는 1 이상의 숫자여야 합니다.")
            Long id
    ) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}