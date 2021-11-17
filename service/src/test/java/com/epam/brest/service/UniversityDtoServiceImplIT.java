package com.epam.brest.service;

import com.epam.brest.model.dto.UniversityDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
class UniversityDtoServiceImplIT {
    @Autowired
    UniversityDtoService universityDtoService;

    @Test
    public void shouldFindAllWithAvgCourse() {
        List<UniversityDto> universities = universityDtoService.findAllWithAvgCourse();
        assertNotNull(universities);
        assertTrue(universities.size() > 0);
        assertTrue(universities.get(0).getCourseAverage() > 0);
    }
}
