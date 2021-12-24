package com.epam.brest.service.impl;

import com.epam.brest.model.University;
import com.epam.brest.service.UniversityService;
import com.epam.brest.service.config.ServiceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfig.class})
@PropertySource({"classpath:dao.properties"})
@Transactional
class UniversityServiceImplIT {

    @Autowired
    UniversityService universityService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldCount() {
        assertNotNull(universityService);
        Integer quantity = universityService.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }

    @Test
    void create() {
        assertNotNull(universityService);
        Integer universitySizeBefore = universityService.count();
        assertNotNull(universitySizeBefore);
        University university = new University("HGU");
        Integer newUniversityId = universityService.create(university);
        assertNotNull(newUniversityId);
        assertEquals(universitySizeBefore, universityService.count() - 1);
    }
}