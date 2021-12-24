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

    @Value("${SQL_FIND_ALL_WITH_AVG_RATING}")
    private String findAllWithAvgRatingSql;


    public UniversityDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<UniversityDto> findAllWithAvgRating() {
        return namedParameterJdbcTemplate.query(findAllWithAvgRatingSql,
                BeanPropertyRowMapper.newInstance(UniversityDto.class));
    }

}
