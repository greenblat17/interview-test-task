package com.greenblat.rest.services;

import com.greenblat.rest.dto.StatusResponse;
import com.greenblat.rest.models.Image;
import com.greenblat.rest.models.Person;
import com.greenblat.rest.models.Status;
import com.greenblat.rest.repositories.ImagesRepository;
import com.greenblat.rest.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
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

    public Person getUserById(long id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public Long save(Person person, String imageUri) {
        Optional<Image> image = imagesRepository.findByUri(imageUri);
        image.ifPresent(person::setImage);

        person.setStatus(Status.OFFLINE);
        peopleRepository.save(person);
        return person.getId();
    }

    @Transactional
    public StatusResponse updateStatus(long id) {
        Optional<Person> person = peopleRepository.findById(id);
        StatusResponse response = new StatusResponse();
        if (person.isPresent()) {
            response.setId(id);


            if (person.get().getStatus() == Status.OFFLINE) {
                person.get().setStatus(Status.ONLINE);
                response.setNewStatus(Status.ONLINE);
                response.setOldStatus(Status.OFFLINE);
            } else {
                person.get().setStatus(Status.OFFLINE);
                response.setNewStatus(Status.OFFLINE);
                response.setOldStatus(Status.ONLINE);
            }
            peopleRepository.save(person.get());
        }

        return response;
    }
}
