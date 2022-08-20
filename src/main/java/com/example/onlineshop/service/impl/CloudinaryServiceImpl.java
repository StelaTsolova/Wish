package com.example.onlineshop.service.impl;

import com.cloudinary.Cloudinary;
import com.example.onlineshop.service.CloudinaryImage;
import com.example.onlineshop.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public CloudinaryImage upload(MultipartFile multipartFile, String folderName) throws IOException {
        File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);

        try {
            @SuppressWarnings("unchecked")
            Map<String, String> result = cloudinary.uploader().upload(tempFile, Map.of("folder", folderName));

            String url = result.getOrDefault(URL, "https://cdn2.vectorstock.com/i/1000x1000/82/41/404-error-page-not-found-funny-fat-cat-vector-21288241.jpg");
            String publicId = result.getOrDefault(PUBLIC_ID, "");

            return new CloudinaryImage(url, publicId);
        } finally {
            tempFile.delete();
        }
    }

    @Override
    public boolean delete(String publicId, String folderName) {
        try {
            this.cloudinary.uploader().destroy(publicId, Map.of("folder", folderName));
        } catch (IOException e) {
            return false;
        }

        return true;
    }


}
