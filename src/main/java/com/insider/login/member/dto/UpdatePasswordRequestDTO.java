package com.insider.login.member.dto;

public class UpdatePasswordRequestDTO {

    private String currentPassword;
    private String newPassword1;
    private String newPassword2;

    public UpdatePasswordRequestDTO() {
    }

    public UpdatePasswordRequestDTO(String currentPassword, String newPassword1, String newPassword2) {
        this.currentPassword = currentPassword;
        this.newPassword1 = newPassword1;
        this.newPassword2 = newPassword2;
    }

    public UpdatePasswordRequestDTO(String newPassword1, String newPassword2) {
        this.newPassword1 = newPassword1;
        this.newPassword2 = newPassword2;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    @Override
    public String toString() {
        return "UpdatePasswordRequest{" +
                "currentPassword='" + currentPassword + '\'' +
                ", newPassword1='" + newPassword1 + '\'' +
                ", newPassword2='" + newPassword2 + '\'' +
                '}';
    }
}
