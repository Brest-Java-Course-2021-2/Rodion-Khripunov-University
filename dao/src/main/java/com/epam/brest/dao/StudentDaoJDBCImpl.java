package com.epam.brest.dao;

import com.epam.brest.StudentDao;
import com.epam.brest.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component
public class StudentDaoJDBCImpl implements StudentDao {

    private final Logger logger = LogManager.getLogger(StudentDaoJDBCImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SELECT_COUNT_FROM_STUDENT}")
    private String sqlStudentCount;

    @Value("${SQL_ALL_STUDENTS}")
    private String sqlStudentAll;

    @Value("${SQL_STUDENT_BY_ID}")
    private String sqlStudentById;

    @Value("${SQL_CHECK_UNIQUE_STUDENT_EMAIL}")
    private String sqlStudentCheckUniqueEmail;

    @Value("${SQL_CREATE_STUDENT}")
    private String sqlStudentCreate;

    @Value("${SQL_UPDATE_STUDENT_EMAIL}")
    private String sqlStudentUpdateEmail;

    @Value("${SQL_DELETE_STUDENT_BY_ID}")
    private String sqlStudentDeleteById;

    public StudentDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Student> findAll() {
        logger.debug("findAllStudents()");
        return namedParameterJdbcTemplate.query(sqlStudentAll, new StudentRowMapper());
    }

    @Override
    public Student getStudentById(Integer studentId) {
        logger.debug("getStudentById({})", studentId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("studentId", studentId);
        return namedParameterJdbcTemplate.queryForObject(sqlStudentById, sqlParameterSource, new StudentRowMapper());
    }

    @Override
    public Integer create(Student student) {
        logger.debug("Start: create({})", student);
        if (!isStudentUnique(student.getEmail())) {
            logger.warn("Student with the same email {} already exists.", student.getEmail());
            throw new IllegalArgumentException("Student with the same email already exists in DB.");
        }
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("firstName", student.getFirstName())
                        .addValue("lastName", student.getLastName())
                        .addValue("enrollmentDate", student.getEnrollmentDate())
                        .addValue("email", student.getEmail())
                        .addValue("course", student.getCourse())
                        .addValue("rating", student.getRating())
                        .addValue("universityId", student.getUniversityId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlStudentCreate, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    private boolean isStudentUnique(String email) {
        logger.debug("Check student email: {} on unique", email);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("email", email);
        return namedParameterJdbcTemplate
                .queryForObject(sqlStudentCheckUniqueEmail, sqlParameterSource, Integer.class) == 0;
    }

    @Override
    public Integer update(Student student) {
        logger.debug("Start: update({})", student);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("firstName", student.getFirstName())
                        .addValue("lastName", student.getLastName())
                        .addValue("enrollmentDate", student.getEnrollmentDate())
                        .addValue("email", student.getEmail())
                        .addValue("course", student.getCourse())
                        .addValue("rating", student.getRating())
                        .addValue("universityId", student.getUniversityId())
                        .addValue("studentId", student.getStudentId());
        return namedParameterJdbcTemplate.update(sqlStudentUpdateEmail, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer studentId) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("studentId", studentId);
        return namedParameterJdbcTemplate.update(sqlStudentDeleteById, sqlParameterSource);
    }

    @Override
    public Integer count() {
        logger.debug("Count()");
        return namedParameterJdbcTemplate
                .queryForObject(sqlStudentCount, new MapSqlParameterSource(), Integer.class);
    }

    private static class StudentRowMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student = new Student();
            student.setStudentId(resultSet.getInt("student_id"));
            student.setFirstName(resultSet.getString("firstname"));
            student.setLastName(resultSet.getString("lastname"));
            student.setEnrollmentDate(LocalDate.parse(resultSet.getString("enrollment_date")));
            student.setEmail(resultSet.getString("email"));
            student.setCourse(resultSet.getInt("course"));
            student.setRating(resultSet.getBigDecimal("rating"));
            student.setUniversityId(resultSet.getInt("university_id"));
            return student;
        }
    }
}
