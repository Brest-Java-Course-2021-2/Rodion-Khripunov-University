package com.epam.brest.rest;

import com.epam.brest.model.dto.StudentDto;
import com.epam.brest.service.StudentDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class StudentDtoController {

    private final Logger logger = LogManager.getLogger(StudentDtoController.class);

    private final StudentDtoService studentDtoService;

    public StudentDtoController(StudentDtoService studentDtoService) {
        this.studentDtoService = studentDtoService;
    }

    @GetMapping(value = "/student_dto")
    public final Collection<StudentDto> studentDto() {
        logger.debug("studentDto()");
        return studentDtoService.findAllDto();
    }

    @GetMapping(value = "/student_dto/filter")
    public final Collection<StudentDto> studentDtoWithDate(@RequestParam(value = "startDate") String startDate,
                                                           @RequestParam(value = "endDate") String endDate) {
        logger.debug("studentDtoWithDate()");
        return studentDtoService.findAllWithDate(startDate, endDate);
    }
}

