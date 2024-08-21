package com.watermelon.dateapp.api.dto;

import com.watermelon.dateapp.domain.likedislike.UserRelationshipStatus;
import com.watermelon.dateapp.domain.user.*;

import java.util.List;

public record UserProfileResponse(
    Long id,
    String UserName,
    String job,
    String tendency,
    String userDescription,
    List<QuestionInfo> questionInfos,
    Sex sex,
    Integer age,
    Double distance,
    String location,
    List<String> photos,
    UserRelationshipStatus relationshipStatus
){
    public UserProfileResponse(User user, double distance, UserRelationshipStatus relationshipStatus) {
        this (
                user.getId(),
                user.getUserName(),
                user.getJob(),
                user.getTendency().getName(),
                user.getDescription(),
                user.getQuestionInfos(),
                user.getSex(),
                user.getAge(),
                distance,
                user.getLocation().getLocationName(),
                user.getUserPhotoFileNames(),
                relationshipStatus
        );
    }
}
