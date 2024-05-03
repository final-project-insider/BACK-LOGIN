package com.insider.login.commute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "CommuteMember")
@Table(name = "member_info")
@AllArgsConstructor
@Getter
public class CommuteMember {

    @Id
    @Column(name = "member_id", nullable = false)
    private int memberId;               // 구성원 번호 (사번)

    @Column(name = "name", nullable = false)
    private String name;                // 구성원 이름

    @Column(name = "password", nullable = false)
    private String password;            // 비밀번호

    @Column(name = "depart_no", nullable = false, insertable = false, updatable = false)
    private int departNo;               // 부서 번호

    @Column(name = "position_name", nullable = false)
    private String positionName;        // 직급명

    @Column(name = "employed_date", nullable = false)
    private LocalDate employedDate;     // 입사일

    @Column(name = "address", nullable = false)
    private String address;             // 주소

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;             // 전화 번호

    @Column(name = "member_status", nullable = false)
    private String memberStatus;       // 현재 상태

    @Column(name = "email", nullable = false)
    private String email;               // 이메일

    @Column(name = "member_role", nullable = false)
    private String memberRole;          // 권한

    @OneToMany(mappedBy = "commuteMember")
    private List<Commute> commuteList;  // 출퇴근 리스트

    @ManyToOne
    @JoinColumn(name = "depart_no", nullable = false)
    private CommuteDepartment department;      // 부서

    protected CommuteMember() {}

}
