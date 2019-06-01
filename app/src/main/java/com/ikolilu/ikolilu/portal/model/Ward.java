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
    private String program;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
    public Ward(int id, String wardId, String schoolName, String studentName, String image, String term, String wClass, String program) {
        this.id = id;
        this.wardId = wardId;
        this.schoolName = schoolName;
        this.studentName = studentName;
        this.image = image;
        this.term = term;
        this.wClass = wClass;
        this.program = program;
    }

    public Ward(int id, String wardId, String schoolName, String studentName, String image, String term, String wClass, String schoolId, String program) {
        this.id = id;
        this.wardId = wardId;
        this.schoolName = schoolName;
        this.studentName = studentName;
        this.image = image;
        this.term = term;
        this.wClass = wClass;
        this.schoolId = schoolId;
        this.program = program;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
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
