package com.example.onlineshop.service;


import com.example.onlineshop.model.entity.Picture;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PictureService {

    Picture savePicture(CloudinaryImage cloudinaryImage, String folderName);

    void deleteByUrl(String url);
}
