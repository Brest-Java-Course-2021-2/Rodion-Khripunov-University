package com.epam.brest.service.impl;

import com.epam.brest.UniversityDao;
import com.epam.brest.model.University;
import com.epam.brest.service.UniversityService;
import com.epam.brest.service.exceptions.UniversityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    private final Logger logger = LogManager.getLogger(UniversityServiceImpl.class);

    private final UniversityDao universityDao;

    public UniversityServiceImpl(UniversityDao universityDao) {
        this.universityDao = universityDao;
    }


    @Override
    @Transactional(readOnly = true)
    public List<University> findAll() {
        logger.debug("findAll()");
        return universityDao.findAll();
    }

    @Override
    @Transactional
    public University getUniversityById(Integer universityId) {
        logger.debug("getUniversityById({})", universityId);
        try {
            return universityDao.getUniversityById(universityId);
        } catch (EmptyResultDataAccessException ex) {
            throw new UniversityNotFoundException(universityId);
        }
    }

    @Override
    @Transactional
    public Integer create(University university) {
        logger.debug("create({})", university);
        return universityDao.create(university);
    }

    @Override
    @Transactional
    public Integer update(University university) {
        logger.debug("update({})", university);
        return universityDao.update(university);
    }

    @Override
    @Transactional
    public Integer delete(Integer universityId) {
        logger.debug("delete({})", universityId);
        return universityDao.delete(universityId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        logger.debug("count()");
        return universityDao.count();
    }
}
