package com.example.fsh_noticet;

public class TeacherInfo {
    private String fullName;
    private String department;
    private String phone;
    private String email;

    public TeacherInfo() {
        //Necessary
    }

    public TeacherInfo(String fullName, String department, String phone, String email) {
        this.fullName = fullName;
        this.department = department;
        this.phone = phone;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
