package com.watermelon.dateapp.domain.user;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
public class UploadPhoto {
    private String uploadPhotoName;
    private String storePhotoName;

    public UploadPhoto(String uploadPhotoName, String storePhotoName) {
        this.uploadPhotoName = uploadPhotoName;
        this.storePhotoName = storePhotoName;
    }
}
