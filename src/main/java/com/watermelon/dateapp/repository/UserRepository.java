package com.watermelon.dateapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.watermelon.dateapp.api.dto.SearchUserRequest;
import com.watermelon.dateapp.api.dto.UserResponse;
import com.watermelon.dateapp.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long>, UserSearchRepository {
	Page<UserResponse> searchUsers(SearchUserRequest searchUserRequest, Pageable pageable);

	Optional<User> findByLoginInfoId(String loginId);
}
