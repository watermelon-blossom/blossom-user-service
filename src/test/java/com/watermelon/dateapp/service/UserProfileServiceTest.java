package com.watermelon.dateapp.service;

import com.watermelon.dateapp.domain.likedislike.UserDislike;
import com.watermelon.dateapp.domain.likedislike.UserLike;
import com.watermelon.dateapp.domain.likedislike.UserRelationshipStatus;
import com.watermelon.dateapp.domain.likedislike.UserSuperLike;
import com.watermelon.dateapp.domain.user.User;
import com.watermelon.dateapp.repository.UserDislikeRepository;
import com.watermelon.dateapp.repository.UserLikeRepository;
import com.watermelon.dateapp.repository.UserSuperLikeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;


@SpringBootTest
class UserProfileServiceTest {
    @Autowired
    UserProfileService userProfileService;
    @MockBean
    UserLikeRepository userLikeRepository;
    @MockBean
    UserDislikeRepository userDislikeRepository;
    @MockBean
    UserSuperLikeRepository userSuperLikeRepository;


    User fromUser;
    User toUser;

    @BeforeEach
    void setUp() {
        // 가산디지털단지역
        fromUser = new User(1L, null, null, null, null, null, null, 37.48150595804579, 126.88261084079251, null, null, null, null);
        // 광운대역
        toUser = new User(1L, null, null, null, null, null, null, 37.62379996979561, 127.06169909443831, null, null, null, null);
    }


    @Test
    void calculateDistanceBetweenUsersByHaversine() {
        // Given
        // When
        double distance = userProfileService.calculateDistanceBetweenUsersByHaversine(fromUser, toUser);
        // Then
        Assertions.assertThat(String.format("%.1f", distance)).isEqualTo("22.4");
    }

    @Test
    void findUserRelationshipStatus_LIKE_테스트() {
        // Given
        BDDMockito.given(userLikeRepository.findByFromUserAndToUser(fromUser, toUser)).willReturn(Optional.of(new UserLike(1L, fromUser, toUser)));
        // When
        UserRelationshipStatus userRelationshipStatus = userProfileService.findUserRelationshipStatus(fromUser, toUser);
        // Then
        Assertions.assertThat(userRelationshipStatus).isEqualTo(UserRelationshipStatus.LIKE);
    }

    @Test
    void findUserRelationshipStatus_DISLIKE_테스트() {
        //given
        BDDMockito.given(userDislikeRepository.findByFromUserAndToUser(fromUser, toUser)).willReturn(Optional.of(new UserDislike(1L, fromUser, toUser)));
        //when
        UserRelationshipStatus userRelationshipStatus = userProfileService.findUserRelationshipStatus(fromUser, toUser);
        //then
        Assertions.assertThat(userRelationshipStatus).isEqualTo(UserRelationshipStatus.DISLIKE);
    }

    @Test
    void findUserRelationshipStatus_SUPERLIKE_테스트() {
        //given
        BDDMockito.given(userSuperLikeRepository.findByFromUserAndToUser(fromUser, toUser)).willReturn(Optional.of(new UserSuperLike(1L, fromUser, toUser)));
        //when
        UserRelationshipStatus userRelationshipStatus = userProfileService.findUserRelationshipStatus(fromUser, toUser);
        //then
        Assertions.assertThat(userRelationshipStatus).isEqualTo(UserRelationshipStatus.SUPERLIKE);
    }

    @Test
    void findUserRelationshipStatus_NONE_테스트() {
        //given
        //when
        UserRelationshipStatus userRelationshipStatus = userProfileService.findUserRelationshipStatus(fromUser, toUser);
        //then
        Assertions.assertThat(userRelationshipStatus).isEqualTo(UserRelationshipStatus.NONE);
    }
}