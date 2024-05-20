package com.insider.login.commute.dto;

import com.insider.login.commute.dto.CommuteDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommuteMemberDTO {

    private int memberId;               // 구성원 번호 (사번)
    private String name;                // 구성원 이름
    private String password;            // 비밀번호
    private int departNo;               // 부서 번호
    private String positionLevel;        // 직급레벨
    private LocalDate employedDate;     // 입사일
    private String address;             // 주소
    private String phoneNo;             // 전화 번호
    private String memberStatus;       // 현재 상태
    private String email;               // 이메일
    private String memberRole;            // 권한
    private List<CommuteDTO> commuteList;   // 출퇴근 리스트
}
