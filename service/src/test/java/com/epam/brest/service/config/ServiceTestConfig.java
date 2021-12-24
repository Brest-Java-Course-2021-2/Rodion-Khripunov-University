package com.epam.brest.service.config;

import com.epam.brest.UniversityDao;
import com.epam.brest.UniversityDtoDao;
import com.epam.brest.dao.UniversityDaoJDBCImpl;
import com.epam.brest.dao.UniversityDtoDaoJdbc;
import com.epam.brest.service.UniversityDtoService;
import com.epam.brest.service.UniversityService;
import com.epam.brest.service.impl.UniversityDtoServiceImpl;
import com.epam.brest.service.impl.UniversityServiceImpl;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceTestConfig extends SpringJdbcConfig {

    @Bean
    UniversityDtoDao universityDtoDao() {
        return new UniversityDtoDaoJdbc(namedParameterJdbcTemplate());
    }

    @Bean
    UniversityDtoService universityDtoService() {
        return new UniversityDtoServiceImpl(universityDtoDao());
    }

    @Bean
    UniversityDao universityDao() {
        return new UniversityDaoJDBCImpl(namedParameterJdbcTemplate());
    }

    @Bean
    UniversityService universityService() {
        return new UniversityServiceImpl(universityDao());
    }
}
