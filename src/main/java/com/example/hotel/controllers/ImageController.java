package com.example.hotel.controllers;

import com.example.hotel.models.Image;
import com.example.hotel.repositories.RoomImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final RoomImageRepository roomImageRepository;

    @GetMapping("/image/{id}")
    public ResponseEntity<List<Image>> getImage(@PathVariable Long id){
        List<Image> images = roomImageRepository.findAllByRoom_Id(id);
        return ResponseEntity.ok(images);
    }
}
