package com.insider.login.commute.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TargetDTO {

    private String target;
    private int targetValue;
    private LocalDate date;
}
