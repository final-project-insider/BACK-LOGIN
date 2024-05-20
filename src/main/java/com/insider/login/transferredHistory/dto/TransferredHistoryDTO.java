package com.insider.login.transferredHistory.dto;

import java.time.LocalDate;


public class TransferredHistoryDTO {

    private int transferredNo;                          // 식별자 번호
    private int newDepartNo;
    private String newPositionName;
    private LocalDate transferredDate;
    private int memberId;

    public TransferredHistoryDTO() {
    }

    public TransferredHistoryDTO(int transferredNo, int newDepartNo, String newPositionName, LocalDate transferredDate, int memberId) {
        this.transferredNo = transferredNo;
        this.newDepartNo = newDepartNo;
        this.newPositionName = newPositionName;
        this.transferredDate = transferredDate;
        this.memberId = memberId;
    }

    public int getTransferredNo() {
        return transferredNo;
    }

    public void setTransferredNo(int transferredNo) {
        this.transferredNo = transferredNo;
    }

    public int getNewDepartNo() {
        return newDepartNo;
    }

    public void setNewDepartNo(int newDepartNo) {
        this.newDepartNo = newDepartNo;
    }

    public String getNewPositionName() {
        return newPositionName;
    }

    public void setNewPositionName(String newPositionName) {
        this.newPositionName = newPositionName;
    }

    public LocalDate getTransferredDate() {
        return transferredDate;
    }

    public void setTransferredDate(LocalDate transferredDate) {
        this.transferredDate = transferredDate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "TransferredHistoryDTO{" +
                "transferredNo=" + transferredNo +
                ", newDepartNo=" + newDepartNo +
                ", newPositionName='" + newPositionName + '\'' +
                ", transferredDate=" + transferredDate +
                ", memberId=" + memberId +
                '}';
    }
}
