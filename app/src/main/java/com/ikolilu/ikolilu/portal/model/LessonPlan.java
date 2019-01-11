package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 09/04/2018.
 */

public class LessonPlan {
    private int id;
    private String subject;
    private String date;
    private String unit;
    private String viewTimeTable;
    private String viewUnits;

    public LessonPlan(int id, String subject, String date, String unit, String viewTimeTable, String viewUnits) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.unit = unit;
        this.viewTimeTable = viewTimeTable;
        this.viewUnits = viewUnits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getViewTimeTable() {
        return viewTimeTable;
    }

    public void setViewTimeTable(String viewTimeTable) {
        this.viewTimeTable = viewTimeTable;
    }

    public String getViewUnits() {
        return viewUnits;
    }

    public void setViewUnits(String viewUnits) {
        this.viewUnits = viewUnits;
    }
}
