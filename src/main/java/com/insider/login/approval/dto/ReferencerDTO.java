package com.insider.login.approval.dto;

public class ReferencerDTO {

    //ReferencerDTO

    private String refNo;               //참조자 번호
    private String approvalNo;          //결재 번호
    private int memberId;               //참조자 사번
    private int refOrder;               //참조 순번

    private String name;                //참조자 이름
    private String positionName;        //참조자 직급
    private String departName;          //참조자 부서명

    public ReferencerDTO () {}

    public ReferencerDTO(String refNo, String approvalNo, int memberId, int refOrder) {
        this.refNo = refNo;
        this.approvalNo = approvalNo;
        this.memberId = memberId;
        this.refOrder = refOrder;
    }

    public ReferencerDTO(String refNo, String approvalNo, int memberId, int refOrder, String name, String positionName, String departName) {
        this.refNo = refNo;
        this.approvalNo = approvalNo;
        this.memberId = memberId;
        this.refOrder = refOrder;
        this.name = name;
        this.positionName = positionName;
        this.departName = departName;
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

    @Override
    public String toString() {
        return "ReferencerDTO{" +
                "refNo='" + refNo + '\'' +
                ", approvalNo='" + approvalNo + '\'' +
                ", memberId=" + memberId +
                ", refOrder=" + refOrder +
                ", name='" + name + '\'' +
                ", positionName='" + positionName + '\'' +
                ", departName='" + departName + '\'' +
                '}';
    }
}
