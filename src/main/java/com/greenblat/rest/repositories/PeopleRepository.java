package com.greenblat.rest.repositories;

import com.greenblat.rest.models.Person;
import com.greenblat.rest.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {

    Optional<Person> findById(Long id);

    List<Person> findByStatus(Status status);

    List<Person> findByUsername(String username);

    List<Person> findByEmail(String email);

    @Query("SELECT p FROM Person p WHERE p.status=?1 AND p.updatedAt>?2")
    List<Person> findByStatusAndTimestamp(Status status, Date date);
}

