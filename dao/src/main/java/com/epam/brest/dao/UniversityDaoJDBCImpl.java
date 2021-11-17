package com.epam.brest.dao;

import com.epam.brest.UniversityDao;
import com.epam.brest.model.University;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UniversityDaoJDBCImpl implements UniversityDao {

    private final Logger logger = LogManager.getLogger(UniversityDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_UNIVERSITIES="select u.university_id, u.university_name from university u order by u.university_name";
    private final String SQL_CREATE_UNIVERSITY="insert into university(university_name) values(:university_name)";

    public UniversityDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<University> findAll() {
        logger.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(SQL_ALL_UNIVERSITIES, new UniversityRowMapper());
    }

    @Override
    public Integer create(University university) {
        logger.debug("Start: create({})", university);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("university_name", university.getUniversityName().toUpperCase());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_UNIVERSITY, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer update(University university) {
        return null;
    }

    @Override
    public Integer delete(Integer universityId) {
        return null;
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
