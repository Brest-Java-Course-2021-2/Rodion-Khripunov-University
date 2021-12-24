package com.epam.brest.service.impl;

import com.epam.brest.UniversityDtoDao;
import com.epam.brest.model.dto.UniversityDto;
import com.epam.brest.service.UniversityDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UniversityDtoServiceImpl implements UniversityDtoService {

    private final UniversityDtoDao universityDtoDao;

    public UniversityDtoServiceImpl(UniversityDtoDao universityDtoDao) {
        this.universityDtoDao = universityDtoDao;
    }

    @Override
    public List<UniversityDto> findAllWithAvgRating() {
        return universityDtoDao.findAllWithAvgRating();
    }
}
