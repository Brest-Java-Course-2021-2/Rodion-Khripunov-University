package com.epam.brest.service.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;

public class UniversityNotFoundException extends EmptyResultDataAccessException {
    public UniversityNotFoundException(Integer id) {
        super("University not found for id: " + id, 1);
    }
}
