package com.insider.login.leave.dto;

public class LeaveAccrualDTO {

    private int leaveAccrualNo;                 // 발생 번호

    private int recipientId;                    // 대상자 사번

    private String recipientName;               // 대상자 사원명

    private String recipientDepart;             // 대상자 부서

    private int leaveAccrualDays;               // 발생 일수

    private String leaveAccrualReason;          // 발생 사유

    private int grantorId;                      // 처리자 사번

    private String grantorName;                 // 처리자 사원명

    public LeaveAccrualDTO() {
    }

    public LeaveAccrualDTO(int recipientId, int leaveAccrualDays, String leaveAccrualReason) {
        this.recipientId = recipientId;
        this.leaveAccrualDays = leaveAccrualDays;
        this.leaveAccrualReason = leaveAccrualReason;
    }

    public LeaveAccrualDTO(int leaveAccrualNo, int recipientId, String recipientName, String recipientDepart, int leaveAccrualDays, String leaveAccrualReason, int grantorId, String grantorName) {
        this.leaveAccrualNo = leaveAccrualNo;
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.recipientDepart = recipientDepart;
        this.leaveAccrualDays = leaveAccrualDays;
        this.leaveAccrualReason = leaveAccrualReason;
        this.grantorId = grantorId;
        this.grantorName = grantorName;
    }

    public int getLeaveAccrualNo() {
        return leaveAccrualNo;
    }

    public void setLeaveAccrualNo(int leaveAccrualNo) {
        this.leaveAccrualNo = leaveAccrualNo;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientDepart() {
        return recipientDepart;
    }

    public void setRecipientDepart(String recipientDepart) {
        this.recipientDepart = recipientDepart;
    }

    public int getLeaveAccrualDays() {
        return leaveAccrualDays;
    }

    public void setLeaveAccrualDays(int leaveAccrualDays) {
        this.leaveAccrualDays = leaveAccrualDays;
    }

    public String getLeaveAccrualReason() {
        return leaveAccrualReason;
    }

    public void setLeaveAccrualReason(String leaveAccrualReason) {
        this.leaveAccrualReason = leaveAccrualReason;
    }

    public int getGrantorId() {
        return grantorId;
    }

    public void setGrantorId(int grantorId) {
        this.grantorId = grantorId;
    }

    public String getGrantorName() {
        return grantorName;
    }

    public void setGrantorName(String grantorName) {
        this.grantorName = grantorName;
    }

    @Override
    public String toString() {
        return "LeaveAccrualDTO{" +
                "leaveAccrualNo=" + leaveAccrualNo +
                ", recipientId=" + recipientId +
                ", recipientName='" + recipientName + '\'' +
                ", recipientDepart='" + recipientDepart + '\'' +
                ", leaveAccrualDays=" + leaveAccrualDays +
                ", leaveAccrualReason='" + leaveAccrualReason + '\'' +
                ", grantorId=" + grantorId +
                ", grantorName='" + grantorName + '\'' +
                '}';
    }
}
