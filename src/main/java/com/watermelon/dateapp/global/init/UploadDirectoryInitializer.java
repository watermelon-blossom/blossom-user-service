package com.watermelon.dateapp.global.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UploadDirectoryInitializer implements CommandLineRunner {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void run(String... args) throws Exception {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
}
