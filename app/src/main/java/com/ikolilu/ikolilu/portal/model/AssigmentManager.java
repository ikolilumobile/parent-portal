package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 09/04/2018.
 */

public class AssigmentManager {
    private int id;
    private String subject;
    private String markScore;
    private String endDate;
    private String viewQuestion;
    private String viewSubmission;

    public AssigmentManager(int id, String subject, String markScore, String endDate, String viewQuestion, String viewSubmission) {
        this.id = id;
        this.subject = subject;
        this.markScore = markScore;
        this.endDate = endDate;
        this.viewQuestion = viewQuestion;
        this.viewSubmission = viewSubmission;
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

    public String getMarkScore() {
        return markScore;
    }

    public void setMarkScore(String markScore) {
        this.markScore = markScore;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getViewQuestion() {
        return viewQuestion;
    }

    public void setViewQuestion(String viewQuestion) {
        this.viewQuestion = viewQuestion;
    }

    public String getViewSubmission() {
        return viewSubmission;
    }

    public void setViewSubmission(String viewSubmission) {
        this.viewSubmission = viewSubmission;
    }
}
