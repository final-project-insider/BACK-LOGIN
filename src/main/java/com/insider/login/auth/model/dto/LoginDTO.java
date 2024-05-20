package com.insider.login.auth.model.dto;

public class LoginDTO {

    private int memberId;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(int memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "memberId=" + memberId +
                ", password='" + password + '\'' +
                '}';
    }
}