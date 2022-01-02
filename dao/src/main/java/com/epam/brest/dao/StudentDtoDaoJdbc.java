package com.epam.brest.dao;

import com.epam.brest.StudentDtoDao;
import com.epam.brest.model.dto.StudentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class StudentDtoDaoJdbc implements StudentDtoDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StudentDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Value("${SQL_FIND_ALL_STUDENT_DTO}")
    private String findAllDto;

    @Value("${SQL_FIND_ALL_WITH_DATE}")
    private String findAllWithWithDate;

    @Override
    public List<StudentDto> findAllDto() {
        return namedParameterJdbcTemplate.query(findAllDto, BeanPropertyRowMapper.newInstance(StudentDto.class));
    }

    @Override
    public List<StudentDto> findAllWithDate(LocalDate startDate, LocalDate endDate) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("startDate", startDate)
                        .addValue("endDate", endDate);
        return namedParameterJdbcTemplate.query(findAllWithWithDate, sqlParameterSource, BeanPropertyRowMapper.newInstance(StudentDto.class));
    }
}
