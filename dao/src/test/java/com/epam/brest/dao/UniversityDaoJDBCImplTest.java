package com.epam.brest.dao;

import com.epam.brest.model.University;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class UniversityDaoJDBCImplTest {

    @InjectMocks
    private UniversityDaoJDBCImpl universityDaoJDBC;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Captor
    private ArgumentCaptor<RowMapper<University>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void findAll() {
        String sql = "select";
        ReflectionTestUtils.setField(universityDaoJDBC, "sqlUniversityAll", sql);
        University university = new University();
        List<University> list = Collections.singletonList(university);
        Mockito.when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<RowMapper<University>>any())).thenReturn(list);

        List<University> result = universityDaoJDBC.findAll();

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), captorMapper.capture());
        RowMapper<University> mapper = captorMapper.getValue();

        assertNotNull(mapper);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertSame(university, result.get(0));
    }

    @Test
    public void getUniversityById() {
        int id = 0;
        String sql = "get";
        ReflectionTestUtils.setField(universityDaoJDBC, "sqlUniversityById", sql);
        University university = new University();
        Mockito.when(namedParameterJdbcTemplate.queryForObject(any(), ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<University>>any())).thenReturn(university);

        University result = universityDaoJDBC.getUniversityById(id);

        Mockito.verify(namedParameterJdbcTemplate).queryForObject(eq(sql), captorSource.capture(),captorMapper.capture());
        SqlParameterSource source = captorSource.getValue();
        RowMapper<University> mapper = captorMapper.getValue();

        assertNotNull(source);
        assertNotNull(mapper);
        assertNotNull(result);
        assertSame(university, result);
    }
}
