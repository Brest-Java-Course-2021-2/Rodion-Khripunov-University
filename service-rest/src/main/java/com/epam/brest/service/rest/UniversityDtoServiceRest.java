package com.epam.brest.service.rest;

import com.epam.brest.model.dto.UniversityDto;
import com.epam.brest.service.UniversityDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UniversityDtoServiceRest implements UniversityDtoService {

    private final Logger logger = LogManager.getLogger(UniversityDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public UniversityDtoServiceRest() {}

    public UniversityDtoServiceRest(String url, RestTemplate restTemplate) {
        this();
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<UniversityDto> findAllWithAvgRating() {
        logger.debug("findAllWithAvgRating()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<UniversityDto>) responseEntity.getBody();
    }
}
