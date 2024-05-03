package com.insider.login.calendar.dto;

public class CalendarCriteriaDTO {
    private String type;
    private int year;
    private int month;
    private int week;
    private int day;
    private String department;

    public CalendarCriteriaDTO(String type, int year, int month, String department) {
        this.type = type;
        this.year = year;
        this.month = month;
        this.department = department;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "CalendarCriteriaDTO{" +
                "type='" + type + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", week=" + week +
                ", day=" + day +
                ", department='" + department + '\'' +
                '}';
    }
}
