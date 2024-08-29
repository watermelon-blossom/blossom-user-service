package com.watermelon.dateapp.service;

import com.watermelon.dateapp.api.dto.UserProfileResponse;
import com.watermelon.dateapp.domain.likedislike.UserDislike;
import com.watermelon.dateapp.domain.likedislike.UserLike;
import com.watermelon.dateapp.domain.likedislike.UserRelationshipStatus;
import com.watermelon.dateapp.domain.likedislike.UserSuperLike;
import com.watermelon.dateapp.domain.user.User;
import com.watermelon.dateapp.global.error.ApplicationException;
import com.watermelon.dateapp.global.error.ErrorType;
import com.watermelon.dateapp.repository.UserDislikeRepository;
import com.watermelon.dateapp.repository.UserLikeRepository;
import com.watermelon.dateapp.repository.UserRepository;
import com.watermelon.dateapp.repository.UserSuperLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {
    private final UserRepository userRepository;
    private final UserLikeRepository userLikeRepository;
    private final UserDislikeRepository userDislikeRepository;
    private final UserSuperLikeRepository userSuperLikeRepository;

    public UserProfileResponse findUserProfile(Long userId, Long targetUserId) {
        User fromUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorType.USER_NOT_FOUND));
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ApplicationException(ErrorType.USER_NOT_FOUND));

        double distance = calculateDistanceBetweenUsersByHaversine(fromUser, targetUser);

        UserRelationshipStatus userRelationshipStatus = findUserRelationshipStatus(fromUser, targetUser);

        return new UserProfileResponse(targetUser, distance, userRelationshipStatus);
    }

    public UserRelationshipStatus findUserRelationshipStatus(User fromUser, User targetUser) {
        UserLike userLike = userLikeRepository.findByFromUserAndToUser(fromUser, targetUser)
                .orElse(null);
        if (userLike != null) {
            return UserRelationshipStatus.LIKE;
        }
        UserDislike userDislike = userDislikeRepository.findByFromUserAndToUser(fromUser, targetUser)
                .orElse(null);
        if (userDislike != null) {
            return UserRelationshipStatus.DISLIKE;
        }
        UserSuperLike userSuperLike = userSuperLikeRepository.findByFromUserAndToUser(fromUser, targetUser)
                .orElse(null);
        if (userSuperLike != null) {
            return UserRelationshipStatus.SUPERLIKE;
        }
        return UserRelationshipStatus.NONE;
    }

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
