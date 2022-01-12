package com.epam.brest.rest;

import com.epam.brest.model.Student;
import com.epam.brest.rest.exception.CustomExceptionHandler;
import com.epam.brest.rest.exception.ErrorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.rest.exception.CustomExceptionHandler.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class StudentControllerIT {

    private static final Logger logger = LogManager.getLogger(StudentControllerIT.class);

    public static final String STUDENTS_ENDPOINT = "/students";

    @Autowired
    private StudentController studentController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcStudentService studentService = new MockMvcStudentService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldFindAllStudents() throws Exception {

        // given
        Student student = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);
        Integer id = studentService.create(student);

        // when
        List<Student> students = studentService.findAll();

        // then
        assertNotNull(students);
        assertTrue(students.size() > 0);
    }

    @Test
    public void shouldCreateStudent() throws Exception {
        Student student = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);
        Integer id = studentService.create(student);
        assertNotNull(id);
    }

    @Test
    public void shouldFindStudentById() throws Exception {

        // given
        Student student = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);
        Integer id = studentService.create(student);

        assertNotNull(id);

        // when
        Optional<Student> optionalStudent = studentService.findById(id);

        // then
        assertTrue(optionalStudent.isPresent());
        assertEquals(optionalStudent.get().getStudentId(), id);
        assertEquals(student.getEmail(), optionalStudent.get().getEmail());
    }

    @Test
    public void shouldUpdateStudent() throws Exception {

        // given
        Student student = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);
        Integer id = studentService.create(student);
        assertNotNull(id);

        Optional<Student> studentOptional = studentService.findById(id);
        assertTrue(studentOptional.isPresent());

        studentOptional.get().setEmail("updatedt1@gmail.com");

        // when
        int result = studentService.update(studentOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Student> updatedStudentOptional = studentService.findById(id);
        assertTrue(updatedStudentOptional.isPresent());
        assertEquals(updatedStudentOptional.get().getStudentId(), id);
        assertEquals(updatedStudentOptional.get().getStudentId(),studentOptional.get().getStudentId());

    }

    @Test
    public void shouldDeleteStudent() throws Exception {
        // given
        Student student = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);
        Integer id = studentService.create(student);

        List<Student> students = studentService.findAll();
        assertNotNull(students);

        // when
        int result = studentService.delete(id);

        // then
        assertTrue(1 == result);

        List<Student> currentStudents = studentService.findAll();
        assertNotNull(currentStudents);

        assertTrue(students.size()-1 == currentStudents.size());
    }

    @Test
    public void shouldReturnStudentNotFoundError() throws Exception {
        logger.debug("shouldReturnUniversityNotFoundError()");
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(STUDENTS_ENDPOINT + "/999999")
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isNotFound())
                        .andReturn().getResponse();
        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), STUDENT_NOT_FOUND);
    }

    @Test
    public void shouldFailOnCreateStudentWithDuplicateEmail() throws Exception {
        Student student1 = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);
        Integer id = studentService.create(student1);
        assertNotNull(id);

        Student student2 = new Student(student1.getFirstName(), student1.getLastName(),
                student1.getEmail(), student1.getUniversityId());

        MockHttpServletResponse response =
                mockMvc.perform(post(STUDENTS_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(student2))
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isUnprocessableEntity())
                        .andReturn().getResponse();

        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), VALIDATION_ERROR);
    }

    class MockMvcStudentService {

        public List<Student> findAll() throws Exception {
            logger.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(STUDENTS_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);
            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Student>>() {});
        }

        public Optional<Student> findById(Integer id) throws Exception {
            logger.debug("findById({})", id);
            MockHttpServletResponse response = mockMvc.perform(get(STUDENTS_ENDPOINT + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Student.class));
        }

        public Integer create(Student student) throws Exception {
            logger.debug("create({})", student);
            String json = objectMapper.writeValueAsString(student);
            MockHttpServletResponse response =
                    mockMvc.perform(post(STUDENTS_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json)
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int update(Student student) throws Exception {
            logger.debug("update({})", student);
            MockHttpServletResponse response =
                    mockMvc.perform(put(STUDENTS_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(student))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int delete(Integer studentId) throws Exception {
            logger.debug("delete(id:{})", studentId);
            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.delete(new StringBuilder(STUDENTS_ENDPOINT).append("/")
                                            .append(studentId).toString())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }
}
