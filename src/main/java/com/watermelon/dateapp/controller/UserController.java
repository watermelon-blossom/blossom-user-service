package com.watermelon.dateapp.controller;

import com.watermelon.dateapp.domain.User;
import com.watermelon.dateapp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request, UriComponentsBuilder uriBuilder) {
        try {
            UserResponse savedUserDto = userService.saveUser(request);
            URI location = uriBuilder.path("/user/{id}")
                    .buildAndExpand(savedUserDto.id())
                    .toUri();
            return ResponseEntity.created(location).body(savedUserDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable
            @NotNull(message = "ID는 nul일 수 없습니다.")
            @Min(value = 1, message = "ID는 1 이상의 숫자여야 합니다.")
            Long id
    ) {
        return userService.findById(id)
                .map(userDto -> ResponseEntity.ok().body(userDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable
            @NotNull(message = "ID는 nul일 수 없습니다.")
            @Min(value = 1, message = "ID는 1 이상의 숫자여야 합니다.")
            Long id,
            @Valid @RequestBody
            UpdateUserRequest request
    ) {
        return userService.updateUser(id, request)
                .map(userDto -> ResponseEntity.ok().body(userDto))
                .orElse(ResponseEntity.notFound().build());
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