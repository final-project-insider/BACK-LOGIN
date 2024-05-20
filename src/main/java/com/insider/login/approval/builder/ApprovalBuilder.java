package com.insider.login.approval.builder;

import com.insider.login.approval.entity.Approval;

import java.time.LocalDateTime;

public class ApprovalBuilder {
        private String approvalNo;          //결재번호
        private int memberId;               //기안자사번
        private String approvalTitle;       //제목
        private String approvalContent;     //기안 내용
        private LocalDateTime approvalDate; //작성 일시
        private String approvalStatus;      //상태
        private String rejectReason;        //반려사유
        private String formNo;             //양식번호

    public ApprovalBuilder(String approvalNo){
        this.approvalNo = approvalNo;
    }

    public ApprovalBuilder(Approval approval){
        this.approvalNo = approval.getApprovalNo();
        this.memberId = approval.getMemberId();
        this.approvalTitle = approval.getApprovalTitle();
        this.approvalContent = approval.getApprovalContent();
        this.approvalDate = approval.getApprovalDate();
        this.approvalStatus = approval.getApprovalStatus();
        this.rejectReason = approval.getRejectReason();
        this.formNo = approval.getFormNo();
    }

    public ApprovalBuilder approvalNo(String val){
        this.approvalNo = val;
        return this;
    }

    public ApprovalBuilder memberId(int val){
        this.memberId = val;
        return this;
    }

    public ApprovalBuilder approvalTitle(String val){
        this.approvalTitle = val;
        return this;
    }

    public ApprovalBuilder approvalContent(String val){
        this.approvalContent = val;
        return this;
    }

    public ApprovalBuilder approvalDate(LocalDateTime val){
        this.approvalDate = val;
        return this;
    }

    public ApprovalBuilder approvalStatus(String val){
        this.approvalStatus = val;
        return this;
    }

    public ApprovalBuilder rejectReason(String val){
        this.rejectReason = val;
        return this;
    }

    public ApprovalBuilder formNo(String val){
        this.formNo = val;
        return this;
    }


    public Approval builder(){
        return new Approval(approvalNo, memberId, approvalTitle, approvalContent, approvalDate, approvalStatus, rejectReason, formNo);
    }
}
