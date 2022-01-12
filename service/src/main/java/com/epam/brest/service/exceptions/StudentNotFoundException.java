package com.epam.brest.service.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;

public class StudentNotFoundException extends EmptyResultDataAccessException {

    public StudentNotFoundException(Integer id) {
        super("Student not found for id: " + id, 1);
    }
}
