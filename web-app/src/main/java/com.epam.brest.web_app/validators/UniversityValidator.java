package com.epam.brest.web_app.validators;

import com.epam.brest.model.University;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.epam.brest.model.constants.UniversityConstants.UNIVERSITY_NAME_SIZE;

@Component
public class UniversityValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return University.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "universityName", "universityName.empty");
        University university = (University) target;

        if (StringUtils.hasLength(university.getUniversityName())
                && university.getUniversityName().length() > UNIVERSITY_NAME_SIZE) {
            errors.rejectValue("universityName", "universityName.maxSize");
        }
    }
}
