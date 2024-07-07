package com.watermelon.dateapp.repository;

import com.watermelon.dateapp.domain.user.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {
    void deleteByPhotoFile_StoreFileName(String storeFileName);
}
