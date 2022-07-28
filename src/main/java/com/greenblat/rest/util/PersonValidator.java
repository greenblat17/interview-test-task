package com.greenblat.rest.util;

import com.greenblat.rest.models.Person;
import com.greenblat.rest.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Objects;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        //Error 1
        Person personById = peopleService.findOne(person.getId());
        if (personById == null || !Objects.equals(personById.getEmail(), person.getEmail())) {
            List<Person> peopleByEmail = peopleService.findUserByEmail(person.getEmail());
            if (!peopleByEmail.isEmpty())
                errors.rejectValue("email", "", "Email should be unique");
        }


        // Error 2
        char firstLetter = person.getUsername().charAt(0);
        if (!Character.isUpperCase(firstLetter))
            errors.rejectValue("username", "", "Username should be start with a capital letter");
    }
}
