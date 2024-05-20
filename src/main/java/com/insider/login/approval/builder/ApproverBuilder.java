package com.insider.login.approval.builder;

import com.insider.login.approval.entity.Approver;

import java.time.LocalDateTime;

public class ApproverBuilder {

    private String approverNo;          // 결재자 번호
    private String approvalNo;          // 결재 번호
    private int approverOrder;          // 결재 순번
    private String approverStatus;      //결재 처리 상태
    private LocalDateTime approverDate; // 결재처리 일시
    private int memberId;               // 결재자 사번

    public ApproverBuilder(String approverNo){
        this.approverNo = approverNo;
    }

    public ApproverBuilder(Approver approver){
        this.approverNo = approver.getApproverNo();
        this.approvalNo = approver.getApprovalNo();
        this.approverDate = approver.getApproverDate();
        this.approverOrder = approver.getApproverOrder();
        this.approverStatus = approver.getApproverStatus();
        this.memberId = approver.getMemberId();

    }

    public ApproverBuilder approverNo(String val){
        this.approverNo = val;
        return this;
    }

    public ApproverBuilder approvalNo(String val){
        this.approvalNo = val;
        return this;
    }

    public ApproverBuilder approverDate(LocalDateTime val){
        this.approverDate = val;
        return this;
    }

    public ApproverBuilder approverOrder(int val){
        this.approverOrder = val;
        return this;
    }

    public ApproverBuilder approverStatus(String val){
        this.approverStatus = val;
        return this;
    }

    public ApproverBuilder memberId(int val){
        this.memberId = val;
        return this;
    }

    public Approver builder(){
        return new Approver(approverNo, approvalNo, approverOrder, approverStatus, approverDate, memberId);
    }
}
