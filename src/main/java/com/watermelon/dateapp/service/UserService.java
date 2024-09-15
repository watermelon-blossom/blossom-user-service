package com.watermelon.dateapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.watermelon.dateapp.api.dto.CreateUserRequest;
import com.watermelon.dateapp.api.dto.SearchUserRequest;
import com.watermelon.dateapp.api.dto.UpdateUserRequest;
import com.watermelon.dateapp.api.dto.UserResponse;
import com.watermelon.dateapp.domain.user.Sex;
import com.watermelon.dateapp.domain.user.User;
import com.watermelon.dateapp.domain.user.UserLocation;
import com.watermelon.dateapp.global.error.ApplicationException;
import com.watermelon.dateapp.global.error.ErrorType;
import com.watermelon.dateapp.repository.UserLocationRepository;
import com.watermelon.dateapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;
	private final UserLocationRepository locationRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public UserResponse saveUser(CreateUserRequest userRequest) {
		UserLocation location = locationRepository.findByLocationName(userRequest.location())
			.orElseThrow(() -> new ApplicationException(ErrorType.LOCATION_NOT_FOUND));

		User newUser = User.of(
			userRequest.loginId(),
			passwordEncoder.encode(userRequest.loginPassword()),
			userRequest.userName(),
			Sex.valueOf(userRequest.sex()),
			userRequest.age(),
			userRequest.lastLatitude(),
			userRequest.lastLongitude(),
			location
		);
		User savedUser = userRepository.save(newUser);
		return new UserResponse(savedUser);
	}

	public UserResponse findById(Long id) {
		return userRepository.findById(id)
			.map(UserResponse::new)
			.orElseThrow(() -> new ApplicationException(ErrorType.USER_NOT_FOUND));
	}

	public User findByLoginInfoId(String userId) {
		return userRepository.findByLoginInfoId(userId)
			.orElseThrow(() -> new ApplicationException(ErrorType.USER_NOT_FOUND));
	}

	@Transactional
	public UserResponse updateUser(Long id, UpdateUserRequest userRequest) {
		UserLocation findLocation = locationRepository.findByLocationName(userRequest.location())
			.orElseThrow(() -> new ApplicationException(ErrorType.LOCATION_NOT_FOUND));
		return userRepository.findById(id)
			.map(user -> {
				user.updateUser(
					userRequest.userName(),
					userRequest.lastLatitude(),
					userRequest.lastLongitude(),
					findLocation
				);
				user = userRepository.save(user);
				return new UserResponse(user);
			})
			.orElseThrow(() -> new ApplicationException(ErrorType.USER_NOT_FOUND));
	}

	@Transactional
	public void deleteUser(Long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
		} else {
			throw new ApplicationException(ErrorType.USER_NOT_FOUND);
		}

	}

	public Page<UserResponse> searchUsers(SearchUserRequest searchRequest, Pageable pageable) {
		return userRepository.searchUsers(searchRequest, pageable);
	}

}
