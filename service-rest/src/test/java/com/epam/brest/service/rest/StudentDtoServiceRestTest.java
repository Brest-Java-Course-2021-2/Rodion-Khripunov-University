package com.epam.brest.service.rest;

import com.epam.brest.config.ServiceRestTestConfig;
import com.epam.brest.model.dto.StudentDto;
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
class StudentDtoServiceRestTest {

    private final Logger logger = LogManager.getLogger(StudentDtoServiceRestTest.class);

    private final String URL = "http://localhost:8088/student_dto";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    StudentDtoServiceRest studentDtoService;

    @BeforeEach
    void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        studentDtoService = new StudentDtoServiceRest(URL, restTemplate);
    }

    @Test
    void shouldFindAllDto() throws Exception {
        logger.debug("shouldFindAllDto()");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        List<StudentDto> list = studentDtoService.findAllDto();

        mockServer.verify();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    void shouldFindAllWithDate() throws Exception {
        logger.debug("shouldFindAllWithDate()");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/filter?startDate=2019-07-15&endDate=2019-07-25")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1), create(2))))
                );

        List<StudentDto> list = studentDtoService.findAllWithDate("2019-07-15", "2019-07-25");

        mockServer.verify();
        assertNotNull(list);
        assertTrue(list.size() > 0);
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