package com.insider.login.member.entity;

import com.insider.login.common.utils.MemberRole;
import com.insider.login.department.entity.Department;
import com.insider.login.position.entity.Position;
import com.insider.login.webSocket.Cahtting.entity.EnteredRoom;
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
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "depart_no")
    private Department department;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position_level")
    private Position position;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    @OneToMany(mappedBy = "member")
    private List<EnteredRoom> enteredRoom = new ArrayList<>();


    protected Member() {}

    public Member(int memberId, String name, String password, LocalDate employedDate, String address, String phoneNo, String memberStatus, String email, MemberRole role, Department department, Position position, String imageUrl) {
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
        this.imageUrl = imageUrl;
    }

    public int getMemberId() {
        return memberId;
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

    public LocalDate getEmployedDate() {
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

    public MemberRole getRole() {
        return role;
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

//    public List<Commute> getCommutes() {
//        return commutes;
//    }
//
//    public void setCommutes(List<Commute> commutes) {
//        this.commutes = commutes;
//    }


    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getRoleList() {
        if (!this.role.getRole().isEmpty()) {
            return Arrays.asList(this.role.getRole().split(","));
        }
        return new ArrayList<>();
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", imageUrl='" + imageUrl + '\'' +
//                ", commutes=" + commutes +
                '}';
    }
}