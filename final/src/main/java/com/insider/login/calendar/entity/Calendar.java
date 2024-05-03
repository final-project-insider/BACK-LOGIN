package com.insider.login.calendar.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CALENDAR")
public class Calendar {

    @Id
    @Column(name = "CALENDAR_No", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int calendarNo;

    @Column(name = "CALENDAR_NAME", nullable = false)
    private String calendarName;

    @Column(name = "CALENDAR_START", nullable = false)
    private LocalDateTime calendarStart;

    @Column(name = "CALENDAR_END", nullable = false)
    private LocalDateTime calendarEnd;

    @Column(name = "COLOR", nullable = false
    )
    private String color;

    @Column(name = "DEPARTMENT", nullable = false)
    private String department;

    @Column(name = "REGISTRANT_ID", nullable = false)
    private int registrantId;

    protected Calendar() {
    }

    public Calendar(int calendarNo, String calendarName, LocalDateTime calendarStart, LocalDateTime calendarEnd, String color, String department, int registrantId) {
        this.calendarNo = calendarNo;
        this.calendarName = calendarName;
        this.calendarStart = calendarStart;
        this.calendarEnd = calendarEnd;
        this.color = color;
        this.department = department;
        this.registrantId = registrantId;
    }

    public int getCalendarNo() {
        return calendarNo;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public LocalDateTime getCalendarStart() {
        return calendarStart;
    }

    public LocalDateTime getCalendarEnd() {
        return calendarEnd;
    }

    public String getColor() {
        return color;
    }

    public String getDepartment() {
        return department;
    }

    public int getRegistrantId() {
        return registrantId;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "calendarNo=" + calendarNo +
                ", calendarName='" + calendarName + '\'' +
                ", calendarStart=" + calendarStart +
                ", calendarEnd=" + calendarEnd +
                ", color='" + color + '\'' +
                ", department='" + department + '\'' +
                ", registrantId=" + registrantId +
                '}';
    }
}
