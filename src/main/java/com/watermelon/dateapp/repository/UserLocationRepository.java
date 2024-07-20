package com.watermelon.dateapp.repository;

import com.watermelon.dateapp.domain.user.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    Optional<UserLocation> findByLocationName(String locationName);
}
