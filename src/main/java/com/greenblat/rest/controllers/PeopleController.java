package com.greenblat.rest.controllers;

import com.greenblat.rest.dto.PersonDTO;
import com.greenblat.rest.models.Person;
import com.greenblat.rest.services.PeopleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public Long addUser(@RequestBody PersonDTO personDTO) {
        return peopleService.save(modelMapper.map(personDTO, Person.class), personDTO.getImageUri());
    }
}

