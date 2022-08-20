package com.example.onlineshop.service;


import com.example.onlineshop.model.entity.Picture;
import com.example.onlineshop.model.entity.Product;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PictureService {

    Picture savePicture(CloudinaryImage cloudinaryImage, String folderName, Product product);

    void deleteByUrl(String url);
}
