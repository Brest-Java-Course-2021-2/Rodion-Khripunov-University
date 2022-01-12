package com.epam.brest.service.impl;

import com.epam.brest.model.dto.UniversityDto;
import com.epam.brest.service.UniversityDtoService;
import com.epam.brest.service.config.ServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfig.class})
@PropertySource({"classpath:dao.properties"})
@Transactional
class UniversityDtoServiceImplIT {

    @Autowired
    UniversityDtoService universityDtoService;

    @Test
    public void shouldFindAllWithAvgRating() {
        List<UniversityDto> universities = universityDtoService.findAllWithAvgRating();
        assertNotNull(universities);
        assertTrue(universities.size() > 0);
        assertTrue(universities.get(0).getAvgRating().doubleValue() > 0);
    }
}
