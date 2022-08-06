package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.entity.Size;
import com.example.onlineshop.repisotiry.SizeRepository;
import com.example.onlineshop.service.SizeService;
import com.example.onlineshop.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;

    public SizeServiceImpl(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public Size getSizeByName(String name) {
        name = name.toUpperCase();

        Optional<Size> size = this.sizeRepository.findByName(name);
        if(size.isPresent()){
            return size.get();
        }

        Size newSize = new Size();
        newSize.setName(name);

        return this.sizeRepository.save(newSize);
    }

//    @Override
//    public int getQuantityBySizeName(String sizeName) {
//        return this.sizeRepository.findByName(sizeName)
//                .orElseThrow(() -> new ObjectNotFoundException("Size with name " + sizeName + " is not found!"));
//
//    }
}
