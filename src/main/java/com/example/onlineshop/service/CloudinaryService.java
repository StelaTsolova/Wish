package com.example.onlineshop.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    CloudinaryImage upload(MultipartFile multipartFile, String folderName) throws IOException;

    boolean delete(String publicId);
}
