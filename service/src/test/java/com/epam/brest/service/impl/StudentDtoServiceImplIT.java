package com.epam.brest.service.impl;

import com.epam.brest.model.dto.StudentDto;
import com.epam.brest.service.StudentDtoService;
import com.epam.brest.service.config.ServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfig.class})
@PropertySource({"classpath:dao.properties"})
@Transactional
class StudentDtoServiceImplIT {

    @Autowired
    StudentDtoService studentDtoService;

    @Test
    public void shouldFindAllDto() {
        List<StudentDto> students = studentDtoService.findAllDto();
        assertNotNull(students);
        assertTrue(students.size() > 0);
        assertNotNull(students.get(0).getUniversityName());
    }

    @Test
    public void shouldFindAllWithDate() {
        LocalDate startDate = LocalDate.MIN;
        LocalDate endDate = LocalDate.MAX;
        List<StudentDto> students = studentDtoService.findAllWithDate(startDate.toString(), endDate.toString());
        assertNotNull(students);
        assertTrue(students.size() > 0);
        assertNotNull(students.get(0).getUniversityName());
    }
}