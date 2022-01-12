package com.epam.brest.service.impl;

import com.epam.brest.StudentDtoDao;
import com.epam.brest.model.dto.StudentDto;
import com.epam.brest.service.StudentDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentDtoServiceImpl implements StudentDtoService {

    private final Logger logger = LogManager.getLogger(StudentDtoServiceImpl.class);

    private final StudentDtoDao studentDtoDao;

    public StudentDtoServiceImpl(StudentDtoDao studentDtoDao) {
        this.studentDtoDao = studentDtoDao;
    }

    @Override
    @Transactional
    public List<StudentDto> findAllDto() {
        logger.debug("findAllDto()");
        return studentDtoDao.findAllDto();
    }

    @Override
    @Transactional
    public List<StudentDto> findAllWithDate(String startDate, String endDate) {
        logger.debug("findAllWithDate({}, {})", startDate, endDate);
        return studentDtoDao.findAllWithDate(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }
}
