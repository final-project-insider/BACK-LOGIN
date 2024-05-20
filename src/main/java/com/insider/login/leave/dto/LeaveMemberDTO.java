package com.insider.login.leave.dto;

public class LeaveMemberDTO {
    private int memberId;

    private String name;

    private int departNo;

    private String department;

    public LeaveMemberDTO() {
    }

    public LeaveMemberDTO(int memberId, String name, int departNo, String department) {
        this.memberId = memberId;
        this.name = name;
        this.departNo = departNo;
        this.department = department;
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

    public int getDepartNo() {
        return departNo;
    }

    public void setDepartNo(int departNo) {
        this.departNo = departNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "LeaveMemberDTO{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", departNo=" + departNo +
                ", department='" + department + '\'' +
                '}';
    }
}
