package com.insider.login.commute.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommuteDepartmentDTO {

    private int departNo;               // 부서 번호
    private String departName;          // 부서명
    private List<CommuteMemberDTO> memberList; // 구성원 리스트

}
