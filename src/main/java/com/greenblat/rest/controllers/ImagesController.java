package com.greenblat.rest.controllers;

import com.greenblat.rest.services.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/images")
public class ImagesController {

    private final ImagesService imagesService;

    @Autowired
    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PostMapping()
    public String uploadImages(@RequestParam("file") MultipartFile file) throws IOException {
        return imagesService.upload(file);
    }
}
