package com.epam.brest.service.rest;

import com.epam.brest.model.dto.StudentDto;
import com.epam.brest.service.StudentDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StudentDtoServiceRest implements StudentDtoService {

    private final Logger logger = LogManager.getLogger(StudentDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    StudentDtoServiceRest() {}

    public StudentDtoServiceRest(String url, RestTemplate restTemplate) {
        this();
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<StudentDto> findAllDto() {
        logger.debug("findAllDto()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<StudentDto>) responseEntity.getBody();
    }

    @Override
    public List<StudentDto> findAllWithDate(String startDate, String endDate) {
        logger.debug("findAllWithDate({}, {})", startDate, endDate);
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/filter?startDate={sd}&endDate={ed}",
                List.class, startDate, endDate);
        return (List<StudentDto>) responseEntity.getBody();
    }
}
