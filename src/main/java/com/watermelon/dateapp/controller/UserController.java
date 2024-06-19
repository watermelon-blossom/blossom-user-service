package com.watermelon.dateapp.controller;

import com.watermelon.dateapp.service.UserService;
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
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto, UriComponentsBuilder uriBuilder) {
        UserDto savedUserDto = userService.saveUser(userDto);

        URI location = uriBuilder.path("/user/{id}")
                            .buildAndExpand(savedUserDto.getId())
                            .toUri();

        return ResponseEntity.created(location).body(savedUserDto);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<String> getUser(@PathVariable Long id) {
        return ResponseEntity.ok().body("");
    }

}
