package com.attendance.model;

public class StudentBean {
    private int userId;
    private String rollNo;
    private String password;
    private String fullName;

    public StudentBean() {
    }

    public StudentBean(int userId, String rollNo, String password, String fullName) {
        this.userId = userId;
        this.rollNo = rollNo;
        this.password = password;
        this.fullName = fullName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
