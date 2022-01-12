package com.epam.brest.service.config;

import com.epam.brest.StudentDao;
import com.epam.brest.StudentDtoDao;
import com.epam.brest.UniversityDao;
import com.epam.brest.UniversityDtoDao;
import com.epam.brest.dao.StudentDaoJDBCImpl;
import com.epam.brest.dao.StudentDtoDaoJdbc;
import com.epam.brest.dao.UniversityDaoJDBCImpl;
import com.epam.brest.dao.UniversityDtoDaoJdbc;
import com.epam.brest.service.StudentDtoService;
import com.epam.brest.service.StudentService;
import com.epam.brest.service.UniversityDtoService;
import com.epam.brest.service.UniversityService;
import com.epam.brest.service.impl.StudentDtoServiceImpl;
import com.epam.brest.service.impl.StudentServiceImpl;
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

    @Bean
    StudentDao studentDao() {
        return new StudentDaoJDBCImpl(namedParameterJdbcTemplate());
    }

    @Bean
    StudentService studentService() {
        return new StudentServiceImpl(studentDao());
    }

    @Bean
    StudentDtoDao studentDtoDao() {
        return new StudentDtoDaoJdbc(namedParameterJdbcTemplate());
    }

    @Bean
    StudentDtoService studentDtoService() {
        return new StudentDtoServiceImpl(studentDtoDao());
    }
}
