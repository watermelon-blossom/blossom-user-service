package com.watermelon.dateapp.domain.user;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
@Getter
public class UploadPhoto {
    private String uploadFileName;
    private String storeFileName;

    public UploadPhoto(String uploadPhotoName, String storePhotoName) {
        this.uploadFileName = uploadPhotoName;
        this.storeFileName = storePhotoName;
    }
}
