package com.greenblat.rest.services;

import com.greenblat.rest.models.Image;
import com.greenblat.rest.models.Person;
import com.greenblat.rest.repositories.ImagesRepository;
import com.greenblat.rest.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final ImagesRepository imagesRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, ImagesRepository imagesRepository) {
        this.peopleRepository = peopleRepository;
        this.imagesRepository = imagesRepository;
    }

    @Transactional
    public Long save(Person user, String imageUri) {
        Optional<Image> image = imagesRepository.findByUri(imageUri);
        if (image.isPresent()) {
            user.setImage(image.get());
            image.get().setUser(user);
        }

        peopleRepository.save(user);
        return user.getId();
    }

    public Person getUserById(long id) {
        return peopleRepository.findById(id).orElse(null);
    }
}
