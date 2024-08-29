package com.watermelon.dateapp.domain.user;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
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
