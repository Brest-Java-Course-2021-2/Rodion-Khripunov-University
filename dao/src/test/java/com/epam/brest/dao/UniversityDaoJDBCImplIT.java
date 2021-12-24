package com.epam.brest.dao;

import com.epam.brest.UniversityDao;
import com.epam.brest.model.University;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({UniversityDaoJDBCImpl.class})
@PropertySource("classpath:dao.properties")
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
class UniversityDaoJDBCImplIT {

    private static final Logger logger = LogManager.getLogger(UniversityDaoJDBCImplIT.class);

    private UniversityDaoJDBCImpl universityDaoJDBC;

    public UniversityDaoJDBCImplIT(@Autowired UniversityDao universityDaoJDBC) {
        this.universityDaoJDBC = (UniversityDaoJDBCImpl) universityDaoJDBC;
    }

    @Test
    void findAll() {
        logger.debug("Execute test: findAll()");
        assertNotNull(universityDaoJDBC);
        assertNotNull(universityDaoJDBC.findAll());
    }

    @Test
    void create() {
        assertNotNull(universityDaoJDBC);
        Integer universitySizeBefore = universityDaoJDBC.count();
        University university = new University("GRU");
        Integer newUniversityId = universityDaoJDBC.create(university);
        assertNotNull(newUniversityId);
        assertEquals((int) universitySizeBefore, universityDaoJDBC.count() - 1);
    }

    @Test
    void tryToCreateEqualUniversities() {
        assertNotNull(universityDaoJDBC);
        University university = new University("GRU");
        assertThrows(IllegalArgumentException.class, () -> {
            universityDaoJDBC.create(university);
            universityDaoJDBC.create(university);
        });
    }

    @Test
    void getUniversityById() {
        List<University> universities = universityDaoJDBC.findAll();
        if (universities.size() > 0) {
            universityDaoJDBC.create(new University("TEST UNIVERSITY"));
            universities = universityDaoJDBC.findAll();
        }
        University universitySrc = universities.get(0);
        University universityDst = universityDaoJDBC.getUniversityById(universitySrc.getUniversityId());
        assertEquals(universitySrc.getUniversityName(), universityDst.getUniversityName());
    }

    @Test
    void updateUniversity() {
        List<University> universities = universityDaoJDBC.findAll();
        if (universities.size() > 0) {
            universityDaoJDBC.create(new University("TEST UNIVERSITY"));
            universities = universityDaoJDBC.findAll();
        }
        University universitySrc = universities.get(0);
        universitySrc.setUniversityName(universitySrc.getUniversityName() + "_TEST");
        universityDaoJDBC.update(universitySrc);

        University universityDst = universityDaoJDBC.getUniversityById(universitySrc.getUniversityId());
        assertEquals(universitySrc.getUniversityName(), universityDst.getUniversityName());
    }

    @Test
    void deleteUniversity() {
        universityDaoJDBC.create(new University("TEST UNIVERSITY"));
        List<University> universities = universityDaoJDBC.findAll();
        universityDaoJDBC.delete(universities.get(universities.size() - 1).getUniversityId());
        assertEquals(universities.size() - 1, universityDaoJDBC.count());
    }

    @Test
    void shouldCount() {
        assertNotNull(universityDaoJDBC);
        Integer quantity = universityDaoJDBC.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }
}
