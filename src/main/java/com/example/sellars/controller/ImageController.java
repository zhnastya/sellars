package com.example.sellars.controller;

import com.example.sellars.models.Image;
import com.example.sellars.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Image imageForProduct = imageRepository.findById(id).orElse(null);
        if (imageForProduct !=null) {
            return ResponseEntity.ok()
                    .header("fileName", imageForProduct.getOriginalFileName())
                    .contentType(MediaType.valueOf(imageForProduct.getContentType()))
                    .contentLength(imageForProduct.getSize())
                    .body(new InputStreamResource(new ByteArrayInputStream(imageForProduct.getBytes())));
        }
        return null;
    }

    @GetMapping("/images/avatar/{id}")
    private ResponseEntity<?> getAvatarById(@PathVariable Long id) {
        Image avatar = imageRepository.findById(id).orElse(null);
        if (avatar !=null) {
            return ResponseEntity.ok()
                    .header("fileName", avatar.getOriginalFileName())
                    .contentType(MediaType.valueOf(avatar.getContentType()))
                    .contentLength(avatar.getSize())
                    .body(new InputStreamResource(new ByteArrayInputStream(avatar.getBytes())));
        }
        return null;
    }
}