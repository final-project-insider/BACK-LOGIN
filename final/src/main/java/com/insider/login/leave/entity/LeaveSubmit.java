package com.insider.login.leave.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "LEAVE_SUBMIT")
public class LeaveSubmit {

    @Id
    @Column(name = "LEAVE_SUB_NO", nullable = false, columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveSubNo;                     // 신청 번호

    @Column(name = "REF_LEAVE_SUB_NO", nullable = true, columnDefinition = "INT")
    private int refLeaveSubNo;                  // 상위 신청 번호 (취소 요청 시 사용)

    @Column(name = "MEMBER_ID", nullable = false, columnDefinition = "VARCHAR(20)")
    private int leaveSubApplicant;           // 신청자 사번 (사번으로 사원명 조회에서 DTO에 담기)

    @Column(name = "LEAVE_SUB_APPROVER", nullable = true, columnDefinition = "VARCHAR(30)")
    private int leaveSubApprover;            // 승인자 사번 (사번으로 사원명 조회해서 DTO에 담기)

    @Column(name = "LEAVE_SUB_START_DATE", nullable = false, columnDefinition = "VARCHAR(10)") // YYYY-MM-DD
    private Date leaveSubStartDate;             // 휴가 시작일

    @Column(name = "LEAVE_SUB_END_DATE", nullable = false, columnDefinition = "VARCHAR(10)")  // YYYY-MM-DD
    private Date leaveSubEndDate;               // 휴가 종료일

    @Column(name = "LEAVE_SUB_APPLY_DATE", nullable = false, columnDefinition = "VARCHAR(10)") // YYYY-MM-DD
    private String leaveSubApplyDate;             // 신청 일자

    @Column(name = "LEAVE_SUB_TYPE", nullable = false, columnDefinition = "VARCHAR(12)") // 연차, 오전반차, 오후반차, 특별휴가
    private String leaveSubType;                // 휴가 유형

    @Column(name = "LEAVE_SUB_STATUS", nullable = false, columnDefinition = "VARCHAR(6)") // 승인, 반려, 대기
    private String leaveSubStatus;              // 처리 상태

    @Column(name = "LEAVE_SUB_PROCESS_DATE", nullable = true , columnDefinition = "VARCHAR(10)") // YYYY-MM-DD
    private String leaveSubProcessDate;           // 처리 일자

    @Column(name = "LEAVE_SUB_REASON", nullable = true, columnDefinition = "VARCHAR(300)")
    private String leaveSubReason;              // 신청 사유
    protected LeaveSubmit() {
    }

    public LeaveSubmit(int leaveSubApplicant, Date leaveSubStartDate, Date leaveSubEndDate, String leaveSubApplyDate, String leaveSubType, String leaveSubReason) {
        this.leaveSubApplicant = leaveSubApplicant;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubApplyDate = leaveSubApplyDate;
        this.leaveSubType = leaveSubType;
        this.leaveSubReason = leaveSubReason;
    }

    public LeaveSubmit(int refLeaveSubNo, int leaveSubApplicant, Date leaveSubStartDate, Date leaveSubEndDate, String leaveSubApplyDate, String leaveSubType, String leaveSubStatus) {
        this.refLeaveSubNo = refLeaveSubNo;
        this.leaveSubApplicant = leaveSubApplicant;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubApplyDate = leaveSubApplyDate;
        this.leaveSubType = leaveSubType;
        this.leaveSubStatus = leaveSubStatus;
    }

    public LeaveSubmit(int leaveSubNo, int refLeaveSubNo, int leaveSubApplicant, int leaveSubApprover, Date leaveSubStartDate, Date leaveSubEndDate, String leaveSubApplyDate, String leaveSubType, String leaveSubStatus, String leaveSubProcessDate, String leaveSubReason) {
        this.leaveSubNo = leaveSubNo;
        this.refLeaveSubNo = refLeaveSubNo;
        this.leaveSubApplicant = leaveSubApplicant;
        this.leaveSubApprover = leaveSubApprover;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubApplyDate = leaveSubApplyDate;
        this.leaveSubType = leaveSubType;
        this.leaveSubStatus = leaveSubStatus;
        this.leaveSubProcessDate = leaveSubProcessDate;
        this.leaveSubReason = leaveSubReason;
    }

    public int getLeaveSubNo() {
        return leaveSubNo;
    }

    public int getRefLeaveSubNo() {
        return refLeaveSubNo;
    }

    public int getLeaveSubApplicant() {
        return leaveSubApplicant;
    }

    public int getLeaveSubApprover() {
        return leaveSubApprover;
    }

    public Date getLeaveSubStartDate() {
        return leaveSubStartDate;
    }

    public Date getLeaveSubEndDate() {
        return leaveSubEndDate;
    }

    public String getLeaveSubApplyDate() {
        return leaveSubApplyDate;
    }

    public String getLeaveSubType() {
        return leaveSubType;
    }

    public String getLeaveSubStatus() {
        return leaveSubStatus;
    }

    public String getLeaveSubProcessDate() {
        return leaveSubProcessDate;
    }

    public String getLeaveSubReason() {
        return leaveSubReason;
    }

    @Override
    public String toString() {
        return "LeaveSubmit{" +
                "leaveSubNo=" + leaveSubNo +
                ", refLeaveSubNo=" + refLeaveSubNo +
                ", leaveSubApplicant='" + leaveSubApplicant + '\'' +
                ", leaveSubApprover='" + leaveSubApprover + '\'' +
                ", leaveSubStartDate=" + leaveSubStartDate +
                ", leaveSubEndDate=" + leaveSubEndDate +
                ", leaveSubApplyDate=" + leaveSubApplyDate +
                ", leaveSubType='" + leaveSubType + '\'' +
                ", leaveSubStatus='" + leaveSubStatus + '\'' +
                ", leaveSubProcessDate=" + leaveSubProcessDate +
                ", leaveSubReason='" + leaveSubReason + '\'' +
                '}';
    }
}
