package com.watermelon.dateapp.domain.user;

import com.watermelon.dateapp.global.common.BaseEntity;
import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

@Entity
public class UserPhoto extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private UploadPhoto photoFile;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static UserPhoto of(User user, UploadPhoto photoFile) {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.photoFile = photoFile;
        userPhoto.user = user;
        return userPhoto;
    }

    public String getStoreFileName() {
        return photoFile.getStoreFileName();
    }

}
