package com.example.onlineshop.repisotiry;

import com.example.onlineshop.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    Optional<Picture> findByUrl(String url);
}
