package com.example.onlineshop.web;

import com.example.onlineshop.model.entity.Picture;
import com.example.onlineshop.service.CloudinaryImage;
import com.example.onlineshop.service.CloudinaryService;
import com.example.onlineshop.service.PictureService;
import com.example.onlineshop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class PictureController {

    private final CloudinaryService cloudinaryService;
    private final PictureService pictureService;
    private final ProductService productService;

    public PictureController(CloudinaryService cloudinaryService, PictureService pictureService,
                             ProductService productService) {
        this.cloudinaryService = cloudinaryService;
        this.pictureService = pictureService;
        this.productService = productService;
    }

    @PostMapping("/pictures/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addPicture(@RequestBody MultipartFile multipartFile,
                                        @RequestParam(value = "folderName") Long productId) throws IOException {
        String folderName = productId.toString();

        CloudinaryImage cloudinaryImage = this.cloudinaryService.upload(multipartFile, folderName);
        Picture picture = this.pictureService.savePicture(cloudinaryImage, folderName);
        this.productService.addPictureByProductId(productId, picture);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/pictures/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String delete(@RequestBody Map<String ,String> info) {
        this.pictureService.deleteByUrl(info.get("url"));

        return "redirect:/pictures";
    }
}
