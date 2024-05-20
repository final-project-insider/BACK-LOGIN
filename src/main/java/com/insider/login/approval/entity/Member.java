package com.insider.login.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity(name="ApprovalMember")
@Table(name="member_info")
public class Member {

    @Column(name="name")
    private String name;                //이름

    @Id
    @Column(name="member_id")
    private int memberId;               //사원번호

    @Column(name="password")
    private String password;            //비밀번호

    @Column(name="depart_no")
    private int departNo;               //부서번호

    @Column(name="position_level")
    private String positionLevel;        //직급이름

    @Column(name="employed_date")
    private Date employedDate;          //입사일자

    @Column(name="address")
    private String address;             //주소

    @Column(name="phone_no")
    private String phoneNo;             //연락처

    @Column(name="member_status")
    private String memberStatus;        //재직상태

    @Column(name="email")
    private String email;               //이메일

    @Column(name="member_role")
    private String memberRole;          //자격

    @Column(name="image_url")
    private String image_url;          //이미지번호

    protected Member(){}

    public Member(String name, int memberId, String password, int departNo, String positionLevel, Date employedDate, String address, String phoneNo, String memberStatus, String email, String memberRole, String image_url) {
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
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getPassword() {
        return password;
    }

    public int getDepartNo() {
        return departNo;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public Date getEmployedDate() {
        return employedDate;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public String getEmail() {
        return email;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public String getImage_url() {
        return image_url;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", memberId=" + memberId +
                ", password='" + password + '\'' +
                ", departNo=" + departNo +
                ", positionName='" + positionLevel + '\'' +
                ", employedDate=" + employedDate +
                ", address='" + address + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", memberStatus='" + memberStatus + '\'' +
                ", email='" + email + '\'' +
                ", memberRole='" + memberRole + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
