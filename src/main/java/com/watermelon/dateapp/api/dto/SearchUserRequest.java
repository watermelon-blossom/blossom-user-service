package com.watermelon.dateapp.api.dto;

import com.watermelon.dateapp.domain.user.Sex;

public record SearchUserRequest(
	Sex sex,
	Integer startAge,
	Integer endAge,
	Double radius,
	Double latitude,
	Double longitude,
	String location
) {
}
