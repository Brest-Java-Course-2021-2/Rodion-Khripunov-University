package com.epam.brest.service;

import com.epam.brest.model.dto.UniversityDto;

import java.util.List;

public interface UniversityDtoService {
    List<UniversityDto> findAllWithAvgRating();
}
