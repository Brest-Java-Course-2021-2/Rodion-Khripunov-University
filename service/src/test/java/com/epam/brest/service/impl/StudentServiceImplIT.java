package com.epam.brest.service.impl;

import com.epam.brest.model.Student;
import com.epam.brest.model.University;
import com.epam.brest.service.StudentService;
import com.epam.brest.service.UniversityService;
import com.epam.brest.service.config.ServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfig.class})
@PropertySource({"classpath:dao.properties"})
@Transactional
class StudentServiceImplIT {

    @Autowired
    StudentService studentService;

    @Test
    void shouldCount() {
        assertNotNull(studentService);
        Integer quantity = studentService.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(5), quantity);
    }

    @Test
    void create() {
        assertNotNull(studentService);
        Integer studentSizeBefore = studentService.count();
        assertNotNull(studentSizeBefore);
        Student student = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);
        Integer newStudentId = studentService.create(student);
        assertNotNull(newStudentId);
        assertEquals(studentSizeBefore, studentService.count() - 1);
    }

    @Test
    void tryToCreateEqualsStudents() {
        assertNotNull(studentService);
        Student student = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.create(student);
            studentService.create(student);
        });
    }
}