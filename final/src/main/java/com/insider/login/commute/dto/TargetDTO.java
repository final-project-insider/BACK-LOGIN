package com.insider.login.commute.dto;

import com.insider.login.commute.entity.Commute;
import com.insider.login.commute.entity.Correction;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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
