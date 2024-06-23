package com.watermelon.dateapp.repository;

import com.watermelon.dateapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
