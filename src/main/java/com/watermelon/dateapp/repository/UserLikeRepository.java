package com.watermelon.dateapp.repository;

import com.watermelon.dateapp.domain.likedislike.UserLike;
import com.watermelon.dateapp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    Optional<UserLike> findByFromUserAndToUser(User fromUser, User toUser);
}
