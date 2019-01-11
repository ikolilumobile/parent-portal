package com.ikolilu.ikolilu.portal.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Genuis on 01/04/2018.
 */

public class NoticeBoard extends RealmObject{
    @PrimaryKey
    private int nId;
    private String nType;
    private String nDescription;
    private String nSchool;
    private String nAttachment;
    private String nTime;

    public NoticeBoard(){

    }

    public NoticeBoard(int nId, String nType, String nDescription, String nSchool, String nAttachment, String time) {
        this.nId = nId;
        this.nType = nType;
        this.nDescription = nDescription;
        this.nSchool = nSchool;
        this.nAttachment = nAttachment;
        this.nTime = time;
    }

    public long getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getnType() {
        return nType;
    }

    public void setnType(String nType) {
        this.nType = nType;
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

    public void setnTime(String nTime){
        this.nTime = nTime;
    }
}
