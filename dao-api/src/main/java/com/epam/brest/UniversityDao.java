package com.epam.brest;

import com.epam.brest.model.University;

import java.util.List;

public interface UniversityDao {

    List<University> findAll();

    Integer create(University university);

    Integer update(University university);

    Integer delete(Integer universityId);

}
