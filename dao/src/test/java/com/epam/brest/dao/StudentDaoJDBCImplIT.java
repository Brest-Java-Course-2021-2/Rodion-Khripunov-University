package com.epam.brest.dao;

import com.epam.brest.StudentDao;
import com.epam.brest.model.Student;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({StudentDaoJDBCImpl.class})
@PropertySource("classpath:dao.properties")
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
class StudentDaoJDBCImplIT {

    private static final Logger logger = LogManager.getLogger(StudentDaoJDBCImplIT.class);

    private final StudentDaoJDBCImpl studentDaoJDBC;

    public StudentDaoJDBCImplIT(@Autowired StudentDao studentDaoJDBC) {
        this.studentDaoJDBC = (StudentDaoJDBCImpl) studentDaoJDBC;
    }

    @Test
    void findAll() {
        logger.debug("Execute test: findAllStudents()");
        assertNotNull(studentDaoJDBC);
        assertNotNull(studentDaoJDBC.findAll());
    }

    @Test
    void create() {
        assertNotNull(studentDaoJDBC);
        Integer studentSizeBefore = studentDaoJDBC.count();
        Student student = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);
        Integer newStudentId = studentDaoJDBC.create(student);
        assertNotNull(newStudentId);
        assertEquals((int) studentSizeBefore, studentDaoJDBC.count() - 1);
    }

    @Test
    void tryToCreateEqualStudents() {
        assertNotNull(studentDaoJDBC);
        Student student = new Student("UserT1", "UserT2", "usert1@gmail.com", 1);
        assertThrows(IllegalArgumentException.class, () -> {
            studentDaoJDBC.create(student);
            studentDaoJDBC.create(student);
        });
    }

    @Test
    void getStudentById() {
        List<Student> students = studentDaoJDBC.findAll();
        if (students.size() > 0) {
            studentDaoJDBC.create(
                    new Student("UserT1", "UserT2", "usert1@gmail.com", 1));
            students = studentDaoJDBC.findAll();
        }
        Student studentSrc = students.get(0);
        Student studentDst = studentDaoJDBC.getStudentById(studentSrc.getStudentId());
        assertEquals(studentSrc.getEmail(), studentDst.getEmail());
    }

    @Test
    void updateStudent() {
        List<Student> students = studentDaoJDBC.findAll();
        if (students.size() > 0) {
            studentDaoJDBC.create(
                    new Student("UserT1", "UserT2", "usert1@gmail.com", 1));
            students = studentDaoJDBC.findAll();
        }
        Student studentSrc = students.get(0);
        studentSrc.setEmail("test." + studentSrc.getEmail());
        studentDaoJDBC.update(studentSrc);

        Student studentDst = studentDaoJDBC.getStudentById(studentSrc.getStudentId());
        assertEquals(studentSrc.getEmail(), studentDst.getEmail());
    }

    @Test
    void deleteStudent() {
        studentDaoJDBC.create(
                new Student("UserT1", "UserT2", "usert1@gmail.com", 1));
        List<Student> students = studentDaoJDBC.findAll();
        studentDaoJDBC.delete(students.get(students.size() - 1).getStudentId());
        assertEquals(students.size() - 1, studentDaoJDBC.count());
    }

    @Test
    void shouldCount() {
        assertNotNull(studentDaoJDBC);
        Integer quantity = studentDaoJDBC.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(5), quantity);
    }
}