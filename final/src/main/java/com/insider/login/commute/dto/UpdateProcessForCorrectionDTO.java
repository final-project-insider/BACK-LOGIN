package com.insider.login.commute.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateProcessForCorrectionDTO {

    private int corrNo;                         // 정정 요청 번호
    private String corrStatus;                  // 정정 상태
    private String reasonForRejection;          // 반려 사유
    private LocalDate corrProcessingDate;       // 정정 처리 일자

    public UpdateProcessForCorrectionDTO(int corrNo, String corrStatus, LocalDate corrProcessingDate) {
        this.corrNo = corrNo;
        this.corrStatus = corrStatus;
        this.corrProcessingDate = corrProcessingDate;
    }

}