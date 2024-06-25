package com.watermelon.dateapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.watermelon.dateapp.api.dto.SearchUserRequest;
import com.watermelon.dateapp.api.dto.UserResponse;

public interface UserSearchRepository {
	Page<UserResponse> searchUsers(SearchUserRequest searchUserRequest, Pageable pageable);
}
