package com.watermelon.dateapp.repository;

import com.watermelon.dateapp.domain.likedislike.UserDislike;
import com.watermelon.dateapp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDislikeRepository extends JpaRepository<UserDislike, Long> {
    Optional<UserDislike> findByFromUserAndToUser(User fromUser, User toUser);
}
