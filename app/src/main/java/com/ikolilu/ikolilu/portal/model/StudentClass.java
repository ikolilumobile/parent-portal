package com.ikolilu.ikolilu.portal.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Genuis on 08/05/2018.
 */

public class StudentClass extends RealmObject {
    @PrimaryKey
    private int id; // init primary key
    private String schoolId; //school ID
    private String studentId; //ward ID
    private String className;

    public StudentClass(){}

    public StudentClass(int id, String schoolId, String studentId, String className) {
        this.id = id;
        this.schoolId = schoolId;
        this.studentId = studentId;
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
