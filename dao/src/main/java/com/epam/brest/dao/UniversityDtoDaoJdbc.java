package com.epam.brest.dao;

import com.epam.brest.UniversityDtoDao;
import com.epam.brest.model.dto.UniversityDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class UniversityDtoDaoJdbc implements UniversityDtoDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String findAllWithAvgSalarySql = "SELECT\n" +
            "\tu.university_id AS universityId,\n" +
            "\tu.university_name AS universityName,\n" +
            "\tavg(s.course) AS courseAverage\n" +
            "FROM\n" +
            "\tuniversity u\n" +
            "LEFT JOIN student s ON\n" +
            "\ts.university_id = u.university_id\n" +
            "GROUP BY\n" +
            "\tu.university_id,\n" +
            "\tu.university_name\n" +
            "ORDER BY\n" +
            "\tuniversity_name";


    public UniversityDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<UniversityDto> findAllWithAvgCourse() {
        List<UniversityDto> universities = namedParameterJdbcTemplate.query(findAllWithAvgSalarySql,
               BeanPropertyRowMapper.newInstance(UniversityDto.class));
        return universities;
    }

}
