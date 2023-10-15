package com.example.sellars.controller;

import com.example.sellars.exeption.NotFoundException;
import com.example.sellars.model.Image;
import com.example.sellars.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@Slf4j
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;

    @GetMapping("/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Image imageForProduct = imageRepository.findById(id).orElse(null);
        if (imageForProduct != null) {
            log.info("Отправлена фотография с id - " + id);
            return ResponseEntity.ok()
                    .header("fileName", imageForProduct.getOriginalFileName())
                    .contentType(MediaType.valueOf(imageForProduct.getContentType()))
                    .contentLength(imageForProduct.getSize())
                    .body(new InputStreamResource(new ByteArrayInputStream(imageForProduct.getBytes())));
        }
        throw new NotFoundException("Фотография с id - " + id + "не найдена");
    }

    @GetMapping("/avatar/{id}")
    private ResponseEntity<?> getAvatarById(@PathVariable Long id) {
        Image avatar = imageRepository.findById(id).orElse(null);
        if (avatar != null) {
            log.info("Отправлена аватарка с id - " + id);
            return ResponseEntity.ok()
                    .header("fileName", avatar.getOriginalFileName())
                    .contentType(MediaType.valueOf(avatar.getContentType()))
                    .contentLength(avatar.getSize())
                    .body(new InputStreamResource(new ByteArrayInputStream(avatar.getBytes())));
        }
        throw new NotFoundException("Аватарка с id - " + id + "не найдена");
    }
}