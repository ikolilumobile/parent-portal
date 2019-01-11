package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 29/03/2018.
 */

public class Ward {

    private int id;
    private String wardId, schoolName;
    private String studentName;
    private String image;

    private String term;
    private String wClass;
    private String schoolId;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
    public Ward(int id, String wardId, String schoolName, String studentName, String image, String term, String wClass) {
        this.id = id;
        this.wardId = wardId;
        this.schoolName = schoolName;
        this.studentName = studentName;
        this.image = image;
        this.term = term;
        this.wClass = wClass;
    }

    public Ward(int id, String wardId, String schoolName, String studentName, String image, String term, String wClass, String schoolId) {
        this.id = id;
        this.wardId = wardId;
        this.schoolName = schoolName;
        this.studentName = studentName;
        this.image = image;
        this.term = term;
        this.wClass = wClass;
        this.schoolId = schoolId;
    }

    public Ward(){}
    public int getId() {
        return id;
    }

    public String getWardId() {
        return wardId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getImage() {
        return image;
    }

    public String getTerm(){ return term; }

    public String getwClass() { return wClass; }

    public void setId(int id) {
        this.id = id;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTerm(String term) { this.term = term; }

    public void setwClass(String wClass) { this.wClass = wClass; }

}
