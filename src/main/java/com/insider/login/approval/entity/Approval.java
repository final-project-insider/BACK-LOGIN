package com.insider.login.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity(name="Approval")
@Table(name="APPROVAL")
public class Approval {
    //approval 엔티티

    @Id
    @Column(name= "APPROVAL_NO")
    private String approvalNo;          //결재번호

    @Column(name="MEMBER_ID")
    private int memberId;               //기안자사번

    @Column(name="APPROVAL_TITLE")
    private String approvalTitle;       //제목

    @Column(name="APPROVAL_CONTENT")
    private String approvalContent;     //기안 내용

    @Column(name="APPROVAL_DATE")
    private LocalDateTime approvalDate; //작성 일시

    @Column(name="APPROVAL_STATUS")
    private String approvalStatus;      //상태

    @Column(name="REJECT_REASON")
    private String rejectReason;        //반려사유

    @Column(name="FORM_NO")
    private String formNo;             //양식번호

    protected Approval(){}

    public Approval(String approvalNo, int memberId, String approvalTitle, String approvalContent, LocalDateTime approvalDate, String approvalStatus, String rejectReason, String formNo) {
        this.approvalNo = approvalNo;
        this.memberId = memberId;
        this.approvalTitle = approvalTitle;
        this.approvalContent = approvalContent;
        this.approvalDate = approvalDate;
        this.approvalStatus = approvalStatus;
        this.rejectReason = rejectReason;
        this.formNo = formNo;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getApprovalTitle() {
        return approvalTitle;
    }

    public String getApprovalContent() {
        return approvalContent;
    }

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public String getFormNo() {
        return formNo;
    }

    @Override
    public String toString() {
        return "Approval{" +
                "approvalNo='" + approvalNo + '\'' +
                ", memberId=" + memberId +
                ", approvalTitle='" + approvalTitle + '\'' +
                ", approvalContent='" + approvalContent + '\'' +
                ", approvalDate=" + approvalDate +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                ", formNo='" + formNo + '\'' +
                '}';
    }
}
