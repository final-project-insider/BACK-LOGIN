package com.insider.login.member.dto;

import com.insider.login.common.utils.MemberRole;
import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.department.entity.Department;
import com.insider.login.position.dto.PositionDTO;
import com.insider.login.position.entity.Position;
import com.insider.login.transferredHistory.dto.TransferredHistoryDTO;
import com.insider.login.transferredHistory.entity.TransferredHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemberDTO {

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

    public MemberDTO() {
    }

    public MemberDTO(int memberId) {
        this.memberId = memberId;
    }

    public MemberDTO(int memberId, String name, String password, LocalDate employedDate, String address, String phoneNo, String memberStatus, String email, MemberRole role, DepartmentDTO departmentDTO, PositionDTO positionDTO, String imageUrl) {
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
    }

//    public MemberDTO(Member member) {
//        this.memberId = member.getMemberId();
//        this.name = member.getName();
//        this.password = member.getPassword();
//        this.employedDate = member.getEmployedDate();
//        this.address = member.getAddress();
//        this.phoneNo = member.getPhoneNo();
//        this.memberStatus = member.getMemberStatus();
//        this.email = member.getEmail();
//        this.role = member.getRole();
//        this.imageUrl = member.getImageUrl();
////        this.commutes = member.getCommutes();
//        // Map Department and Position
//        setDepartment(member.getDepartment());
//        setPosition(member.getPosition());
//    }

    public static List<TransferredHistoryDTO> mapTransferredHistoryList(List<TransferredHistory> transferredHistoryList) {
        List<TransferredHistoryDTO> transferredHistoryDTOList = new ArrayList<>();
        for (TransferredHistory transferredHistory : transferredHistoryList) {
            TransferredHistoryDTO transferredHistoryDTO = new TransferredHistoryDTO();
            transferredHistoryDTO.setTransferredDate(transferredHistory.getTransferredDate());
            transferredHistoryDTO.setNewPositionName(transferredHistory.getNewPositionName());
            transferredHistoryDTO.setNewDepartNo(transferredHistory.getNewDepartNo());

            transferredHistoryDTOList.add(transferredHistoryDTO);
        }
        return transferredHistoryDTOList;
    }

    public MemberDTO(MemberDTO memberDTO) {
    }

    public List<String> getRoleList() {
        if (this.role != null && !this.role.getRole().isEmpty()) {
            return Arrays.asList(this.role.getRole().split(","));
        }
        return new ArrayList<>();
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

    public void setDepartment(Department department) {
        if (department != null) {
            this.departmentDTO = new DepartmentDTO();
            this.departmentDTO.setDepartNo(department.getDepartNo());
            this.departmentDTO.setDepartName(department.getDepartName());
        }
    }

    public void setPositionDTO(PositionDTO positionDTO) {
        this.positionDTO = positionDTO;
    }

    public void setPosition(Position position) {
        if (position != null) {
            this.positionDTO = new PositionDTO();
            this.positionDTO.setPositionName(position.getPositionName());
            this.positionDTO.setPositionLevel(position.getPositionLevel());
        }
    }

    public PositionDTO getPositionDTO() {
        return positionDTO;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
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
                '}';
    }
}


