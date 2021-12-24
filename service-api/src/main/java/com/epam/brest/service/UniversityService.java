package com.epam.brest.service;

import com.epam.brest.model.University;

import java.util.List;

public interface UniversityService {

    List<University> findAll();

    University getUniversityById(Integer universityId);

    Integer create(University university);

    Integer update(University university);

    Integer delete(Integer universityId);

    Integer count();
}
