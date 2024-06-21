package com.watermelon.dateapp.service;

import com.watermelon.dateapp.controller.CreateUserRequest;
import com.watermelon.dateapp.controller.CreateUserResponse;
import com.watermelon.dateapp.domain.User;
import com.watermelon.dateapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest userRequest) {
        User savedUser = userRepository.save(new User(userRequest.userName()));
        return new CreateUserResponse(savedUser);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
