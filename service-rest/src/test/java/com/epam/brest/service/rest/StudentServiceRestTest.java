package com.epam.brest.service.rest;

import com.epam.brest.config.ServiceRestTestConfig;
import com.epam.brest.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import({ServiceRestTestConfig.class})
class StudentServiceRestTest {

    private final Logger logger = LogManager.getLogger(StudentServiceRestTest.class);

    private final String URL = "http://localhost:8088/students";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    StudentServiceRest studentService;

    @BeforeEach
    void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        studentService = new StudentServiceRest(URL, restTemplate);
    }

    @Test
    void shouldFindAllStudents() throws Exception {
        logger.debug("shouldFindAllStudents()");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        List<Student> students = studentService.findAll();

        mockServer.verify();
        assertNotNull(students);
        assertTrue(students.size() > 0);
    }

    @Test
    void shouldCreateStudent() throws Exception {
        logger.debug("shouldCreateStudent()");

        Student student = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        Integer id = studentService.create(student);

        mockServer.verify();
        assertNotNull(id);
    }

    @Test
    void shouldFindStudentById() throws Exception {
        logger.debug("shouldFindStudentById()");

        Integer id = 1;
        Student student = new Student(id, "UserT1", "UserT2", "usert1@gmail.com", 1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(student))
                );

        Student resultStudent = studentService.getStudentById(id);

        mockServer.verify();
        assertNotNull(resultStudent);
        assertEquals(resultStudent.getStudentId(), id);
        assertEquals(resultStudent.getEmail(), student.getEmail());
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        logger.debug("shouldUpdateStudent()");

        Integer id = 1;
        Student student = new Student(id, "UserT1", "UserT2", "usert1@gmail.com", 1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(student))
                );

        int result = studentService.update(student);
        Student resultStudent = studentService.getStudentById(id);

        mockServer.verify();
        assertTrue(1 == result);
        assertNotNull(resultStudent);
        assertEquals(resultStudent.getStudentId(), id);
        assertEquals(resultStudent.getEmail(), student.getEmail());
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        logger.debug("shouldDeleteStudent()");

        Integer id = 1;

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        int result = studentService.delete(id);

        mockServer.verify();
        assertTrue(1 == result);
    }

    private Student create(int index) {
        Student student = new Student();
        student.setStudentId(index);
        student.setFirstName("f" + index);
        student.setLastName("l" + index);
        student.setEnrollmentDate(LocalDate.parse("2021-07-15").plusDays(index));
        student.setEmail("email" + index + "@gmail.com");
        student.setCourse(2 + index);
        student.setRating(BigDecimal.valueOf(5.5 + index));
        student.setUniversityId(1 + index);
        return student;
    }
}