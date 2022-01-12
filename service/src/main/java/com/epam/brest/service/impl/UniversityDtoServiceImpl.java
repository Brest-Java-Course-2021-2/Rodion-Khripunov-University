package com.epam.brest.service.impl;

import com.epam.brest.UniversityDtoDao;
import com.epam.brest.model.dto.UniversityDto;
import com.epam.brest.service.UniversityDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UniversityDtoServiceImpl implements UniversityDtoService {

    private final Logger logger = LogManager.getLogger(UniversityDtoServiceImpl.class);

    private final UniversityDtoDao universityDtoDao;

    public UniversityDtoServiceImpl(UniversityDtoDao universityDtoDao) {
        this.universityDtoDao = universityDtoDao;
    }

    @Override
    @Transactional
    public List<UniversityDto> findAllWithAvgRating() {
        logger.debug("findAllWithAvgRating()");
        return universityDtoDao.findAllWithAvgRating();
    }
}
