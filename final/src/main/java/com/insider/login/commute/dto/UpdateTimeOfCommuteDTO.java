package com.insider.login.commute.dto;

import lombok.*;

import java.time.Duration;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateTimeOfCommuteDTO {

    private int commuteNo;              // 출퇴근 번호
    private LocalTime startWork;        // 출근 시간    // 임시
    private LocalTime endWork;          // 퇴근 시간
    private String workingStatus;       // 근무 상태
    private int totalWorkingHours;      // 총 근무 시간

    public UpdateTimeOfCommuteDTO(int commuteNo, LocalTime endWork, String workingStatus, int totalWorkingHours) {
        this.commuteNo = commuteNo;
        this.endWork = endWork;
        this.workingStatus = workingStatus;
        this.totalWorkingHours = totalWorkingHours;
    }
}
