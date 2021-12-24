package com.epam.brest.service.rest;

import com.epam.brest.model.University;
import com.epam.brest.service.UniversityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UniversityServiceRest implements UniversityService {

    private final Logger logger = LogManager.getLogger(UniversityServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public UniversityServiceRest() {}

    public UniversityServiceRest(String url, RestTemplate restTemplate) {
        this();
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<University> findAll() {
        logger.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<University>) responseEntity.getBody();
    }

    @Override
    public University getUniversityById(Integer universityId) {
        logger.debug("findById({})", universityId);
        ResponseEntity<University> responseEntity = restTemplate.getForEntity(url + "/" + universityId, University.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer create(University university) {
        logger.debug("create({})", university);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, university, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer update(University university) {
        logger.debug("update({})", university);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<University> entity = new HttpEntity<>(university, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer delete(Integer universityId) {
        logger.debug("delete({})", universityId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<University> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + universityId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer count() {
        logger.debug("count()");
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(url + "/count", Integer.class);
        return responseEntity.getBody();
    }
}
