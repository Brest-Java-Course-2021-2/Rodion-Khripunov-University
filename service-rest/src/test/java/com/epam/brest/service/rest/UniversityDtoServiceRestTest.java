package com.epam.brest.service.rest;

import com.epam.brest.config.ServiceRestTestConfig;
import com.epam.brest.model.dto.UniversityDto;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import({ServiceRestTestConfig.class})
class UniversityDtoServiceRestTest {

    private final Logger logger = LogManager.getLogger(UniversityDtoServiceRestTest.class);

    private final String URL = "http://localhost:8088/university_dto";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    UniversityDtoServiceRest universityDtoService;

    @BeforeEach
    void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        universityDtoService = new UniversityDtoServiceRest(URL, restTemplate);
    }

    @Test
    void shouldFindAllWithAvgCourse() throws Exception {
        logger.debug("shouldFindAllWithAvgCourse()");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        List<UniversityDto> list = universityDtoService.findAllWithAvgRating();

        mockServer.verify();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    private UniversityDto create(int index) {
        UniversityDto universityDto = new UniversityDto();
        universityDto.setUniversityId(index);
        universityDto.setUniversityName("d" + index);
        universityDto.setAvgRating(BigDecimal.valueOf(5 + index));
        return universityDto;
    }
}