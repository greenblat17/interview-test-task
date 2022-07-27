package com.greenblat.rest.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uri")
    private String uri;

    @OneToMany(mappedBy = "image")
    private List<Person> people;

    public Image(String filename) {
        this.uri = filename;
    }

    public Image() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String filename) {
        this.uri = filename;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
