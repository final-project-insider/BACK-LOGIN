package com.insider.login.leave.util;


import com.insider.login.leave.entity.LeaveSubmit;

import java.time.LocalDate;

public class LeaveSubmitBuilder {
    private int leaveSubNo;
    private int refLeaveSubNo;
    private int leaveSubApplicant;
    private int leaveSubApprover;
    private LocalDate leaveSubStartDate;
    private LocalDate  leaveSubEndDate;
    private String leaveSubApplyDate;
    private String leaveSubType;
    private String leaveSubStatus;
    private String leaveSubProcessDate;
    private String leaveSubReason;

    public LeaveSubmitBuilder setLeaveSubNo(int leaveSubNo) {
        this.leaveSubNo = leaveSubNo;
        return this;
    }

    public LeaveSubmitBuilder setRefLeaveSubNo(int refLeaveSubNo) {
        this.refLeaveSubNo = refLeaveSubNo;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubApplicant(int leaveSubApplicant) {
        this.leaveSubApplicant = leaveSubApplicant;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubApprover(int leaveSubApprover) {
        this.leaveSubApprover = leaveSubApprover;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubStartDate(LocalDate  leaveSubStartDate) {
        this.leaveSubStartDate = leaveSubStartDate;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubEndDate(LocalDate  leaveSubEndDate) {
        this.leaveSubEndDate = leaveSubEndDate;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubApplyDate(String leaveSubApplyDate) {
        this.leaveSubApplyDate = leaveSubApplyDate;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubType(String leaveSubType) {
        this.leaveSubType = leaveSubType;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubStatus(String leaveSubStatus) {
        this.leaveSubStatus = leaveSubStatus;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubProcessDate(String leaveSubProcessDate) {
        this.leaveSubProcessDate = leaveSubProcessDate;
        return this;
    }
    public LeaveSubmitBuilder setLeaveSubReason(String leaveSubReason) {
        this.leaveSubReason = leaveSubReason;
        return this;
    }

    public LeaveSubmit build() {
        return new LeaveSubmit(leaveSubNo, refLeaveSubNo, leaveSubApplicant, leaveSubApprover, leaveSubStartDate, leaveSubEndDate, leaveSubApplyDate, leaveSubType, leaveSubStatus, leaveSubProcessDate, leaveSubReason);
    }
}
