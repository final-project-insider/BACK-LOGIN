package com.insider.login.leave.dto;

import java.time.LocalDate;

public class LeaveSubmitDTO {

    private int leaveSubNo;                     // 신청 번호

    private int refLeaveSubNo;                  // 상위 신청 번호 (취소 요청 시 사용)

    private int leaveSubApplicant;              // 신청자 사번

    private String applicantName;               // 신청자 사원명

    private String applicantDept;               // 신청자 부서

    private int leaveSubApprover;               // 승인자 사번

    private String approverName;                // 승인자 사원명

    private LocalDate leaveSubStartDate;             // 휴가 시작일

    private LocalDate  leaveSubEndDate;               // 휴가 종료일

    private String leaveSubApplyDate;           // 신청 일자

    private String leaveSubType;                // 휴가 유형

    private String leaveSubStatus;              // 처리 상태

    private String leaveSubProcessDate;         // 처리 일자

    private String leaveSubReason;              // 신청 사유

    public LeaveSubmitDTO() {
    }

    //휴가 취소 요청 등록
    public LeaveSubmitDTO(int refLeaveSubNo, int leaveSubApplicant, LocalDate  leaveSubStartDate, LocalDate  leaveSubEndDate, String leaveSubApplyDate, String leaveSubType, String leaveSubStatus, String leaveSubReason) {
        this.refLeaveSubNo = refLeaveSubNo;
        this.leaveSubApplicant = leaveSubApplicant;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubApplyDate = leaveSubApplyDate;
        this.leaveSubType = leaveSubType;
        this.leaveSubStatus = leaveSubStatus;
        this.leaveSubReason = leaveSubReason;
    }

    // 휴가 취소 요청
    public LeaveSubmitDTO(int leaveSubNo, int leaveSubApplicant, LocalDate  leaveSubStartDate, LocalDate  leaveSubEndDate, String leaveSubReason) {
        this.leaveSubNo = leaveSubNo;
        this.leaveSubApplicant = leaveSubApplicant;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubReason = leaveSubReason;
    }

    // 휴가 신청
    public LeaveSubmitDTO(int leaveSubApplicant, LocalDate  leaveSubStartDate, LocalDate  leaveSubEndDate, String leaveSubType, String leaveSubReason) {
        this.leaveSubApplicant = leaveSubApplicant;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubType = leaveSubType;
        this.leaveSubReason = leaveSubReason;
    }

    public LeaveSubmitDTO(int leaveSubApplicant, LocalDate  leaveSubStartDate, LocalDate  leaveSubEndDate, String leaveSubApplyDate, String leaveSubType, String leaveSubReason) {
        this.leaveSubApplicant = leaveSubApplicant;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubApplyDate = leaveSubApplyDate;
        this.leaveSubType = leaveSubType;
        this.leaveSubReason = leaveSubReason;
    }


    public LeaveSubmitDTO(int leaveSubNo, int refLeaveSubNo, int leaveSubApplicant, String applicantName, String applicantDept, int leaveSubApprover, String approverName, LocalDate  leaveSubStartDate, LocalDate  leaveSubEndDate, String leaveSubApplyDate, String leaveSubType, String leaveSubStatus, String leaveSubProcessDate, String leaveSubReason) {
        this.leaveSubNo = leaveSubNo;
        this.refLeaveSubNo = refLeaveSubNo;
        this.leaveSubApplicant = leaveSubApplicant;
        this.applicantName = applicantName;
        this.applicantDept = applicantDept;
        this.leaveSubApprover = leaveSubApprover;
        this.approverName = approverName;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubApplyDate = leaveSubApplyDate;
        this.leaveSubType = leaveSubType;
        this.leaveSubStatus = leaveSubStatus;
        this.leaveSubProcessDate = leaveSubProcessDate;
        this.leaveSubReason = leaveSubReason;
    }

    public LeaveSubmitDTO(int leaveSubNo, int leaveSubApprover, String leaveSubStatus, String leaveSubProcessDate) {
        this.leaveSubNo = leaveSubNo;
        this.leaveSubApprover = leaveSubApprover;
        this.leaveSubStatus = leaveSubStatus;
        this.leaveSubProcessDate = leaveSubProcessDate;
    }



