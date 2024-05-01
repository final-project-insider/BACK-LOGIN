package com.insider.login.commute.dto;

import com.insider.login.member.dto.MemberDTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommuteDTO {

    private int commuteNo;                  // 출퇴근 번호
    private int memberId;                   // 사번
    private LocalDate workingDate;          // 근무 일자
    private LocalTime startWork;            // 출근 시간
    private LocalTime endWork;              // 퇴근 시간
    private String workingStatus;           // 근무 상태
    private int totalWorkingHours;          // 총 근무 시간
    private MemberDTO member;               // 구성원
    private CorrectionDTO correction;       // 출퇴근 정정

    public CommuteDTO(int memberId, LocalDate workingDate, LocalTime startWork, LocalTime endWork, String workingStatus, int totalWorkingHours) {
        this.memberId = memberId;
        this.workingDate = workingDate;
        this.startWork = startWork;
        this.endWork = endWork;
        this.workingStatus = workingStatus;
        this.totalWorkingHours = totalWorkingHours;
    }

    public CommuteDTO(int commuteNo, LocalTime endWork, String workingStatus, int totalWorkingHours) {
        this.commuteNo = commuteNo;
        this.endWork = endWork;
        this.workingStatus = workingStatus;
        this.totalWorkingHours = totalWorkingHours;
    }

    public CommuteDTO(int memberId, LocalDate workingDate, LocalTime startWork, LocalTime endWork, String workingStatus, int totalWorkingHours, MemberDTO member) {
        this.memberId = memberId;
        this.workingDate = workingDate;
        this.startWork = startWork;
        this.endWork = endWork;
        this.workingStatus = workingStatus;
        this.totalWorkingHours = totalWorkingHours;
        this.member = member;
    }
}
