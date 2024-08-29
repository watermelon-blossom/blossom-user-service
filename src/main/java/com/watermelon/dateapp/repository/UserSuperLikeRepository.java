package com.watermelon.dateapp.repository;

import com.watermelon.dateapp.domain.likedislike.UserSuperLike;
import com.watermelon.dateapp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSuperLikeRepository extends JpaRepository<UserSuperLike, Long> {
    Optional<UserSuperLike> findByFromUserAndToUser(User fromUser, User toUser);
}
