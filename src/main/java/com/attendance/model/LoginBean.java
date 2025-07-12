package com.attendance.model;

public class LoginBean {
    private String loginId;
    private String password;
    private String role;

    // Getters and Setters
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Method to validate user credentials (for simplicity, we can just check if all fields are not empty)
    public boolean isValid() {
        return loginId != null && !loginId.isEmpty() && password != null && !password.isEmpty() && role != null && !role.isEmpty();
    }
}
