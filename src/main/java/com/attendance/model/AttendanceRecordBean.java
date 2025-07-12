package com.attendance.model;

import java.util.Date;

public class AttendanceRecordBean {
    private Date date;
    private int lectureNo;
    private String subjectName;
    private String status;

    // Getters and Setters
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public int getLectureNo() {
        return lectureNo;
    }
    public void setLectureNo(int lectureNo) {
        this.lectureNo = lectureNo;
    }

    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
