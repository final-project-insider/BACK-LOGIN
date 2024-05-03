package com.insider.login.member.entity;

import com.insider.login.auth.image.entity.Image;

import com.insider.login.common.utils.MemberRole;
import com.insider.login.department.entity.Department;
import com.insider.login.position.entity.Position;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "member_info")
public class Member {                 // JPA를 사용을 할 것이기 때문에 entity 설계 필수

    @Id
    @Column(name = "member_id", nullable = false)
    private int memberId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password", nullable = false)
    private String password;
//    @Column(name = "depart_no", nullable = false)
//    private int departNo;
//    @Column(name = "position_name" ,nullable = false)
//    private String positionName;
    @Column(name = "employed_date", nullable = false)
    private LocalDate employedDate;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "phone_no", nullable = false)
    private String phoneNo;
    @Column(name = "member_status", nullable = false)
    private String memberStatus;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "member_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;
//    private com.insider.prefinal.common.UserRole userRole;
//    @Column(name = "member_image_no")
//    private int memberImageNo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "depart_no")
    private Department department;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "position_name")
    private Position position;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_image_no", referencedColumnName = "member_image_no")
    private Image image;


//    @OneToMany(mappedBy = "member_id")
//    private List<EnteredRoom> enteredRooms;

    public Member() {

    }

    public List<String> getRoleList() {
        if (this.role.getRole().length() > 0) {
            return Arrays.asList(this.role.getRole().split(","));
        }
        return new ArrayList<>();
    }

    public int getMemberId() {
        return memberId;
    }

    public Member(int memberId, String name, String password, LocalDate employedDate, String address, String phoneNo, String memberStatus, String email, MemberRole role, Department department, Position position, Image image) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.employedDate = employedDate;
        this.address = address;
        this.phoneNo = phoneNo;
        this.memberStatus = memberStatus;
        this.email = email;
        this.role = role;
        this.department = department;
        this.position = position;
        this.image = image;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getEmployedDate() {
        return employedDate;
    }

    public void setEmployedDate(LocalDate employedDate) {
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

    public MemberRole getRole() {
        return role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", employedDate=" + employedDate +
                ", address='" + address + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", memberStatus='" + memberStatus + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", department=" + department +
                ", position=" + position +
                ", image=" + image +
                '}';
    }
}
