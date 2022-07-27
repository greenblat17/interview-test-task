package com.greenblat.rest.dto;

import java.util.List;

public class PeopleResponse {
    private List<PersonDTO> personDTOs;

    public PeopleResponse() {
    }

    public PeopleResponse(List<PersonDTO> personDTOs) {
        this.personDTOs = personDTOs;
    }

    public List<PersonDTO> getPersonDTOs() {
        return personDTOs;
    }

    public void setPersonDTOs(List<PersonDTO> personDTOs) {
        this.personDTOs = personDTOs;
    }
}
