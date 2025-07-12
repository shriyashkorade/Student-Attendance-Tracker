package com.attendance.model;

public class AttendanceSummaryBean {
    private String rollNo;
    private String fullName;
    private String subjectName;
    private int totalLectures;
    private int lecturesAttended;
    private double percentage;

    // Getters and setters
    public String getRollNo() {
        return rollNo;
    }
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
    	this.subjectName = subjectName;
    }
    
    public int getTotalLectures() {
        return totalLectures;
    }
    public void setTotalLectures(int totalLectures) {
        this.totalLectures = totalLectures;
    }

    public int getLecturesAttended() {
        return lecturesAttended;
    }
    public void setLecturesAttended(int lecturesAttended) {
        this.lecturesAttended = lecturesAttended;
    }

    public double getPercentage() {
        return percentage;
    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    // Calculate attendance percentage based on totalLectures and lecturesAttended
    public void calculatePercentage() {
        if (totalLectures > 0) {
            percentage = ((double) lecturesAttended / totalLectures) * 100;
        } else {
            percentage = 0.0;
        }
    }
}
