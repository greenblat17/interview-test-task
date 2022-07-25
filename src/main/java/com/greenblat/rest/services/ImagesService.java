package com.greenblat.rest.services;

import com.greenblat.rest.models.Image;
import com.greenblat.rest.repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ImagesService {

    @Value("${upload.path}")
    private String uploadPath;

    private final ImagesRepository imagesRepository;

    @Autowired
    public ImagesService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    @Transactional
    public String upload(MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists())
            uploadDir.mkdir();

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "-" + file.getOriginalFilename();

        String uri = uploadPath + "/" + resultFileName;
        file.transferTo(new File(uri));

        Image image = new Image(uri);
        imagesRepository.save(image);

        return image.getUri();
    }
}
