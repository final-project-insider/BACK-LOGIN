package com.insider.login.approval.dto;

import java.sql.Date;

public class MemberDTO {

    private String name;                //이름
    private int memberId;               //사번
    private String password;            //비밀번호
    private int departNo;               //부서번호
    private String positionLevel;        //직급번호
    private Date employedDate;          //입사일
    private String address;             //주소
    private String phoneNo;             //연락처
    private String memberStatus;        //재직상태
    private String email;               //이메일
    private String memberRole;          //자격
    private String imageUrl;            //이미지url
    private String departName;          //부서명

    private String positionName;        //직급명

    public MemberDTO(){}

    public MemberDTO(String name, int memberId, String password, int departNo, String positionLevel, Date employedDate, String address, String phoneNo, String memberStatus, String email, String memberRole, String imageUrl, String departName) {
        this.name = name;
        this.memberId = memberId;
        this.password = password;
        this.departNo = departNo;
        this.positionLevel = positionLevel;
        this.employedDate = employedDate;
        this.address = address;
        this.phoneNo = phoneNo;
        this.memberStatus = memberStatus;
        this.email = email;
        this.memberRole = memberRole;
        this.imageUrl = imageUrl;
        this.departName = departName;
    }

    public MemberDTO(String name, int memberId, String password, int departNo, String positionLevel, Date employedDate, String address, String phoneNo, String memberStatus, String email, String memberRole, String imageUrl) {
        this.name = name;
        this.memberId = memberId;
        this.password = password;
        this.departNo = departNo;
        this.positionLevel = positionLevel;
        this.employedDate = employedDate;
        this.address = address;
        this.phoneNo = phoneNo;
        this.memberStatus = memberStatus;
        this.email = email;
        this.memberRole = memberRole;
        this.imageUrl = imageUrl;
    }

    public MemberDTO(String name, int memberId, String password, int departNo, String positionLevel, Date employedDate, String address, String phoneNo, String memberStatus, String email, String memberRole, String imageUrl, String departName, String positionName) {
        this.name = name;
        this.memberId = memberId;
        this.password = password;
        this.departNo = departNo;
        this.positionLevel = positionLevel;
        this.employedDate = employedDate;
        this.address = address;
        this.phoneNo = phoneNo;
        this.memberStatus = memberStatus;
        this.email = email;
        this.memberRole = memberRole;
        this.imageUrl = imageUrl;
        this.departName = departName;
        this.positionName = positionName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getDepartNo() {
        return departNo;
    }

    public void setDepartNo(int departNo) {
        this.departNo = departNo;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public Date getEmployedDate() {
        return employedDate;
    }

    public void setEmployedDate(Date employedDate) {
        this.employedDate = employedDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "name='" + name + '\'' +
                ", memberId=" + memberId +
                ", password='" + password + '\'' +
                ", departNo=" + departNo +
                ", positionLevel='" + positionLevel + '\'' +
                ", employedDate=" + employedDate +
                ", address='" + address + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", memberStatus='" + memberStatus + '\'' +
                ", email='" + email + '\'' +
                ", memberRole='" + memberRole + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", departName='" + departName + '\'' +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
