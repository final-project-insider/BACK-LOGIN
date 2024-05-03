package com.insider.login.other.notice.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoticeDTO {

    private int noticeNo;                       // 알림 번호
    private int memberId;                       // 사번
    private LocalDateTime noticeDateTime;       // 알림 일시
    private String noticeType;                  // 알림 구분
    private String noticeContent;               // 알림 내용

    public NoticeDTO(int memberId, LocalDateTime noticeDateTime, String noticeType, String noticeContent) {
        this.memberId = memberId;
        this.noticeDateTime = noticeDateTime;
        this.noticeType = noticeType;
        this.noticeContent = noticeContent;
    }
}
