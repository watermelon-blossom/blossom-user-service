package com.watermelon.dateapp.domain.user;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
public class UploadPhoto {
    private String uploadFileName;
    private String storeFileName;

    public UploadPhoto(String uploadPhotoName, String storePhotoName) {
        this.uploadFileName = uploadPhotoName;
        this.storeFileName = storePhotoName;
    }
}
