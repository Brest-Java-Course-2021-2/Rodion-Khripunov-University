package com.epam.brest.service;

import com.epam.brest.model.dto.StudentDto;

import java.util.List;

public interface StudentDtoService {

    List<StudentDto> findAllDto();

    List<StudentDto> findAllWithDate(String startDate, String endDate);
}
