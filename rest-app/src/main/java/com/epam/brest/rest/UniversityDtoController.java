package com.epam.brest.rest;

import com.epam.brest.model.dto.UniversityDto;
import com.epam.brest.service.UniversityDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UniversityDtoController {
    private final Logger logger = LogManager.getLogger(UniversityDtoController.class);

    private final UniversityDtoService universityDtoService;

    public UniversityDtoController(UniversityDtoService universityDtoService) {
        this.universityDtoService = universityDtoService;
    }

    @GetMapping(value = "/university_dto")
    public final Collection<UniversityDto> universityDto() {
        logger.debug("universityDto()");
        return universityDtoService.findAllWithAvgCourse();
    }
}
