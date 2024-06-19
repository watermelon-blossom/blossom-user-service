package com.watermelon.dateapp.service;

import com.watermelon.dateapp.controller.UserDto;
import com.watermelon.dateapp.domain.User;
import com.watermelon.dateapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto saveUser(UserDto userDto) {
        User savedUser = userRepository.save(new User(userDto.getUserName()));
        return new UserDto(savedUser.getId(), savedUser.getUserName());
    }
}
