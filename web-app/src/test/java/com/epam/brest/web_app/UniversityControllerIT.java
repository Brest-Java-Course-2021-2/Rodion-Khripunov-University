package com.epam.brest.web_app;

import com.epam.brest.model.University;
import com.epam.brest.model.dto.UniversityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
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

import java.net.URI;
import java.util.Arrays;

import static com.epam.brest.model.constants.UniversityConstants.UNIVERSITY_NAME_SIZE;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Disabled
class UniversityControllerIT {

    private static final String UNIVERSITY_DTO_URL = "http://localhost:8088/university-dto";
    private static final String UNIVERSITIES_URL = "http://localhost:8088/universities";

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
    void shouldReturnUniversitiesPage() throws Exception {
        UniversityDto u1 = createUniversityDto(1, "IT", 5);
        UniversityDto u2 = createUniversityDto(2, "SECURITY", 4);
        UniversityDto u3 = createUniversityDto(3, "MANAGEMENT", null);
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(UNIVERSITY_DTO_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(u1, u2, u3)))
                );
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/universities")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("universities"))
//                .andExpect(model().attribute("universities", hasItem(
//                        allOf(
//                                hasProperty("universityId", is(u1.getUniversityId())),
//                                hasProperty("universityName", is(u1.getUniversityName())),
//                                hasProperty("courseAverage", is(u1.getCourseAverage()))
//                        )
//                )))
//                .andExpect(model().attribute("universities", hasItem(
//                        allOf(
//                                hasProperty("universityId", is(d2.getUniversityId())),
//                                hasProperty("universityName", is(d2.getUniversityName())),
//                                hasProperty("courseAverage", is(d2.getAvgSalary()))
//                        )
//                )))
//                .andExpect(model().attribute("universities", hasItem(
//                        allOf(
//                                hasProperty("universityId", is(d3.getUniversityId())),
//                                hasProperty("universityName", is(d3.getUniversityName())),
//                                hasProperty("courseAverage", isEmptyOrNullString())
//                        )
//                )))
        ;
        mockServer.verify();
    }

    @Test
    void shouldAddUniversity() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(UNIVERSITIES_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        University university = new University("Test");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/university")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("universityName", university.getUniversityName())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/universities"))
                .andExpect(redirectedUrl("/universities"));
        mockServer.verify();
    }

    @Test
    void shouldFailAddUniversityOnEmptyName() throws Exception {
        University university = new University("");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/university")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("universityName", university.getUniversityName())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("university"))
                .andExpect(
                        model().attributeHasFieldErrors(
                                "university", "universityName"
                        )
                );
    }

    @Test
    public void shouldOpenEditUniversityPageById() throws Exception {
        University d = createUniversity(1, "UTest");
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(UNIVERSITIES_URL + "/" + d.getUniversityId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(d))
                );
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/university/1")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("university"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("university", hasProperty("universityId", is(1))))
                .andExpect(model().attribute("university", hasProperty("universityName", is("UTest"))));
    }

    @Test
    public void shouldUpdateUniversityAfterEdit() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(UNIVERSITIES_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        String testName = RandomStringUtils.randomAlphabetic(UNIVERSITY_NAME_SIZE);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/university/1")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("universityId", "1")
                                .param("universityName", testName)
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/universities"))
                .andExpect(redirectedUrl("/universities"));
        mockServer.verify();
    }

    @Test
    public void shouldDeleteUniversity() throws Exception {
        int id = 3;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(UNIVERSITIES_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/university/3/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/universities"))
                .andExpect(redirectedUrl("/universities"));
        mockServer.verify();
    }

    private UniversityDto createUniversityDto(int id, String name, Integer courseAverage) {
        UniversityDto universityDto = new UniversityDto();
        universityDto.setUniversityId(id);
        universityDto.setUniversityName(name);
        universityDto.setCourseAverage(courseAverage);
        return universityDto;
    }

    private University createUniversity(int id, String name) {
        University university = new University();
        university.setUniversityId(id);
        university.setUniversityName(name);
        return university;
    }
}