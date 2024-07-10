package com.watermelon.dateapp.service;

import com.watermelon.dateapp.domain.user.UploadPhoto;
import com.watermelon.dateapp.domain.user.User;
import com.watermelon.dateapp.domain.user.UserPhoto;
import com.watermelon.dateapp.global.error.ApplicationException;
import com.watermelon.dateapp.global.error.ErrorType;
import com.watermelon.dateapp.repository.UserPhotoRepository;
import com.watermelon.dateapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPhotoService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final UserRepository userRepository;
    private final UserPhotoRepository userPhotoRepository;

    @Transactional
    public List<String> storePhotos(Long userId, List<MultipartFile> imageFiles) {
        List<String> fileNames = new ArrayList<>();
        if (!validateImageFileExist(userId, imageFiles)) throw new ApplicationException(ErrorType.IMAGE_FILE_NOT_EXIST);
        for (MultipartFile imageFile : imageFiles) {
            if (!imageFile.isEmpty()) {
                fileNames.add(storePhoto(userId, imageFile));
            }
        }

        return fileNames;
    }

    private boolean validateImageFileExist(Long userId, List<MultipartFile> imageFiles) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorType.USER_NOT_FOUND));
        if (imageFiles == null || imageFiles.size() == 0) {
            return false;
        }
        return true;
    }

    @Transactional
    public String storePhoto(Long userId, MultipartFile imageFile) {
        if (imageFile.isEmpty()) { return null; }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorType.USER_NOT_FOUND));

        String originalFilename = imageFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        try {
            imageFile.transferTo(new File(getFullPath(storeFileName)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(ErrorType.INTERNAL_PROCESSING_ERROR);
        }

        userPhotoRepository.save(UserPhoto.of(user, new UploadPhoto(originalFilename, storeFileName)));
        return storeFileName;
    }

    @Transactional
    public void deletePhoto(String file) throws IOException {
        Path path = Paths.get(uploadDir, file);
        if (Files.exists(path)) {
            Files.delete(path);
            userPhotoRepository.deleteByPhotoFileStoreFileName(file);
        } else {
            throw new ApplicationException(ErrorType.FILE_NOT_FOUND);
        }
    }

    public Resource getPhoto(String fileName) throws MalformedURLException {

        return new UrlResource("file:" + getFullPath(fileName));
    }

    private String createStoreFileName(String originalFilename) {
        String extension = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    private String extractExt(String originalFilename) {
        int position = originalFilename.lastIndexOf(".");
        return originalFilename.substring(position + 1);
    }


    private String getFullPath(String fileName) {
        return uploadDir + fileName;
    }
}
