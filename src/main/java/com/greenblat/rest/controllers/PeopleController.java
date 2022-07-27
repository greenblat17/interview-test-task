package com.greenblat.rest.controllers;

import com.greenblat.rest.dto.PeopleResponse;
import com.greenblat.rest.dto.PersonDTO;
import com.greenblat.rest.dto.StatusResponse;
import com.greenblat.rest.models.Person;
import com.greenblat.rest.services.PeopleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public ResponseEntity<String> addUser(@RequestBody PersonDTO personDTO) {
        Long id = peopleService.save(modelMapper.map(personDTO, Person.class), personDTO.getImageUri());
        return ResponseEntity.ok("Id: " + id);
    }

    @GetMapping("/{id}")
    public PersonDTO getUser(@PathVariable int id) {
        Person person = peopleService.findOne(id);
        PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
        personDTO.setImageUri(person.getImage() != null ? person.getImage().getUri() : null);
        return personDTO;
    }

    @GetMapping()
    public PeopleResponse getAllUser(@RequestParam(required = false, name = "status") String status) {
        System.out.println(100);
        List<Person> people = peopleService.findAll(status);
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Person person: people) {
            PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
            personDTO.setImageUri(person.getImage().getUri());
            personDTOList.add(personDTO);
        }
        return new PeopleResponse(personDTOList);
    }

    @GetMapping("/{id}/status")
    public StatusResponse changeStatus(@PathVariable("id") int id) {
        return peopleService.updateStatus(id);
    }
}

