package com.watermelon.dateapp.service;

import com.watermelon.dateapp.api.dto.UserProfileResponse;
import com.watermelon.dateapp.domain.likedislike.UserRelationshipStatus;
import com.watermelon.dateapp.domain.user.User;
import com.watermelon.dateapp.global.error.ApplicationException;
import com.watermelon.dateapp.global.error.ErrorType;
import com.watermelon.dateapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {
    public double calculateDistanceBetweenUsersByHaversine(User fromUser, User toUser) {
        double earthRadius = 6371; // 지구 반지름(km)

        // 경도변화량 라디안으로 변환
        double dLon = Math.toRadians(Math.abs(fromUser.getLastLongitude() - toUser.getLastLongitude()));
        // 위도변화량 라디안으로 변환
        double dLat = Math.toRadians(Math.abs(fromUser.getLastLatitude() - toUser.getLastLatitude()));

        // 하버사인 공식 적용
        double a = Math.sin(dLat/2)*Math.sin(dLat/2) +
                Math.cos(Math.toRadians(fromUser.getLastLatitude()))*Math.cos(Math.toRadians(toUser.getLastLatitude())) *
                Math.sin(dLon/2)*Math.sin(dLon/2);
        double distance = 2 * earthRadius * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        distance = Math.round(distance * 10) / 10.0;

        return distance;
    }
}
