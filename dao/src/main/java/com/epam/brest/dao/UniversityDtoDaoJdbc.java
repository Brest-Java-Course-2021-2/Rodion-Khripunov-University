package com.epam.brest.dao;

import com.epam.brest.UniversityDtoDao;
import com.epam.brest.model.dto.UniversityDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UniversityDtoDaoJdbc implements UniversityDtoDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SQL_FIND_ALL_WITH_AVG_COURSE}")
    private String findAllWithAvgCourseSql;


    public UniversityDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<UniversityDto> findAllWithAvgCourse() {
        List<UniversityDto> universities = namedParameterJdbcTemplate.query(findAllWithAvgCourseSql,
               BeanPropertyRowMapper.newInstance(UniversityDto.class));
        return universities;
    }

}
