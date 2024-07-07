package com.watermelon.dateapp.global.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadDirectoryInitializer implements CommandLineRunner {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void run(String... args) throws Exception {
        Path dirPath = Paths.get(uploadDir);
        Files.createDirectories(dirPath);
    }
}
