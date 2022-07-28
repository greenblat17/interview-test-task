package com.greenblat.rest.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class PersonError {
    public static void incorrectField(BindingResult bindingResult) {
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
