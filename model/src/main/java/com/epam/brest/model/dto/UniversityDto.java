package com.epam.brest.model.dto;

public class UniversityDto {

    private Integer universityId;

    private String universityName;

    private Integer courseAverage;

    public UniversityDto(){
    }

    public UniversityDto(String universityName) {
        this.universityName = universityName;
    }

    public Integer getUniversityId() {
        return universityId;
    }

    public void setUniversityId(final Integer universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(final String universityName) {
        this.universityName = universityName;
    }

    public Integer getCourseAverage() {
        return courseAverage;
    }

    public void setCourseAverage(final Integer courseAverage){
        this.courseAverage = courseAverage;
    }

    @Override
    public String toString() {
        return "UniversityDto{" +
                "universityId=" + universityId +
                ", universityName='" + universityName + '\'' +
                ", courseAverage=" + courseAverage +
                '}';
    }
}
