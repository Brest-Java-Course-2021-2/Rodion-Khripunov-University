package com.epam.brest.model.dto;

import java.math.BigDecimal;

public class UniversityDto {

    private Integer universityId;

    private String universityName;

    private BigDecimal avgRating;

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

    public BigDecimal getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(final BigDecimal avgRating){
            this.avgRating = avgRating;
    }

    @Override
    public String toString() {
        return "UniversityDto{" +
                "universityId=" + universityId +
                ", universityName='" + universityName + '\'' +
                ", avgRating=" + avgRating +
                '}';
    }
}
