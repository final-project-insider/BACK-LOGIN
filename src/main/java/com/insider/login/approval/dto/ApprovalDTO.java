package com.insider.login.approval.dto;

import java.util.List;

public class ApprovalDTO {

    //ApprovalDTO

    // 결재번호, 기안자 사번(*member_info), 제목, 기안 내용, 작성일시, 상태, 반려사유, 양식번호(*form),
    // 첨부파일(*List(apr_attachment)), 결재선(*List(approver)), 참조선(*List(referencer))



    private String approvalNo;                  //결재번호
    private int memberId;                       //기안자사번
    private String approvalTitle;               //제목
    private String approvalContent;             //기안 내용
    private String approvalDate;                //작성일시
    private String approvalStatus;              //상태
    private String rejectReason;                //반려사유
    private String formNo;                      //양식번호

    private String formName;                    //양식이름

    private String departName;                  //기안자 부서
    private String name;                        //기안자 이름
    private String positionName;                //기안자 직급명


    private List<AttachmentDTO> attachment;     //첨부파일
    private List<ApproverDTO> approver;         //결재선
    private List<ReferencerDTO> referencer;     //참조선

    private String finalApproverDate;                //최종승인날짜
    private String standByApprover;             //진행중(대기)인 사람

    public ApprovalDTO(){}


    public ApprovalDTO(String approvalNo, int memberId, String approvalTitle, String approvalContent, String approvalDate, String approvalStatus, String rejectReason, String formNo, String formName, String departName, String name, String positionName, List<AttachmentDTO> attachment, List<ApproverDTO> approver, List<ReferencerDTO> referencer, String approverDate, String standByApprover) {
        this.approvalNo = approvalNo;
        this.memberId = memberId;
        this.approvalTitle = approvalTitle;
        this.approvalContent = approvalContent;
        this.approvalDate = approvalDate;
        this.approvalStatus = approvalStatus;
        this.rejectReason = rejectReason;
        this.formNo = formNo;
        this.formName = formName;
        this.departName = departName;
        this.name = name;
        this.positionName = positionName;
        this.attachment = attachment;
        this.approver = approver;
        this.referencer = referencer;
        this.finalApproverDate = approverDate;
        this.standByApprover = standByApprover;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getApprovalTitle() {
        return approvalTitle;
    }

    public void setApprovalTitle(String approvalTitle) {
        this.approvalTitle = approvalTitle;
    }

    public String getApprovalContent() {
        return approvalContent;
    }

    public void setApprovalContent(String approvalContent) {
        this.approvalContent = approvalContent;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public List<AttachmentDTO> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<AttachmentDTO> attachment) {
        this.attachment = attachment;
    }

    public List<ApproverDTO> getApprover() {
        return approver;
    }

    public void setApprover(List<ApproverDTO> approver) {
        this.approver = approver;
    }

    public List<ReferencerDTO> getReferencer() {
        return referencer;
    }

    public void setReferencer(List<ReferencerDTO> referencer) {
        this.referencer = referencer;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getFinalApproverDate() {
        return finalApproverDate;
    }

    public void setFinalApproverDate(String finalApproverDate) {
        this.finalApproverDate = finalApproverDate;
    }

    public String getStandByApprover() {
        return standByApprover;
    }

    public void setStandByApprover(String standByApprover) {
        this.standByApprover = standByApprover;
    }



    @Override
    public String toString() {
        return "ApprovalDTO{" +
                "\n[approvalNo='" + approvalNo + "']" +
                "\n[memberId=" + memberId + "']" +
                "\n[approvalTitle='" + approvalTitle + "']" +
                "\n[approvalContent='" + approvalContent + "']" +
                "\n[approvalDate='" + approvalDate + "']" +
                "\n[approvalStatus='" + approvalStatus + "']" +
                "\n[rejectReason='" + rejectReason + "']" +
                "\n[formNo='" + formNo + "']" +
                "\n[formName='" + formName + "']" +
                "\n[departName='" + departName + "']" +
                "\n[name='" + name + "']" +
                "\n[positionName='" + positionName + "']" +
                "\n[attachment=" + attachment + "']" +
                "\n[approver=" + approver + "']" +
                "\n[referencer=" + referencer + "']" +
                "\n[approverDate=" + finalApproverDate + "']" +
                "\n[standByApprover=" + standByApprover + "']" +
                '\n'+'}';
    }
}
