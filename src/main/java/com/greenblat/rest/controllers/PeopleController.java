package com.greenblat.rest.controllers;

import com.greenblat.rest.dto.PeopleResponse;
import com.greenblat.rest.dto.PersonDTO;
import com.greenblat.rest.dto.PersonResponse;
import com.greenblat.rest.dto.StatusResponse;
import com.greenblat.rest.models.Person;
import com.greenblat.rest.services.PeopleService;
import com.greenblat.rest.util.PersonErrorResponse;
import com.greenblat.rest.util.PersonNotCreatedException;
import com.greenblat.rest.util.PersonNotFoundException;
import com.greenblat.rest.util.PersonValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.greenblat.rest.util.PersonError.incorrectField;

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
    public ResponseEntity<String> addUser(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        personValidator.validate(modelMapper.map(personDTO, Person.class), bindingResult);
        if (bindingResult.hasErrors())
            incorrectField(bindingResult);

        Long id = peopleService.save(modelMapper.map(personDTO, Person.class), personDTO.getImageUri());
        return ResponseEntity.ok("Id: " + id);
    }

    @GetMapping("/{id}")
    public PersonResponse getUser(@PathVariable int id) {
        Person person = peopleService.findOne(id);
        if (person == null)
            throw new PersonNotFoundException();

        PersonResponse personResponse = modelMapper.map(person, PersonResponse.class);
        personResponse.setImageUri(person.getImage() != null ? person.getImage().getUri() : null);
        return personResponse;
    }

    @GetMapping()
    public PeopleResponse getAllUser(@RequestParam(required = false, name = "status") String status,
                                     @RequestParam(required = false, name = "timestamp") Long timestamp) {
        List<Person> people = peopleService.findAll(status, timestamp);
        List<PersonResponse> peopleResponse = new ArrayList<>();
        for (Person person: people) {
            PersonResponse personResponse = modelMapper.map(person, PersonResponse.class);
            personResponse.setImageUri(person.getImage() != null ? person.getImage().getUri() : null);
            peopleResponse.add(personResponse);
        }
        return new PeopleResponse(peopleResponse);
    }

    @GetMapping("/{id}/status")
    public StatusResponse changeStatus(@PathVariable("id") int id) {
        return peopleService.updateStatus(id);
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
}

