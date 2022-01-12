package com.epam.brest.service;

import com.epam.brest.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAll();

    Student getStudentById(Integer studentId);

    Integer create(Student student);

    Integer update(Student student);

    Integer delete(Integer studentId);

    Integer count();
}
