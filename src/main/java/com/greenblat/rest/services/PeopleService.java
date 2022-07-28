package com.greenblat.rest.services;

import com.greenblat.rest.dto.StatusResponse;
import com.greenblat.rest.models.Image;
import com.greenblat.rest.models.Person;
import com.greenblat.rest.models.enums.Status;
import com.greenblat.rest.repositories.ImagesRepository;
import com.greenblat.rest.repositories.PeopleRepository;
import com.greenblat.rest.util.ImageNotFoundException;
import com.greenblat.rest.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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

    public Person findOne(long id) {
        return peopleRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    public List<Person> findAll(String status, Long timestamp) {
        if (status == null)
            return peopleRepository.findAll();
        else if (timestamp == null)
            return peopleRepository.findByStatus(status.equals("Online") ? Status.ONLINE : Status.OFFLINE);
        else
            return peopleRepository.findByStatusAndTimestamp(status.equals("Online") ? Status.ONLINE : Status.OFFLINE, new Date(timestamp));

    }

    @Transactional
    public Long save(Person person, String imageUri) {
        Image image = imagesRepository.findByUri(imageUri).orElseThrow(ImageNotFoundException::new);

        person.setStatus(Status.OFFLINE);
        person.setUpdatedAt(new Date());
        person.setImage(image);
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
            person.get().setUpdatedAt(new Date());
            peopleRepository.save(person.get());
        } else {
            throw new PersonNotFoundException();
        }

        return response;
    }

    @Transactional
    public Person update(long id, Person updatedPerson, String imageUri) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isEmpty())
            throw new PersonNotFoundException();

        updatedPerson.setId(id);
        updatedPerson.setUpdatedAt(person.get().getUpdatedAt());
        updatedPerson.setStatus(person.get().getStatus());
        updatedPerson.setImage(imagesRepository.findByUri(imageUri).orElseThrow(ImageNotFoundException::new));
        peopleRepository.save(updatedPerson);
        return updatedPerson;

    }

    public Person findUserByEmail(String email) {
        return peopleRepository.findByEmail(email).orElse(null);
    }

}
