package com.insider.login.auth.image.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "member_image")
public class Image {


    @Id
    @Column(name = "member_image_no", nullable = false)
    private int memberImageNo;
//    @Column(name = "member_image_name", nullable = false)
//    private String memberImageName;
    @Column(name = "member_image_path", nullable = false)
    private String memberImagePath;

    public Image() {
    }

    public Image(int memberImageNo, String memberImageName, String memberImagePath) {
        this.memberImageNo = memberImageNo;
//        this.memberImageName = memberImageName;
        this.memberImagePath = memberImagePath;
    }

    public int getMemberImageNo() {
        return memberImageNo;
    }

    public void setMemberImageNo(int memberImageNo) {
        this.memberImageNo = memberImageNo;
    }

//    public String getMemberImageName() {
//        return memberImageName;
//    }
//
//    public void setMemberImageName(String memberImageName) {
//        this.memberImageName = memberImageName;
//    }

    public String getMemberImagePath() {
        return memberImagePath;
    }

    public void setMemberImagePath(String memberImagePath) {
        this.memberImagePath = memberImagePath;
    }

    @Override
    public String toString() {
        return "ImageController{" +
                "memberImageNo=" + memberImageNo +
//                ", memberImageName='" + memberImageName + '\'' +
                ", memberImagePath='" + memberImagePath + '\'' +
                '}';
    }
}