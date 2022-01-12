package com.epam.brest.rest;

import com.epam.brest.model.dto.StudentDto;
import com.epam.brest.service.StudentDtoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class StudentDtoControllerTest {

    @InjectMocks
    private StudentDtoController studentDtoController;

    @Mock
    private StudentDtoService studentDtoService;

    ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentDtoController)
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(studentDtoService);
    }

    @Test
    public void shouldFindAllDto() throws Exception {
        Mockito.when(studentDtoService.findAllDto()).thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/student_dto")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].studentId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("f0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("l0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].enrollmentDate", Matchers.is("2021-07-15")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("email0@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].course", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rating", Matchers.is(5.5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].universityName", Matchers.is("s0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].studentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("f1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("l1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].enrollmentDate", Matchers.is("2021-07-16")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is("email1@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].course", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rating", Matchers.is(6.5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].universityName", Matchers.is("s1")))
        ;

        Mockito.verify(studentDtoService).findAllDto();
    }

    @Test
    public void shouldFindAllWithDate() throws Exception {
        String startDate = LocalDate.MIN.toString();
        String endDate = LocalDate.MAX.toString();
        Mockito.when(studentDtoService.findAllWithDate(startDate, endDate)).thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/student_dto/filter")
                                .contentType("application/json")
                                .param("startDate", startDate)
                                .param("endDate", endDate)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].studentId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("f0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("l0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].enrollmentDate", Matchers.is("2021-07-15")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("email0@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].course", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rating", Matchers.is(5.5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].universityName", Matchers.is("s0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].studentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("f1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("l1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].enrollmentDate", Matchers.is("2021-07-16")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is("email1@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].course", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rating", Matchers.is(6.5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].universityName", Matchers.is("s1")))
        ;

        Mockito.verify(studentDtoService).findAllWithDate(startDate, endDate);
    }

    private StudentDto create(int index) {
        StudentDto studentDto = new StudentDto();
        studentDto.setStudentId(index);
        studentDto.setFirstName("f" + index);
        studentDto.setLastName("l" + index);
        studentDto.setEnrollmentDate(LocalDate.parse("2021-07-15").plusDays(index));
        studentDto.setEmail("email" + index + "@gmail.com");
        studentDto.setCourse(2 + index);
        studentDto.setRating(BigDecimal.valueOf(5.5 + index));
        studentDto.setUniversityName("s" + index);
        return studentDto;
    }
}