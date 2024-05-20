package com.insider.login.leave.dto;

public class LeaveInfoDTO {

    private int memberId;                     // 사번
    private String name;                      // 이름
    private int annualLeave;                  // 연차
    private int vacationLeave;                // 공가
    private int familyEventLeave;             // 경조사 휴가
    private int specialLeave;                 // 특별휴가
    private int totalDays;                    // 총 부여 일수
    private int consumedDays;                 // 소진 일수
    private int remainingDays;                // 잔여 일수

    public LeaveInfoDTO() {
    }

    public LeaveInfoDTO(int memberId, int annualLeave, int vacationLeave, int familyEventLeave, int specialLeave, int totalDays) {
        this.memberId = memberId;
        this.annualLeave = annualLeave;
        this.vacationLeave = vacationLeave;
        this.familyEventLeave = familyEventLeave;
        this.specialLeave = specialLeave;
        this.totalDays = totalDays;
    }

    public LeaveInfoDTO(int memberId, String name, int annualLeave, int vacationLeave, int familyEventLeave, int specialLeave, int totalDays, int consumedDays, int remainingDays) {
        this.memberId = memberId;
        this.name = name;
        this.annualLeave = annualLeave;
        this.vacationLeave = vacationLeave;
        this.familyEventLeave = familyEventLeave;
        this.specialLeave = specialLeave;
        this.totalDays = totalDays;
        this.consumedDays = consumedDays;
        this.remainingDays = remainingDays;
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

    public int getAnnualLeave() {
        return annualLeave;
    }

    public void setAnnualLeave(int annualLeave) {
        this.annualLeave = annualLeave;
    }

    public int getVacationLeave() {
        return vacationLeave;
    }

    public void setVacationLeave(int vacationLeave) {
        this.vacationLeave = vacationLeave;
    }

    public int getFamilyEventLeave() {
        return familyEventLeave;
    }

    public void setFamilyEventLeave(int familyEventLeave) {
        this.familyEventLeave = familyEventLeave;
    }

    public int getSpecialLeave() {
        return specialLeave;
    }

    public void setSpecialLeave(int specialLeave) {
        this.specialLeave = specialLeave;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getConsumedDays() {
        return consumedDays;
    }

    public void setConsumedDays(int consumedDays) {
        this.consumedDays = consumedDays;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    @Override
    public String toString() {
        return "LeaveInfoDTO{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", annualLeave=" + annualLeave +
                ", vacationLeave=" + vacationLeave +
                ", familyEventLeave=" + familyEventLeave +
                ", specialLeave=" + specialLeave +
                ", totalDays=" + totalDays +
                ", consumedDays=" + consumedDays +
                ", remainingDays=" + remainingDays +
                '}';
    }
}
