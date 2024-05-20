package com.insider.login.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity(name = "Approver")
@Table(name="APPROVER")
public class Approver {

    //Approver엔티티

    @Id
    @Column(name="APPROVER_NO")
    private String approverNo;          // 결재자 번호

    @Column(name="APPROVAL_NO")
    private String approvalNo;          // 결재 번호

    @Column(name="APPROVER_ORDER")
    private int approverOrder;          // 결재 순번

    @Column(name="APPROVER_STATUS")
    private String approverStatus;      //결재 처리 상태

    @Column(name="APPROVER_DATE")
    private LocalDateTime approverDate; // 결재처리 일시

    @Column(name="MEMBER_ID")
    private int memberId;               // 결재자 사번

    protected Approver (){}

    public Approver(String approverNo, String approvalNo, int approverOrder, String approverStatus, LocalDateTime approverDate, int memberId) {
        this.approverNo = approverNo;
        this.approvalNo = approvalNo;
        this.approverOrder = approverOrder;
        this.approverStatus = approverStatus;
        this.approverDate = approverDate;
        this.memberId = memberId;
    }

    public String getApproverNo() {
        return approverNo;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public int getApproverOrder() {
        return approverOrder;
    }

    public String getApproverStatus() {
        return approverStatus;
    }

    public LocalDateTime getApproverDate() {
        return approverDate;
    }

    public int getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "Approver{" +
                "approverNo='" + approverNo + '\'' +
                ", approvalNo='" + approvalNo + '\'' +
                ", approverOrder=" + approverOrder +
                ", approverStatus='" + approverStatus + '\'' +
                ", approverDate=" + approverDate +
                ", memberId=" + memberId +
                '}';
    }
}
