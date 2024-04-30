package com.insider.login.auth.image.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class ImageDTO {

    private int memberImageNo;
    private String memberImageName;
    private String memberImagePath;

    public ImageDTO() {
    }

    public ImageDTO(int memberImageNo, String memberImageName, String memberImagePath) {
        this.memberImageNo = memberImageNo;
        this.memberImageName = memberImageName;
        this.memberImagePath = memberImagePath;
    }

    public int getMemberImageNo() {
        return memberImageNo;
    }

    public void setMemberImageNo(int memberImageNo) {
        this.memberImageNo = memberImageNo;
    }

    public String getMemberImageName() {
        return memberImageName;
    }

    public void setMemberImageName(String memberImageName) {
        this.memberImageName = memberImageName;
    }

    public String getMemberImagePath() {
        return memberImagePath;
    }

    public void setMemberImagePath(String memberImagePath) {
        this.memberImagePath = memberImagePath;
    }

    @Override
    public String toString() {
        return "ImageDTO{" +
                "memberImageNo=" + memberImageNo +
                ", memberImageName='" + memberImageName + '\'' +
                ", memberImagePath='" + memberImagePath + '\'' +
                '}';
    }
}
