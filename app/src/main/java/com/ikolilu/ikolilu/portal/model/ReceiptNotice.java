package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 02/04/2018.
 */

public class ReceiptNotice {
    private int nId;
    private String rTitle;
    private String rDescription;
    private String rSchool;
    private String rAttachment;
    private String rTime;

    public ReceiptNotice(int nId, String rTitle, String rDescription, String rSchool, String rAttachment, String rTime) {
        this.nId = nId;
        this.rTitle = rTitle;
        this.rDescription = rDescription;
        this.rSchool = rSchool;
        this.rAttachment = rAttachment;
        this.rTime = rTime;
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getrTitle() {
        return rTitle;
    }

    public void setrTitle(String rTitle) {
        this.rTitle = rTitle;
    }

    public String getrDescription() {
        return rDescription;
    }

    public void setrDescription(String rDescription) {
        this.rDescription = rDescription;
    }

    public String getrSchool() {
        return rSchool;
    }

    public void setrSchool(String rSchool) {
        this.rSchool = rSchool;
    }

    public String getrAttachment() {
        return rAttachment;
    }

    public void setrAttachment(String rAttachment) {
        this.rAttachment = rAttachment;
    }

    public String getrTime() {
        return rTime;
    }

    public void setrTime(String rTime) {
        this.rTime = rTime;
    }
}
