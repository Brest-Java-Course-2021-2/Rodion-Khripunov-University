package com.epam.brest.service.rest;

import com.epam.brest.config.ServiceRestTestConfig;
import com.epam.brest.model.University;
import com.epam.brest.model.dto.UniversityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static com.epam.brest.model.constants.UniversityConstants.UNIVERSITY_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import({ServiceRestTestConfig.class})
class UniversityServiceRestTest {

    private final Logger logger = LogManager.getLogger(UniversityDtoServiceRestTest.class);

    private final String URL = "http://localhost:8088/universities";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    UniversityServiceRest universityService;

    @BeforeEach
    void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        universityService = new UniversityServiceRest(URL, restTemplate);
    }

    @Test
    void shouldFindAllUniversities() throws Exception {
        logger.debug("shouldFindAllUniversities()");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        List<University> universities = universityService.findAll();

        mockServer.verify();
        assertNotNull(universities);
        assertTrue(universities.size() > 0);
    }

    @Test
    void shouldCreateUniversity() throws Exception {
        logger.debug("shouldCreateUniversity()");

        University university = new University()
                .setUniversityName(RandomStringUtils.randomAlphabetic(UNIVERSITY_NAME_SIZE));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        Integer id = universityService.create(university);

        mockServer.verify();
        assertNotNull(id);
    }

    @Test
    void shouldFindUniversityById() throws Exception {
        logger.debug("shouldFindUniversityById()");

        Integer id = 1;
        University university = new University()
                .setUniversityId(id)
                .setUniversityName(RandomStringUtils.randomAlphabetic(UNIVERSITY_NAME_SIZE));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(university))
                );

        University resultUniversity = universityService.getUniversityById(id);

        mockServer.verify();
        assertNotNull(resultUniversity);
        assertEquals(resultUniversity.getUniversityId(), id);
        assertEquals(resultUniversity.getUniversityName(), university.getUniversityName());
    }

    @Test
    void shouldUpdateUniversity() throws Exception {
        logger.debug("shouldUpdateUniversity()");

        Integer id = 1;
        University university = new University()
                .setUniversityId(id)
                .setUniversityName(RandomStringUtils.randomAlphabetic(UNIVERSITY_NAME_SIZE));

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
                        .body(mapper.writeValueAsString(university))
                );

        int result = universityService.update(university);
        University resultUniversity = universityService.getUniversityById(id);

        mockServer.verify();
        assertTrue(1 == result);
        assertNotNull(resultUniversity);
        assertEquals(resultUniversity.getUniversityId(), id);
        assertEquals(resultUniversity.getUniversityName(), university.getUniversityName());
    }

    @Test
    void shouldDeleteUniversity() throws Exception {
        logger.debug("shouldDeleteUniversity()");

        Integer id = 1;

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        int result = universityService.delete(id);

        mockServer.verify();
        assertTrue(1 == result);
    }

    private University create(int index) {
        University university = new University();
        university.setUniversityId(index);
        university.setUniversityName("d" + index);
        return university;
    }
}