package com.epam.brest.web_app.validators;

import com.epam.brest.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
public class StudentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstName", "studentFirstName.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "studentLastName.empty");
        ValidationUtils.rejectIfEmpty(errors, "enrollmentDate", "studentEnrollmentDate.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "studentEmail.empty");
        ValidationUtils.rejectIfEmpty(errors, "course", "studentCourse.empty");
        ValidationUtils.rejectIfEmpty(errors, "rating", "studentRating.empty");
        ValidationUtils.rejectIfEmpty(errors, "universityId", "studentUniversityId.empty");
        Student student = (Student) target;
        if (StringUtils.hasLength(student.getEmail())
                && !student.getEmail().matches("^(.+)@(.+)$")) {
            errors.rejectValue("email", "studentEmail.NotAnEmail");
        }
        if (student.getCourse() != null) {
            if (student.getCourse() < 1 || student.getCourse() > 5) {
                errors.rejectValue("course", "studentCourse.outOfRange");
            }
        }
        if (student.getRating() != null) {
            if (student.getRating().compareTo(BigDecimal.ZERO) < 0 || student.getRating().compareTo(BigDecimal.TEN) > 0) {
                errors.rejectValue("rating", "studentRating.outOfRange");
            }
        }
    }
}
