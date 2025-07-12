package com.attendance.model;

public class RegisterBean {
    private String fullName;
    private String rollNo;
    private String password;
    private String confirmPassword;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}
