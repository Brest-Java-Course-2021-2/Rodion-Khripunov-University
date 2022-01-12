package com.epam.brest.rest;

import com.epam.brest.model.University;
import com.epam.brest.rest.exception.CustomExceptionHandler;
import com.epam.brest.rest.exception.ErrorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
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

import static com.epam.brest.model.constants.UniversityConstants.UNIVERSITY_NAME_SIZE;
import static com.epam.brest.rest.exception.CustomExceptionHandler.UNIVERSITY_NOT_FOUND;
import static com.epam.brest.rest.exception.CustomExceptionHandler.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class UniversityControllerIT {

    private static final Logger logger = LogManager.getLogger(UniversityControllerIT.class);

    public static final String UNIVERSITIES_ENDPOINT = "/universities";

    @Autowired
    private UniversityController universityController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcUniversityService universityService = new MockMvcUniversityService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(universityController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldFindAllUniversities() throws Exception {

        // given
        University university = new University(RandomStringUtils.randomAlphabetic(UNIVERSITY_NAME_SIZE));
        Integer id = universityService.create(university);

        // when
        List<University> universities = universityService.findAll();

        // then
        assertNotNull(universities);
        assertTrue(universities.size() > 0);
    }

    @Test
    public void shouldCreateUniversity() throws Exception {
        University university = new University(RandomStringUtils.randomAlphabetic(UNIVERSITY_NAME_SIZE));
        Integer id = universityService.create(university);
        assertNotNull(id);
    }

    @Test
    public void shouldFindUniversityById() throws Exception {

        // given
        University university = new University(RandomStringUtils.randomAlphabetic(UNIVERSITY_NAME_SIZE));
        Integer id = universityService.create(university);

        assertNotNull(id);

        // when
        Optional<University> optionalUniversity = universityService.findById(id);

        // then
        assertTrue(optionalUniversity.isPresent());
        assertEquals(optionalUniversity.get().getUniversityId(), id);
        assertEquals(university.getUniversityName(), optionalUniversity.get().getUniversityName());
    }

    @Test
    public void shouldUpdateUniversity() throws Exception {

        // given
        University university = new University(RandomStringUtils.randomAlphabetic(UNIVERSITY_NAME_SIZE));
        Integer id = universityService.create(university);
        assertNotNull(id);

        Optional<University> universityOptional = universityService.findById(id);
        assertTrue(universityOptional.isPresent());

        universityOptional.get().
                setUniversityName(RandomStringUtils.randomAlphabetic(UNIVERSITY_NAME_SIZE));

        // when
        int result = universityService.update(universityOptional.get());

        // then
        assertTrue(1 == result);

        Optional<University> updatedUniversityOptional = universityService.findById(id);
        assertTrue(updatedUniversityOptional.isPresent());
        assertEquals(updatedUniversityOptional.get().getUniversityId(), id);
        assertEquals(updatedUniversityOptional.get().getUniversityId(),universityOptional.get().getUniversityId());

    }

    @Test
    public void shouldDeleteUniversity() throws Exception {
        // given
        University university = new University(RandomStringUtils.randomAlphabetic(UNIVERSITY_NAME_SIZE));
        Integer id = universityService.create(university);

        List<University> universities = universityService.findAll();
        assertNotNull(universities);

        // when
        int result = universityService.delete(id);

        // then
        assertTrue(1 == result);

        List<University> currentUniversities = universityService.findAll();
        assertNotNull(currentUniversities);

        assertTrue(universities.size()-1 == currentUniversities.size());
    }

    @Test
    public void shouldReturnUniversityNotFoundError() throws Exception {
        logger.debug("shouldReturnUniversityNotFoundError()");
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(UNIVERSITIES_ENDPOINT + "/999999")
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isNotFound())
                        .andReturn().getResponse();
        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), UNIVERSITY_NOT_FOUND);
    }

    @Test
    public void shouldFailOnCreateUniversityWithDuplicateName() throws Exception {
        University university1 = new University(RandomStringUtils.randomAlphabetic(UNIVERSITY_NAME_SIZE));
        Integer id = universityService.create(university1);
        assertNotNull(id);

        University university2 = new University(university1.getUniversityName());

        MockHttpServletResponse response =
                mockMvc.perform(post(UNIVERSITIES_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(university2))
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isUnprocessableEntity())
                        .andReturn().getResponse();

        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), VALIDATION_ERROR);
    }

    class MockMvcUniversityService {

        public List<University> findAll() throws Exception {
            logger.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(UNIVERSITIES_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);
            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<University>>() {});
        }

        public Optional<University> findById(Integer id) throws Exception {
            logger.debug("findById({})", id);
            MockHttpServletResponse response = mockMvc.perform(get(UNIVERSITIES_ENDPOINT + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), University.class));
        }

        public Integer create(University university) throws Exception {
            logger.debug("create({})", university);
            String json = objectMapper.writeValueAsString(university);
            MockHttpServletResponse response =
                    mockMvc.perform(post(UNIVERSITIES_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json)
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int update(University university) throws Exception {
            logger.debug("update({})", university);
            MockHttpServletResponse response =
                    mockMvc.perform(put(UNIVERSITIES_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(university))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int delete(Integer universityId) throws Exception {
            logger.debug("delete(id:{})", universityId);
            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.delete(new StringBuilder(UNIVERSITIES_ENDPOINT).append("/")
                                            .append(universityId).toString())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }
}
