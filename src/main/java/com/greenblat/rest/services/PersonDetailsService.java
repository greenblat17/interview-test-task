package com.greenblat.rest.services;

import com.greenblat.rest.models.Image;
import com.greenblat.rest.models.Person;
import com.greenblat.rest.repositories.PeopleRepository;
import com.greenblat.rest.security.PersonDetails;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> person =  peopleRepository.findByEmail(email);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found with this email");


        return new PersonDetails(person.get());
    }
}
