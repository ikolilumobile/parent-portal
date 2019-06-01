package com.ikolilu.ikolilu.portal.model;

public class PreschoolGrade {
    Integer id;
    String gradeCode;
    String gradeType;
    String gradeResult;
    PreschoolGradeKey key;

    public PreschoolGrade(Integer id, String gradeCode, String gradeType, String gradeResult, PreschoolGradeKey key) {
        this.id = id;
        this.gradeCode = gradeCode;
        this.gradeType = gradeType;
        this.gradeResult = gradeResult;
        this.key = key;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public PreschoolGradeKey getKey() {
        return key;
    }

    public void setKey(PreschoolGradeKey key) {
        this.key = key;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public String getGradeResult() {
        return gradeResult;
    }

    public void setGradeResult(String gradeResult) {
        this.gradeResult = gradeResult;
    }
}
