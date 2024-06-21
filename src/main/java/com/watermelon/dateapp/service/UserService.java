package com.watermelon.dateapp.service;

import com.watermelon.dateapp.controller.CreateUserRequest;
import com.watermelon.dateapp.controller.UserResponse;
import com.watermelon.dateapp.domain.User;
import com.watermelon.dateapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse saveUser(CreateUserRequest userRequest) {
        User savedUser = userRepository.save(new User(userRequest.userName()));
        return new UserResponse(savedUser);
    }

    public Optional<UserResponse> findById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserResponse(user));
    }
}
