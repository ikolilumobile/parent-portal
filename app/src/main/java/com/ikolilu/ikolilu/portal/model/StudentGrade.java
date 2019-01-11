package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 10/04/2018.
 */

public class StudentGrade {
    private int id;
    private String subject;
    private String classScore;
    private String examScore;
    private String total;
    private String position;
    private String grade;
    private String comment;

    public StudentGrade(int id, String subject, String classScore, String examScore, String total, String position, String grade, String comment) {
        this.id = id;
        this.subject = subject;
        this.classScore = classScore;
        this.examScore = examScore;
        this.total = total;
        this.position = position;
        this.grade = grade;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClassScore() {
        return classScore;
    }

    public void setClassScore(String classScore) {
        this.classScore = classScore;
    }

    public String getExamScore() {
        return examScore;
    }

    public void setExamScore(String examScore) {
        this.examScore = examScore;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
