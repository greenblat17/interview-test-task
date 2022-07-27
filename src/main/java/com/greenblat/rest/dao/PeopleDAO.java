package com.greenblat.rest.dao;

import com.greenblat.rest.models.Person;
import com.greenblat.rest.models.Status;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Component
public class PeopleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Person> findByStatusAndTimestamp(Status status, Date timestamp) {
        Session session = entityManager.unwrap(Session.class);

        return (List<Person>) session.createQuery("FROM Person WHERE status=:status AND updatedAt>:timestamp")
                .setParameter("status", status).setParameter("timestamp", timestamp)
                .getResultList();
    }
}
