package com.epam.brest.web_app;

import com.epam.brest.model.Student;
import com.epam.brest.model.University;
import com.epam.brest.model.dto.StudentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Disabled
class StudentControllerIT {

    private static final String STUDENT_DTO_URL = "http://localhost:8088/student_dto";
    private static final String UNIVERSITIES_URL = "http://localhost:8088/universities";
    private static final String STUDENTS_URL = "http://localhost:8088/students";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldReturnStudentsPage() throws Exception {
        StudentDto s1 = new StudentDto(1,"UserT1", "UserT2", LocalDate.now(),
                "usert1@gmail.com", 1, BigDecimal.valueOf(5), "GTU");
        StudentDto s2 = new StudentDto(2,"UserT3", "UserT4", LocalDate.now(),
                "usert3@gmail.com", 1, BigDecimal.valueOf(5), "MGU");
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(STUDENT_DTO_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(s1, s2)))
                );
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/students")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("students"))
                .andExpect(model().attribute("students", hasItem(hasEntry("studentId", s1.getStudentId()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("firstName", s1.getFirstName()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("lastName", s1.getLastName()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("enrollmentDate",
                        s1.getEnrollmentDate().toString()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("email", s1.getEmail()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("course", s1.getCourse()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("rating", s1.getRating().intValue()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("universityName", s1.getUniversityName()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("studentId", s2.getStudentId()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("firstName", s2.getFirstName()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("lastName", s2.getLastName()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("enrollmentDate",
                        s2.getEnrollmentDate().toString()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("email", s2.getEmail()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("course", s2.getCourse()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("rating", s2.getRating().intValue()))))
                .andExpect(model().attribute("students", hasItem(hasEntry("universityName", s2.getUniversityName()))));
        mockServer.verify();
    }

    @Test
    void shouldAddStudent() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(STUDENTS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        Student student = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/student")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("firstName", student.getFirstName())
                                .param("lastName", student.getLastName())
                                .param("enrollmentDate", student.getEnrollmentDate().toString())
                                .param("email", student.getEmail())
                                .param("course", student.getCourse().toString())
                                .param("rating", student.getRating().toString())
                                .param("universityId", student.getUniversityId().toString())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/students"))
                .andExpect(redirectedUrl("/students"));
        mockServer.verify();
    }

    @Test
    void shouldFailAddStudentOnEmptyEmail() throws Exception {
        University u = new University(1,"Test");
        Student student = new Student("UserT1", "UserT2", "", 1);
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(UNIVERSITIES_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(u)))
                );
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/student")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("firstName", student.getFirstName())
                                .param("lastName", student.getLastName())
                                .param("enrollmentDate", student.getEnrollmentDate().toString())
                                .param("email", student.getEmail())
                                .param("course", student.getCourse().toString())
                                .param("rating", student.getRating().toString())
                                .param("universityId", student.getUniversityId().toString())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("student"))
                .andExpect(
                        model().attributeHasFieldErrors("student", "email")
                );
    }

    @Test
    public void shouldOpenEditStudentPageById() throws Exception {
        University u = new University(1,"Test");
        Student s = new Student(1,"UserT1", "UserT2", "usert1@gmail.com", 1);
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(STUDENTS_URL + "/" + s.getStudentId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(s))
                );
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(UNIVERSITIES_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(u)))
                );
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/1")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("student"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("student", hasProperty("studentId",
                        is(1))))
                .andExpect(model().attribute("student", hasProperty("firstName",
                        is("UserT1"))))
                .andExpect(model().attribute("student", hasProperty("lastName",
                        is("UserT2"))))
                .andExpect(model().attribute("student", hasProperty("enrollmentDate",
                        is(LocalDate.parse("2022-01-12")))))
                .andExpect(model().attribute("student", hasProperty("email",
                        is("usert1@gmail.com"))))
                .andExpect(model().attribute("student", hasProperty("course",
                        is(1))))
                .andExpect(model().attribute("student", hasProperty("rating",
                        is(BigDecimal.valueOf(0)))))
                .andExpect(model().attribute("student", hasProperty("universityId",
                        is(1))));
    }

    @Test
    public void shouldUpdateStudentAfterEdit() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(STUDENTS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        String testEmail = "usert1updated@gmail.com";
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/student/1")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("studentId", "1")
                                .param("firstName", "UserT1")
                                .param("lastName", "UserT2")
                                .param("enrollmentDate", "2022-01-12")
                                .param("email", testEmail)
                                .param("course", "1")
                                .param("rating", "0")
                                .param("universityId", "1")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/students"))
                .andExpect(redirectedUrl("/students"));
        mockServer.verify();
    }

    @Test
    public void shouldDeleteStudent() throws Exception {
        int id = 3;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(STUDENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/3/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/students"))
                .andExpect(redirectedUrl("/students"));
        mockServer.verify();
    }
}