    public LeaveSubmitDTO(int leaveSubNo, int leaveSubApprover, String leaveSubStatus, String leaveSubProcessDate, String leaveSubReason) {
        this.leaveSubNo = leaveSubNo;
        this.leaveSubApprover = leaveSubApprover;
        this.leaveSubStatus = leaveSubStatus;
        this.leaveSubProcessDate = leaveSubProcessDate;
        this.leaveSubReason = leaveSubReason;


    }

    public int getLeaveSubNo() {
        return leaveSubNo;
    }

    public void setLeaveSubNo(int leaveSubNo) {
        this.leaveSubNo = leaveSubNo;
    }

    public int getRefLeaveSubNo() {
        return refLeaveSubNo;
    }

    public void setRefLeaveSubNo(int refLeaveSubNo) {
        this.refLeaveSubNo = refLeaveSubNo;
    }

    public int getLeaveSubApplicant() {
        return leaveSubApplicant;
    }

    public void setLeaveSubApplicant(int leaveSubApplicant) {
        this.leaveSubApplicant = leaveSubApplicant;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantDept() {
        return applicantDept;
    }

    public void setApplicantDept(String applicantDept) {
        this.applicantDept = applicantDept;
    }

    public int getLeaveSubApprover() {
        return leaveSubApprover;
    }

    public void setLeaveSubApprover(int leaveSubApprover) {
        this.leaveSubApprover = leaveSubApprover;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public LocalDate  getLeaveSubStartDate() {
        return leaveSubStartDate;
    }

    public void setLeaveSubStartDate(LocalDate  leaveSubStartDate) {
        this.leaveSubStartDate = leaveSubStartDate;
    }

    public LocalDate  getLeaveSubEndDate() {
        return leaveSubEndDate;
    }

    public void setLeaveSubEndDate(LocalDate  leaveSubEndDate) {
        this.leaveSubEndDate = leaveSubEndDate;
    }

    public String getLeaveSubApplyDate() {
        return leaveSubApplyDate;
    }

    public void setLeaveSubApplyDate(String leaveSubApplyDate) {
        this.leaveSubApplyDate = leaveSubApplyDate;
    }

    public String getLeaveSubType() {
        return leaveSubType;
    }

    public void setLeaveSubType(String leaveSubType) {
        this.leaveSubType = leaveSubType;
    }

    public String getLeaveSubStatus() {
        return leaveSubStatus;
    }

    public void setLeaveSubStatus(String leaveSubStatus) {
        this.leaveSubStatus = leaveSubStatus;
    }

    public String getLeaveSubProcessDate() {
        return leaveSubProcessDate;
    }

    public void setLeaveSubProcessDate(String leaveSubProcessDate) {
        this.leaveSubProcessDate = leaveSubProcessDate;
    }

    public String getLeaveSubReason() {
        return leaveSubReason;
    }

    public void setLeaveSubReason(String leaveSubReason) {
        this.leaveSubReason = leaveSubReason;
    }

    @Override
    public String toString() {
        return "LeaveSubmitDTO{" +
                "leaveSubNo=" + leaveSubNo +
                ", refLeaveSubNo=" + refLeaveSubNo +
                ", leaveSubApplicant=" + leaveSubApplicant +
                ", applicantName='" + applicantName + '\'' +
                ", applicantDept='" + applicantDept + '\'' +
                ", leaveSubApprover=" + leaveSubApprover +
                ", approverName='" + approverName + '\'' +
                ", leaveSubStartDate=" + leaveSubStartDate +
                ", leaveSubEndDate=" + leaveSubEndDate +
                ", leaveSubApplyDate='" + leaveSubApplyDate + '\'' +
                ", leaveSubType='" + leaveSubType + '\'' +
                ", leaveSubStatus='" + leaveSubStatus + '\'' +
                ", leaveSubProcessDate='" + leaveSubProcessDate + '\'' +
                ", leaveSubReason='" + leaveSubReason + '\'' +
                '}';
    }
}
