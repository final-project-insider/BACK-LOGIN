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
    private LocalTime endWork;          // 퇴근 시간
    private String workingStatus;       // 근무 상태
    private int totalWorkingHours;      // 총 근무 시간

}
