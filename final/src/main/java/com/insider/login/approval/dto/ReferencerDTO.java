package com.insider.login.approval.dto;

public class ReferencerDTO {

    //ReferencerDTO

    private String refNo;               //참조자 번호
    private String approvalNo;          //결재 번호
    private int memberId;               //참조자 사번
    private int refOrder;               //참조 순번

    public ReferencerDTO () {}

    public ReferencerDTO(String refNo, String approvalNo, int memberId, int refOrder) {
        this.refNo = refNo;
        this.approvalNo = approvalNo;
        this.memberId = memberId;
        this.refOrder = refOrder;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
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

    public int getRefOrder() {
        return refOrder;
    }

    public void setRefOrder(int refOrder) {
        this.refOrder = refOrder;
    }

    @Override
    public String toString() {
        return "ReferencerDTO{" +
                "refNo='" + refNo + '\'' +
                ", approvalNo='" + approvalNo + '\'' +
                ", memberId=" + memberId +
                ", refOrder=" + refOrder +
                '}';
    }
}
