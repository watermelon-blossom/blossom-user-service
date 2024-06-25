package com.watermelon.dateapp.service;

import com.watermelon.dateapp.api.dto.UpdateUserRequest;
import com.watermelon.dateapp.domain.user.User;
import com.watermelon.dateapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.watermelon.dateapp.api.dto.CreateUserRequest;
import com.watermelon.dateapp.api.dto.SearchUserRequest;
import com.watermelon.dateapp.api.dto.UserResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;

	@Transactional
	public UserResponse saveUser(CreateUserRequest userRequest) {
		User newUser = User.of(userRequest.userName());
		User savedUser = userRepository.save(newUser);
		return new UserResponse(savedUser);
	}

    public Optional<UserResponse> findById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserResponse(user));
    }

    @Transactional
    public Optional<UserResponse> updateUser(Long id, UpdateUserRequest userRequest) {
        return userRepository.findById(id)
                .map(user -> {
                    user.updateUserName(userRequest.userName());
                    user = userRepository.save(user);
                    return new UserResponse(user);
                });
    }

    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else{
            throw new IllegalArgumentException("User not found");
        }

    }

	public Page<UserResponse> searchUsers(SearchUserRequest searchRequest, Pageable pageable) {
		return userRepository.searchUsers(searchRequest, pageable);
	}
}
