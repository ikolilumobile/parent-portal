package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 11/04/2018.
 */

public class StudentScore {

    private int id;
    private String classworkId;
    private String score;
    private String data;
    private String subject;
    private String subjectName;

    public StudentScore(int id, String classworkId, String score, String data, String subject, String subjectName) {
        this.id = id;
        this.classworkId = classworkId;
        this.score = score;
        this.data = data;
        this.subject = subject;
        this.subjectName = subjectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassworkId() {
        return classworkId;
    }

    public void setClassworkId(String classworkId) {
        this.classworkId = classworkId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
