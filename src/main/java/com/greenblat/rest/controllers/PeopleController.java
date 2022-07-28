package com.greenblat.rest.controllers;

import com.greenblat.rest.dto.PeopleResponse;
import com.greenblat.rest.dto.PersonRequest;
import com.greenblat.rest.dto.PersonResponse;
import com.greenblat.rest.dto.StatusResponse;
import com.greenblat.rest.models.Person;
import com.greenblat.rest.services.PeopleService;
import com.greenblat.rest.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
        this.personValidator = personValidator;
    }

    @PostMapping()
    public ResponseEntity<String> addUser(@RequestBody @Valid PersonRequest personDTO, BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);
        
        validationPerson(person, bindingResult);
        Long id = peopleService.save(person, personDTO.getImageUri());
        return ResponseEntity.ok("Id: " + id);
    }

    @PatchMapping("/{id}")
    public PersonResponse updateUser(@PathVariable long id, @RequestBody @Valid PersonRequest personDTO, BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);
        person.setId(id);

        validationPerson(person, bindingResult);

        Person updatePerson = peopleService.update(id, person, personDTO.getImageUri());
        return convertToPersonResponse(updatePerson);
    }

    @GetMapping("/{id}")
    public PersonResponse getUser(@PathVariable int id) {
        Person person = peopleService.findOne(id);

        PersonResponse personResponse = convertToPersonResponse(person);
        personResponse.setImageUri(person.getImage() != null ? person.getImage().getUri() : null);

        return personResponse;
    }

    @GetMapping()
    public PeopleResponse getAllUser(@RequestParam(required = false, name = "status") String status,
                                     @RequestParam(required = false, name = "timestamp") Long timestamp) {
        List<Person> people = peopleService.findAll(status, timestamp);
        List<PersonResponse> peopleResponse = new ArrayList<>();
        for (Person person: people) {
            PersonResponse personResponse = convertToPersonResponse(person);
            personResponse.setImageUri(person.getImage() != null ? person.getImage().getUri() : null);
            peopleResponse.add(personResponse);
        }
        return new PeopleResponse(peopleResponse);
    }

    @GetMapping("/{id}/status")
    public StatusResponse changeStatus(@PathVariable("id") int id) {
        return peopleService.updateStatus(id);
    }


    // convert
    public Person convertToPerson(PersonRequest personRequest) {
        return modelMapper.map(personRequest, Person.class);
    }

    public PersonResponse convertToPersonResponse(Person person) {
        return modelMapper.map(person, PersonResponse.class);
    }

    // validation
    public void validationPerson(Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            StringBuilder error = new StringBuilder();

            for (FieldError field : bindingResult.getFieldErrors()) {
                error
                        .append(field.getField())
                        .append(" - ")
                        .append(field.getDefaultMessage())
                        .append("; ");
            }

            throw new PersonNotCreatedException(error.toString());
        }
    }

    // Exception Handler
    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "User with this ID wasn't found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ImageErrorResponse> handleException(ImageNotFoundException e) {
        ImageErrorResponse response = new ImageErrorResponse(
                "Image with this URI wasn't found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

