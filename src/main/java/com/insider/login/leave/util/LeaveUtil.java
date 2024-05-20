package com.insider.login.leave.util;

import com.insider.login.leave.dto.LeaveInfoDTO;
import com.insider.login.leave.entity.LeaveSubmit;
import com.insider.login.leave.entity.Leaves;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaveUtil {

    public int leaveDaysCalc(LeaveSubmit leaveSubmit) {
        LocalDate startDate = leaveSubmit.getLeaveSubStartDate();
        LocalDate endDate = leaveSubmit.getLeaveSubEndDate();

        // 두 날짜 사이의 일수 계산
        return (int) (ChronoUnit.DAYS.between(startDate, endDate ) + 1);
    }

    public LeaveInfoDTO leaveInfoCalc(List<Leaves> memberLeaves) {

        int memberId = memberLeaves.get(0).getMemberId();
        int annualLeave = 0;        // 연차
        int vacationLeave = 0;      // 공가
        int familyEventLeave = 0;   // 경조사 휴가
        int specialLeave = 0;       // 특별휴가


        // 해당 사번을 키로 가진 휴가 리스트를 for문을 통해 유형별 총 일수를 구함
        for (Leaves leaves : memberLeaves) {
            switch (leaves.getLeaveType()) {
                case "연차":
                    annualLeave += leaves.getLeaveDays();
                    break;
                case "공가":
                    vacationLeave += leaves.getLeaveDays();
                    break;
                case "경조사":
                    familyEventLeave += leaves.getLeaveDays();
                    break;
                default:
                    specialLeave += leaves.getLeaveDays();
                    break;
            }
        }

        // 총 부여 일수
        int totalDays = annualLeave + vacationLeave + familyEventLeave + specialLeave;


        return new LeaveInfoDTO(memberId, annualLeave, vacationLeave, familyEventLeave, specialLeave, totalDays);
    }

}
