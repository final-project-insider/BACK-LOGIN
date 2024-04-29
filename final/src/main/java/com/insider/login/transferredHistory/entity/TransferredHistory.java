package com.insider.login.transferredHistory.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "transferred_history")
public class TransferredHistory {

    @Id
    @Column(name = "transferred_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
    private int transferredId;                          // 식별자 번호
    @Column(name = "new_depart_no", nullable = true)
    private int newDepartNo;
    @Column(name = "new_position_name", nullable = true)
    private int newPositionNo;
    @Column(name = "transferred_date", nullable = false)
    private LocalDate transferredDate;
    @Column(name = "member_id", nullable = false)
    private int memberId;

    public TransferredHistory() {
    }


}
