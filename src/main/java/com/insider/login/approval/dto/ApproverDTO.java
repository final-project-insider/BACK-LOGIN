package com.insider.login.approval.dto;

public class ApproverDTO {

    //ApproverDTO

    private String approverNo;              //결재자 번호
    private String approvalNo;              //결재번호
    private int approverOrder;              //결재 순번
    private String approverStatus;          //결재 처리 상태
    private String approverDate;            //결재처리일시
    private int memberId;                   //결재자 사번

    private String name;                    //결재자 이름
    private String positionName;            //결재자 직급명
    private String departName;              //결재자 부서명

    private String rejectReason;            //결재자 반려사유

    public ApproverDTO () {}

    public ApproverDTO(String approverNo, String approvalNo, int approverOrder, String approverStatus, String approverDate, int memberId) {
        this.approverNo = approverNo;
        this.approvalNo = approvalNo;
        this.approverOrder = approverOrder;
        this.approverStatus = approverStatus;
        this.approverDate = approverDate;
        this.memberId = memberId;
    }

    public ApproverDTO(String approverNo, String approvalNo, int approverOrder, String approverStatus, String approverDate, int memberId, String name, String positionName, String departName, String rejectReason) {
        this.approverNo = approverNo;
        this.approvalNo = approvalNo;
        this.approverOrder = approverOrder;
        this.approverStatus = approverStatus;
        this.approverDate = approverDate;
        this.memberId = memberId;
        this.name = name;
        this.positionName = positionName;
        this.departName = departName;
        this.rejectReason = rejectReason;
    }

    public String getApproverNo() {
        return approverNo;
    }

    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo;
    }

    public int getApproverOrder() {
        return approverOrder;
    }

    public void setApproverOrder(int approverOrder) {
        this.approverOrder = approverOrder;
    }

    public String getApproverStatus() {
        return approverStatus;
    }

    public void setApproverStatus(String approverStatus) {
        this.approverStatus = approverStatus;
    }

    public String getApproverDate() {
        return approverDate;
    }

    public void setApproverDate(String approverDate) {
        this.approverDate = approverDate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }


    @Override
    public String toString() {
        return "ApproverDTO{" +
                "approverNo='" + approverNo + '\'' +
                ", approvalNo='" + approvalNo + '\'' +
                ", approverOrder=" + approverOrder +
                ", approverStatus='" + approverStatus + '\'' +
                ", approverDate='" + approverDate + '\'' +
                ", memberId=" + memberId +
                ", name='" + name + '\'' +
                ", positionName='" + positionName + '\'' +
                ", departName='" + departName + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                '}';
    }
}
