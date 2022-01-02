package com.epam.brest.dao;

import com.epam.brest.StudentDtoDao;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({StudentDtoDaoJdbc.class})
@PropertySource("classpath:dao.properties")
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
class StudentDtoDaoJdbcIT {

    private static final Logger logger = LogManager.getLogger(StudentDtoDaoJdbcIT.class);

    private final StudentDtoDaoJdbc studentDtoDaoJdbc;

    public StudentDtoDaoJdbcIT(@Autowired StudentDtoDao studentDaoJDBC) {
        this.studentDtoDaoJdbc = (StudentDtoDaoJdbc) studentDaoJDBC;
    }

    @Test
    void shouldFindAllDto() {
        logger.debug("Execute test: findAllDto()");
        assertNotNull(studentDtoDaoJdbc);
        assertNotNull(studentDtoDaoJdbc.findAllDto());
    }

    @Test
    void shouldFindAllDtoWithDate() {
        logger.debug("Execute test: findAllWithUniversityName()");
        assertNotNull(studentDtoDaoJdbc);
        assertNotNull(studentDtoDaoJdbc.findAllWithDate(LocalDate.parse("2019-07-27"), LocalDate.now()));
    }
}