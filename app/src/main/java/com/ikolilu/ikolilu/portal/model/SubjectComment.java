package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 06/07/2018.
 */

public class SubjectComment {
    private int cId;
    private String cSubject;
    private String cComment;
    private String cTeacher;

    public SubjectComment(int id, String cSubject, String cComment, String cTeacher)
    {
        this.cId = id;
        this.cSubject = cSubject;
        this.cComment = cComment;
        this.cTeacher = cTeacher;
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

    public String getcTeacher() {
        return cTeacher;
    }

    public void setcTeacher(String cTeacher) {
        this.cTeacher = cTeacher;
    }
}
