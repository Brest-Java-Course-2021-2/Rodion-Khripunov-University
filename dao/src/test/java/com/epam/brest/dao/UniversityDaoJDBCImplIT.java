package com.epam.brest.dao;

import com.epam.brest.UniversityDao;
import com.epam.brest.model.University;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
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
        Integer universitySizeBefore = universityDaoJDBC.findAll().size();
        University university = new University("GRU");
        Integer newUniversityId = universityDaoJDBC.create(university);
        assertNotNull(newUniversityId);
        assertEquals((int) universitySizeBefore, universityDaoJDBC.findAll().size() - 1);
    }

    @Test
    void tryToCreateEqualUniversities() {
        assertNotNull(universityDaoJDBC);
        University university = new University("GRU");
        assertThrows(DuplicateKeyException.class, () -> {
            universityDaoJDBC.create(university);
            universityDaoJDBC.create(university);
        });
    }
}
