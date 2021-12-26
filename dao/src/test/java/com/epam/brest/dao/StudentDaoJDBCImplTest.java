package com.epam.brest.dao;

import com.epam.brest.model.Student;
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
class StudentDaoJDBCImplTest {

    @InjectMocks
    private StudentDaoJDBCImpl studentDaoJDBC;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Captor
    private ArgumentCaptor<RowMapper<Student>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void findAll() {
        String sql = "select";
        ReflectionTestUtils.setField(studentDaoJDBC, "sqlStudentAll", sql);
        Student student = new Student();
        List<Student> list = Collections.singletonList(student);
        Mockito.when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<RowMapper<Student>>any())).thenReturn(list);

        List<Student> result = studentDaoJDBC.findAll();

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), captorMapper.capture());
        RowMapper<Student> mapper = captorMapper.getValue();

        assertNotNull(mapper);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertSame(student, result.get(0));
    }

    @Test
    public void getUniversityById() {
        int id = 0;
        String sql = "get";
        ReflectionTestUtils.setField(studentDaoJDBC, "sqlStudentById", sql);
        Student student = new Student();
        Mockito.when(namedParameterJdbcTemplate.queryForObject(any(), ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<Student>>any())).thenReturn(student);

        Student result = studentDaoJDBC.getStudentById(id);

        Mockito.verify(namedParameterJdbcTemplate).queryForObject(eq(sql), captorSource.capture(),captorMapper.capture());
        SqlParameterSource source = captorSource.getValue();
        RowMapper<Student> mapper = captorMapper.getValue();

        assertNotNull(source);
        assertNotNull(mapper);
        assertNotNull(result);
        assertSame(student, result);
    }
}