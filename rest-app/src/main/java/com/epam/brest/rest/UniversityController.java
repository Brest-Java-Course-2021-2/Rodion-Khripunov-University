package com.epam.brest.rest;

import com.epam.brest.model.University;
import com.epam.brest.service.UniversityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class UniversityController {
    private static final Logger logger = LogManager.getLogger(UniversityController.class);

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping(value = "/universities")
    public final Collection<University> universities() {
        logger.debug("universities()");
        return universityService.findAll();
    }

    @GetMapping(value = "/universities/{id}")
    public final University getUniversityById(@PathVariable Integer id) {
        logger.debug("universityById({})", id);
        University university = universityService.getUniversityById(id);
        return university;
    }

    @PostMapping(path = "/universities", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createUniversity(@RequestBody University university) {
        logger.debug("createUniversity({})", university);
        Integer id = universityService.create(university);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(path = "/universities", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> updateUniversity(@RequestBody University university) {
        logger.debug("updateUniversity({})", university);
        int result = universityService.update(university);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(path = "/universities/{id}", produces = "application/json")
    public ResponseEntity<Integer> deleteUniversity(@PathVariable Integer id) {
        logger.debug("deleteUniversity({})", id);
        int result = universityService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}