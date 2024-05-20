package com.insider.login.member.dto;

import com.insider.login.common.utils.MemberRole;
import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.position.dto.PositionDTO;

import java.time.LocalDate;

public class ShowMemberDTO {

    private int memberId;
    private String name;
    private String password;
    private LocalDate employedDate;
    private String address;
    private String phoneNo;
    private String memberStatus;
    private String email;
    private MemberRole role;
    private DepartmentDTO departmentDTO;
    private PositionDTO positionDTO;
    private String imageUrl;
    private String periodOfWork;

    public ShowMemberDTO() {
    }

    public ShowMemberDTO(int memberId, String name, String password, LocalDate employedDate, String address, String phoneNo, String memberStatus, String email, MemberRole role, DepartmentDTO departmentDTO, PositionDTO positionDTO, String imageUrl, String periodOfWork) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.employedDate = employedDate;
        this.address = address;
        this.phoneNo = phoneNo;
        this.memberStatus = memberStatus;
        this.email = email;
        this.role = role;
        this.departmentDTO = departmentDTO;
        this.positionDTO = positionDTO;
        this.imageUrl = imageUrl;
        this.periodOfWork = periodOfWork;
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

    public DepartmentDTO getDepartmentDTO() {
        return departmentDTO;
    }

    public void setDepartmentDTO(DepartmentDTO departmentDTO) {
        this.departmentDTO = departmentDTO;
    }

    public PositionDTO getPositionDTO() {
        return positionDTO;
    }

    public void setPositionDTO(PositionDTO positionDTO) {
        this.positionDTO = positionDTO;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPeriodOfWork() {
        return periodOfWork;
    }

    public void setPeriodOfWork(String periodOfWork) {
        this.periodOfWork = periodOfWork;
    }

    @Override
    public String toString() {
        return "showMemberDTO{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", employedDate=" + employedDate +
                ", address='" + address + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", memberStatus='" + memberStatus + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", departmentDTO=" + departmentDTO +
                ", positionDTO=" + positionDTO +
                ", imageUrl='" + imageUrl + '\'' +
                ", periodOfWork='" + periodOfWork + '\'' +
                '}';
    }
}
