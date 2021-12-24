package com.epam.brest.rest;

import com.epam.brest.model.dto.UniversityDto;
import com.epam.brest.service.UniversityDtoService;
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
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class UniversityDtoControllerTest {

    @InjectMocks
    private UniversityDtoController universityDtoController;

    @Mock
    private UniversityDtoService universityDtoService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(universityDtoController)
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(universityDtoService);
    }

    @Test
    public void shouldFindAllWithAvgSalary() throws Exception {

        Mockito.when(universityDtoService.findAllWithAvgRating()).thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/university_dto")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].universityId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].universityName", Matchers.is("d0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseAverage", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].universityId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].universityName", Matchers.is("d1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseAverage", Matchers.is(101)))
        ;

        Mockito.verify(universityDtoService).findAllWithAvgRating();
    }

    private UniversityDto create(int index) {
        UniversityDto universityDto = new UniversityDto();
        universityDto.setUniversityId(index);
        universityDto.setUniversityName("d" + index);
        universityDto.setAvgRating(BigDecimal.valueOf(5 + index));
        return universityDto;
    }
}