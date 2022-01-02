package com.epam.brest;

import com.epam.brest.model.dto.StudentDto;

import java.time.LocalDate;
import java.util.List;

public interface StudentDtoDao {

    List<StudentDto> findAllDto();

    List<StudentDto> findAllWithDate(LocalDate startDate, LocalDate endDate);
}
