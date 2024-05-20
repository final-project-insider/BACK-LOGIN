package com.insider.login.leave.entity;

import jakarta.persistence.*;


@Entity
@Table(name="member_info")
public class LeaveMember {

    @Id
    @Column(name = "member_id", nullable = false)
    private int memberId;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "depart_no", nullable = false)
    private String departNo;

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getDepartNo() {
        return departNo;
    }

    @Override
    public String toString() {
        return "LeaveMember{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", departNo='" + departNo + '\'' +
                '}';
    }
}