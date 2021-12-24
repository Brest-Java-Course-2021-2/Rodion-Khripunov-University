package com.epam.brest.model;

public class University {

    private Integer universityId;

    private String universityName;

    public University(){
    }

    public University(String universityName) {
        this.universityName = universityName;
    }

    public University(Integer universityId, String universityName) {
        this.universityId = universityId;
        this.universityName = universityName;
    }

    public Integer getUniversityId() {
        return universityId;
    }

//    public void setUniversityId(Integer universityId) {
//        this.universityId = universityId;
//    }

    public University setUniversityId(Integer universityId) {
        this.universityId = universityId;
        return this;
    }

    public String getUniversityName() {
        return universityName;
    }

//    public void setUniversityName(String universityName) {
//        this.universityName = universityName;
//    }

    public University setUniversityName(String universityName) {
        this.universityName = universityName;
        return this;
    }

    @Override
    public String toString() {
        return "University{" +
                "universityId=" + universityId +
                ", universityName='" + universityName + '\'' +
                '}';
    }
}
