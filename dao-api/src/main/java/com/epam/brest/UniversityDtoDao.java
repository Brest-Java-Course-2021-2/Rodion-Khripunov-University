package com.epam.brest;

import com.epam.brest.model.dto.UniversityDto;

import java.util.List;

public interface UniversityDtoDao {
    List<UniversityDto> findAllWithAvgCourse();
}
