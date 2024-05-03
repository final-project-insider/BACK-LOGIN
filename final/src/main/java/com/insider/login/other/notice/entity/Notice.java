package com.insider.login.other.notice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "Notice")
@Table(name = "notice")
@AllArgsConstructor
@Getter
@ToString
public class Notice {

    @Id
    @Column(name = "notice_no", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeNo;           // 알림 번호

    @Column(name = "member_id", nullable = false)
    private int memberId;           // 사번

    @Column(name = "notice_datetime", nullable = false)
    private LocalDateTime noticeDateTime;       // 알림 일시

    @Column(name = "notice_type", nullable = false)
    private String noticeType;                  // 알림 구분

    @Column(name = "notice_content", nullable = false)
    private String noticeContent;               // 알림 내용

    protected Notice() {}

}
