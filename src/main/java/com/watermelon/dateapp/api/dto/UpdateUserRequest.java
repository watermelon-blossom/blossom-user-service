package com.watermelon.dateapp.api.dto;

public record UpdateUserRequest(
        String userName,
        String sex,
        Integer age,
        Double lastLatitude,
        Double lastLongitude,
        String location
) {
}
