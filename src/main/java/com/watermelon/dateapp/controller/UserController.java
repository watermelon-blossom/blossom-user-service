package com.watermelon.dateapp.controller;

import com.watermelon.dateapp.domain.User;
import com.watermelon.dateapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/user")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request, UriComponentsBuilder uriBuilder) {
        try {
            CreateUserResponse savedUserDto = userService.saveUser(request);
            URI location = uriBuilder.path("/user/{id}")
                    .buildAndExpand(savedUserDto.id())
                    .toUri();
            return ResponseEntity.created(location).body(savedUserDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User byId = userService.findById(id);
        return ResponseEntity.ok().body(byId);
    }
}