package com.insider.login.leave.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "LEAVE_ACCRUAL")
public class LeaveAccrual {

    @Id
    @Column(name = "LEAVE_ACCRUAL_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveAccrualNo;                 // 발생 번호

    @Column(name = "RECIPIENT_ID", nullable = false)
    private int recipientId;                 // 대상자 사번 ( 사번으로 DTO에 사원명, 부서 담기)

    @Column(name = "LEAVE_ACCRUAL_GRANTOR", nullable = false)
    private int grantorId;         // 처리자 사번 (사번으로 DTO에 사원명 담기)

    @Column(name = "LEAVE_ACCRUAL_DAYS", nullable = false)
    private int leaveAccrualDays;               // 발생 일수

    @Column(name = "LEAVE_ACCRUAL_REASON", nullable = false, columnDefinition = "VARCHAR(300)")
    private String leaveAccrualReason;          // 발생 사유


    protected LeaveAccrual() {}

    public LeaveAccrual(int leaveAccrualNo, int recipientId, int grantorId, int leaveAccrualDays, String leaveAccrualReason) {
        this.leaveAccrualNo = leaveAccrualNo;
        this.recipientId = recipientId;
        this.grantorId = grantorId;
        this.leaveAccrualDays = leaveAccrualDays;
        this.leaveAccrualReason = leaveAccrualReason;
    }

    public int getLeaveAccrualNo() {
        return leaveAccrualNo;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public int getGrantorId() {
        return grantorId;
    }

    public int getLeaveAccrualDays() {
        return leaveAccrualDays;
    }

    public String getLeaveAccrualReason() {
        return leaveAccrualReason;
    }

    @Override
    public String toString() {
        return "LeaveAccrual{" +
                "leaveAccrualNo=" + leaveAccrualNo +
                ", recipientId=" + recipientId +
                ", grantorId=" + grantorId +
                ", leaveAccrualDays=" + leaveAccrualDays +
                ", leaveAccrualReason='" + leaveAccrualReason + '\'' +
                '}';
    }
}
