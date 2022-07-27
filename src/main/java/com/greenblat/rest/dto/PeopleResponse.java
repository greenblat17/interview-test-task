package com.greenblat.rest.dto;

import java.util.List;

public class PeopleResponse {
    private List<PersonResponse> personDTOs;

    public PeopleResponse() {
    }

    public PeopleResponse(List<PersonResponse> personDTOs) {
        this.personDTOs = personDTOs;
    }

    public List<PersonResponse> getPersonDTOs() {
        return personDTOs;
    }

    public void setPersonDTOs(List<PersonResponse> personDTOs) {
        this.personDTOs = personDTOs;
    }
}
