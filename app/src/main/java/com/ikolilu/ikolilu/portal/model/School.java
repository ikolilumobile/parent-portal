package com.ikolilu.ikolilu.portal.model;

import java.io.Serializable;

/**
 * Created by Genuis on 08/05/2018.
 */

public class School implements Serializable{

    public static final String TAG = "School";
    private int id;
    private String SchoolName;
    private String SchoolCode;

    public School(){}

    public School(int id, String schoolName, String schoolCode) {
        this.id = id;
        SchoolName = schoolName;
        SchoolCode = schoolCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public String getSchoolCode() {
        return SchoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        SchoolCode = schoolCode;
    }
}
