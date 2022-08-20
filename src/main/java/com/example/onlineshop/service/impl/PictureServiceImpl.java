package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.entity.Picture;
import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.repisotiry.PictureRepository;
import com.example.onlineshop.service.CloudinaryImage;
import com.example.onlineshop.service.CloudinaryService;
import com.example.onlineshop.service.PictureService;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;
    private final ProductService productService;

    public PictureServiceImpl(PictureRepository pictureRepository,
                              CloudinaryService cloudinaryService, ProductService productService) {
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;
        this.productService = productService;
    }

    @Override
    public Picture savePicture(CloudinaryImage cloudinaryImage, String folderName, Product product) {
        Picture picture = new Picture(folderName, cloudinaryImage.getUrl(), cloudinaryImage.getPublicId());
        picture.setProduct(product);

        return this.pictureRepository.save(picture);
    }

    @Override
    public void deleteByUrl(String url) {
        Picture picture = this.pictureRepository.findByUrl(url)
                .orElseThrow(() -> new ObjectNotFoundException("Picture with url " + url + " is not found!"));

        if (this.cloudinaryService.delete(picture.getPublicId(), picture.getFolderName())) {
            this.productService.removePicture(picture);
            this.pictureRepository.delete(picture);
        }
    }
}
