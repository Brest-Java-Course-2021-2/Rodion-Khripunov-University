package com.epam.brest.rest.exception;

import com.epam.brest.service.exceptions.StudentNotFoundException;
import com.epam.brest.service.exceptions.UniversityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    public static final String UNIVERSITY_NOT_FOUND = "university.not_found";
    public static final String STUDENT_NOT_FOUND = "student.not_found";
    public static final String VALIDATION_ERROR = "validation_error";

    @ExceptionHandler(UniversityNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUniversityNotFoundException (UniversityNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(
                new ErrorResponse(UNIVERSITY_NOT_FOUND, details)
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleStudentNotFoundException (StudentNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(
                new ErrorResponse(STUDENT_NOT_FOUND, details)
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse(VALIDATION_ERROR, ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
