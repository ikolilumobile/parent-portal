package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 09/05/2018.
 */

public class ExamType {
    private int id;
    private String szExamType;

    public ExamType(){}

    public ExamType(int id, String szExamType) {
        this.id = id;
        this.szExamType = szExamType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSzExamType() {
        return szExamType;
    }

    public void setSzExamType(String szExamType) {
        this.szExamType = szExamType;
    }
}
