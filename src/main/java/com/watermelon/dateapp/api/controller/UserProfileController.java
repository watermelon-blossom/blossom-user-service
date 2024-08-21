package com.watermelon.dateapp.api.controller;

import com.watermelon.dateapp.api.dto.UserProfileResponse;
import com.watermelon.dateapp.global.support.ApiResponse;
import com.watermelon.dateapp.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping("/profile")
    public ApiResponse<UserProfileResponse> getProfile(@RequestParam Long userId, @RequestParam Long targetUserId) {
        UserProfileResponse userProfileResponse = userProfileService.findUserProfile(userId, targetUserId);
        return ApiResponse.success(userProfileResponse);
    }

}
