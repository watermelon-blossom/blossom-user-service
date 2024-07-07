package com.watermelon.dateapp.api.controller;

import com.watermelon.dateapp.global.support.ApiResponse;
import com.watermelon.dateapp.service.UserPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserPhotoController {

    private final UserPhotoService userPhotoService;

    @PostMapping("/user/photos")
    public ApiResponse<List<String>> storePhoto(
            @RequestParam
            Long userId,
            @RequestParam
            List<MultipartFile> imageFiles
    ) throws IOException {

        List<String> pathList = userPhotoService.storePhotos(userId, imageFiles);
        return ApiResponse.success(pathList);
    }

    @GetMapping("/user/photos/{file}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String file) throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png; image/jpeg");
        Resource photo = userPhotoService.getPhoto(file);
        return ResponseEntity.ok().headers(headers).body(photo);
    }

    @DeleteMapping("/user/photos/{file}")
    public ApiResponse<String> deletePhoto(@PathVariable String file) throws IOException {
        userPhotoService.deletePhoto(file);


        return ApiResponse.success(null);
    }
}
