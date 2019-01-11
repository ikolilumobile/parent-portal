package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 27/08/2018.
 */

public class WardDailyActivity {
    private long id;
    private String teacherName;
    private String actions;
    private String description;
    private String image;
    private String date;

    public WardDailyActivity(long id, String teacherName, String actions, String description, String image, String date) {
        this.id = id;
        this.teacherName = teacherName;
        this.actions = actions;
        this.description = description;
        this.image = image;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
