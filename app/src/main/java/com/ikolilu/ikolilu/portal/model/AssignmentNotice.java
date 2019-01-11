package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 01/04/2018.
 */

public class AssignmentNotice {
    private int nId;
    private String nTitle;
    private String nDescription;
    private String nSchool;
    private String nAttachment;
    private String nTime;

    public AssignmentNotice(int nId, String nTitle, String nDescription, String nSchool, String nAttachment, String nTime) {
        this.nId = nId;
        this.nTitle = nTitle;
        this.nDescription = nDescription;
        this.nSchool = nSchool;
        this.nAttachment = nAttachment;
        this.nTime = nTime;
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public String getnDescription() {
        return nDescription;
    }

    public void setnDescription(String nDescription) {
        this.nDescription = nDescription;
    }

    public String getnSchool() {
        return nSchool;
    }

    public void setnSchool(String nSchool) {
        this.nSchool = nSchool;
    }

    public String getnAttachment() {
        return nAttachment;
    }

    public void setnAttachment(String nAttachment) {
        this.nAttachment = nAttachment;
    }

    public String getnTime() {
        return nTime;
    }

    public void setnTime(String nTime) {
        this.nTime = nTime;
    }
}
