package com.epam.brest.dao;

import com.epam.brest.UniversityDao;
import com.epam.brest.model.University;
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
import java.util.List;

@Component
public class UniversityDaoJDBCImpl implements UniversityDao {

    private final Logger logger = LogManager.getLogger(UniversityDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SELECT_COUNT_FROM_UNIVERSITY}")
    private String sqlUniversityCount;

    @Value("${SQL_ALL_UNIVERSITIES}")
    private String sqlUniversityAll;

    @Value("${SQL_UNIVERSITY_BY_ID}")
    private String sqlUniversityById;

    @Value("${SQL_CHECK_UNIQUE_UNIVERSITY_NAME}")
    private String sqlUniversityCheckUniqueName;

    @Value("${SQL_CREATE_UNIVERSITY}")
    private String sqlUniversityCreate;

    @Value("${SQL_UPDATE_UNIVERSITY_NAME}")
    private String sqlUniversityUpdateName;

    @Value("${SQL_DELETE_UNIVERSITY_BY_ID}")
    private String sqlUniversityDeleteById;

    public UniversityDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<University> findAll() {
        logger.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(sqlUniversityAll, new UniversityRowMapper());
    }

    @Override
    public University getUniversityById(Integer universityId) {
        logger.debug("GetUniversityById({})", universityId);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("universityId", universityId);
        return namedParameterJdbcTemplate.queryForObject(sqlUniversityById, sqlParameterSource, new UniversityRowMapper());
    }

    @Override
    public Integer create(University university) {
        logger.debug("Start: create({})", university);
        if (!isUniversityUnique(university.getUniversityName())) {
            logger.warn("University with the same name {} already exists.", university.getUniversityName());
            throw new IllegalArgumentException("University with the same name already exists in DB.");
        }
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("universityName", university.getUniversityName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlUniversityCreate, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    private boolean isUniversityUnique(String universityName) {
        logger.debug("Check university name: {} on unique", universityName);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("universityName", universityName);
        return namedParameterJdbcTemplate.queryForObject(sqlUniversityCheckUniqueName, sqlParameterSource, Integer.class) == 0;
    }

    @Override
    public Integer update(University university) {
        logger.debug("Start: update({})", university);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("universityName", university.getUniversityName())
                        .addValue("universityId", university.getUniversityId());
        return namedParameterJdbcTemplate.update(sqlUniversityUpdateName, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer universityId) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("universityId", universityId);
        return namedParameterJdbcTemplate.update(sqlUniversityDeleteById, sqlParameterSource);
    }

    @Override
    public Integer count() {
        logger.debug("Count()");
        return namedParameterJdbcTemplate.queryForObject(sqlUniversityCount, new MapSqlParameterSource(), Integer.class);
    }

    private class UniversityRowMapper implements RowMapper<University> {

        @Override
        public University mapRow(ResultSet resultSet, int i) throws SQLException {
            University university = new University();
            university.setUniversityId(resultSet.getInt("university_id"));
            university.setUniversityName(resultSet.getString("university_name"));
            return university;
        }
    }
}
