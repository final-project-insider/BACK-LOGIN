package com.insider.login.leave.dto;

public class LeavesDTO {

    private int leaveNo;            // 휴가 번호

    private String memberId;        // 사번

    private int leaveDays;          // 휴가 일수

    private String leaveType;       // 휴가 유형

    public LeavesDTO() {
    }

    public LeavesDTO(int leaveNo, String memberId, int leaveDays, String leaveType) {
        this.leaveNo = leaveNo;
        this.memberId = memberId;
        this.leaveDays = leaveDays;
        this.leaveType = leaveType;
    }

    public int getLeaveNo() {
        return leaveNo;
    }

    public void setLeaveNo(int leaveNo) {
        this.leaveNo = leaveNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(int leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    @Override
    public String toString() {
        return "LeaveDTO{" +
                "leaveNo=" + leaveNo +
                ", memberId='" + memberId + '\'' +
                ", leaveDays=" + leaveDays +
                ", leaveType='" + leaveType + '\'' +
                '}';
    }
}
