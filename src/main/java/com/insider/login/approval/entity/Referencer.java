package com.insider.login.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="Referencer")
@Table(name="REFERENCER")
public class Referencer {

    // Referencer 엔티티

    @Id
    @Column(name = "REF_NO")
    private String refNo;               //참조자번호

    @Column(name = "APPROVAL_NO")
    private String approvalNo;          //결재번호

    @Column(name = "MEMBER_ID")
    private int memberId;               //참조자 사번

    @Column(name = "REF_ORDER")
    private int refOrder;               //참조순번

    protected Referencer() {}

    public Referencer(String refNo, String approvalNo, int memberId, int refOrder) {
        this.refNo = refNo;
        this.approvalNo = approvalNo;
        this.memberId = memberId;
        this.refOrder = refOrder;
    }

    public String getRefNo() {
        return refNo;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getRefOrder() {
        return refOrder;
    }

    @Override
    public String toString() {
        return "Referencer{" +
                "refNo='" + refNo + '\'' +
                ", approvalNo='" + approvalNo + '\'' +
                ", memberId=" + memberId +
                ", refOrder=" + refOrder +
                '}';
    }
}
