package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 08/04/2018.
 */

public class DailyAttendance {

    private int id;
    private String data, status, subject, topic, time, comment;

    public DailyAttendance(int id, String data, String status, String subject, String topic, String time, String comment) {
        this.id = id;
        this.data = data;
        this.status = status;
        this.subject = subject;
        this.topic = topic;
        this.time = time;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
