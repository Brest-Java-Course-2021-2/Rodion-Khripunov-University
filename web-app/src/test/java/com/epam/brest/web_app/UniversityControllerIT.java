package com.epam.brest.web_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:app-context-test.xml"})
@Transactional
class UniversityControllerIT {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void SetUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldReturnDepartmentsPage() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/universities")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("universities"))
                .andExpect(model().attribute("universities", hasItem(
                        allOf(
                                hasProperty("universityId", is(1)),
                                hasProperty("universityName", is("BGTU"))//,
                               // hasProperty("courseAVG", is(4))
                        )
                )))
                .andExpect(model().attribute("universities", hasItem(
                        allOf(
                                hasProperty("universityId", is(2)),
                                hasProperty("universityName", is("BGUIR"))//,
                                //hasProperty("courseAVG", is(4))
                        )
                )))
                .andExpect(model().attribute("universities", hasItem(
                        allOf(
                                hasProperty("universityId", is(3)),
                                hasProperty("universityName", is("MGU"))//,
                                //hasProperty("courseAVG", is(3))
                        )
                )));
    }
}