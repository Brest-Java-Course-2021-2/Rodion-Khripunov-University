package com.epam.brest.service.impl;

import com.epam.brest.StudentDao;
import com.epam.brest.model.Student;
import com.epam.brest.service.StudentService;
import com.epam.brest.service.exceptions.StudentNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        logger.debug("findAll()");
        return studentDao.findAll();
    }

    @Override
    @Transactional
    public Student getStudentById(Integer studentId) {
        logger.debug("getStudentById({})", studentId);
        try {
            return studentDao.getStudentById(studentId);
        } catch (EmptyResultDataAccessException ex) {
            throw new StudentNotFoundException(studentId);
        }
    }

    @Override
    @Transactional
    public Integer create(Student student) {
        logger.debug("create({})", student);
        return studentDao.create(student);
    }

    @Override
    @Transactional
    public Integer update(Student student) {
        logger.debug("update({})", student);
        return studentDao.update(student);
    }

    @Override
    @Transactional
    public Integer delete(Integer studentId) {
        logger.debug("delete({})", studentId);
        return studentDao.delete(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        logger.debug("count()");
        return studentDao.count();
    }
}
