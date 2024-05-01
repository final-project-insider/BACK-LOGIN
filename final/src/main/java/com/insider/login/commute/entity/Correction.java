package com.insider.login.commute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "commute_corr")
@AllArgsConstructor
@Getter
@ToString
public class Correction {

    @Id
    @Column(name = "corr_no", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int corrNo;                         // 정정 요청 번호

    @Column(name = "commute_no", nullable = false)
    private int commuteNo;                      // 출퇴근 번호

    @Column(name = "req_start_work", nullable = false)
    private String reqStartWork;                // 정정 요청 출근 시간

    @Column(name = "req_end_work")
    private String reqEndWork;                  // 정정 요청 퇴근 시간

    @Column(name = "reason_for_corr", nullable = false)
    private String reasonForCorr;               // 정정 사유

    @Column(name = "corr_registration_date", nullable = false)
    private LocalDate corrRegistrationDate;     // 정정 등록 일자

    @Column(name = "corr_status", nullable = false)
    private String corrStatus;                  // 정정 상태

    @Column(name = "reason_for_rejection")
    private String reasonForRejection;          // 반려 사유

    @Column(name = "corr_processing_date")
    private LocalDate corrProcessingDate;       // 정정 처리 일자

    @OneToOne(mappedBy = "correction")
    private Commute commute;                    // 출퇴근

    protected Correction() {}

    public Correction(int corrNo, int commuteNo, String reqStartWork, String reqEndWork, String reasonForCorr, LocalDate corrRegistrationDate, String corrStatus, String reasonForRejection, LocalDate corrProcessingDate) {
        this.corrNo = corrNo;
        this.commuteNo = commuteNo;
        this.reqStartWork = reqStartWork;
        this.reqEndWork = reqEndWork;
        this.reasonForCorr = reasonForCorr;
        this.corrRegistrationDate = corrRegistrationDate;
        this.corrStatus = corrStatus;
        this.reasonForRejection = reasonForRejection;
        this.corrProcessingDate = corrProcessingDate;
    }
}
