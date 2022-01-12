package com.epam.brest.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StudentDto {

    private Integer studentId;

    private String firstName;

    private String lastName;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate enrollmentDate;

    private String email;

    private Integer course;

    private BigDecimal rating;

    private String universityName;

    public StudentDto() {}

    public StudentDto(String firstName, String lastName, String email , String universityName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.enrollmentDate = LocalDate.now();
        this.email = email;
        this.course = 1;
        this.rating = BigDecimal.valueOf(0);
        this.universityName = universityName;
    }

    public StudentDto(Integer studentId, String firstName, String lastName, LocalDate enrollmentDate, String email,
                   Integer course, BigDecimal rating, String universityName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enrollmentDate = enrollmentDate;
        this.email = email;
        this.course = course;
        this.rating = rating;
        this.universityName = universityName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public StudentDto setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                ", email='" + email + '\'' +
                ", course=" + course +
                ", rating=" + rating +
                ", universityName=" + universityName +
                '}';
    }
}