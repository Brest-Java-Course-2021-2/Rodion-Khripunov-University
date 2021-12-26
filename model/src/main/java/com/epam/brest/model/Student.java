package com.epam.brest.model;

import java.math.BigDecimal;

public class Student {

    private Integer studentId;

    private String firstName;

    private String lastName;

    private String email;

    private Integer course;

    private BigDecimal rating;

    private Integer universityId;

    public Integer getStudentId() {
        return studentId;
    }

    public Student() {}

    public Student(String firstName, String lastName, String email , Integer universityId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.course = 1;
        this.rating = BigDecimal.valueOf(0);
        this.universityId = universityId;
    }

    public Student(Integer studentId, String firstName, String lastName, String email, Integer course,
                   BigDecimal rating, Integer universityId) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.course = course;
        this.rating = rating;
        this.universityId = universityId;
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

    public Integer getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Integer universityId) {
        this.universityId = universityId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", course=" + course +
                ", rating=" + rating +
                ", universityId=" + universityId +
                '}';
    }
}
