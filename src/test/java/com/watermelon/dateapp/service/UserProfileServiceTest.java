package com.watermelon.dateapp.service;

import com.watermelon.dateapp.domain.user.*;
import com.watermelon.dateapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserProfileServiceTest {
    @Autowired
    UserProfileService userProfileService;

    @Test
    void calculateDistanceBetweenUsersByHaversine() {
        // Given
        // 가산디지텉단지역
        User fromUser = new User(1L, null, null, null, null, null, 37.48150595804579, 126.88261084079251, null, null, null, null);
        // 광운대역
        User toUser = new User(1L, null, null, null, null, null, 37.62379996979561, 127.06169909443831, null, null, null, null);
        // When
        double distance = userProfileService.calculateDistanceBetweenUsersByHaversine(fromUser, toUser);
        // Then
        Assertions.assertThat(String.format("%.1f", distance)).isEqualTo("22.4");
    }
}