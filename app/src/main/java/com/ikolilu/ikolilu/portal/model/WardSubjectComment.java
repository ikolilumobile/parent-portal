package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 25/07/2018.
 */

public class WardSubjectComment {
    private int cId;
    private String cSubject;
    private String cComment;
    private String cTotal;
    private String cTeacher;

    public WardSubjectComment(int id, String subject, String comment, String total, String teacher)
    {
        this.cId = id;
        this.cSubject = subject;
        this.cComment = comment;
        this.cTotal = total;
        this.cTeacher = teacher;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcSubject() {
        return cSubject;
    }

    public void setcSubject(String cSubject) {
        this.cSubject = cSubject;
    }

    public String getcComment() {
        return cComment;
    }

    public void setcComment(String cComment) {
        this.cComment = cComment;
    }

    public String getcTotal() {
        return cTotal;
    }

    public void setcTotal(String cTotal) {
        this.cTotal = cTotal;
    }

    public String getcTeacher() {
        return cTeacher;
    }

    public void setcTeacher(String cTeacher) {
        this.cTeacher = cTeacher;
    }
}
