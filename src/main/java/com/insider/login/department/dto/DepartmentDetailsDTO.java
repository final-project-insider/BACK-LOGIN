package com.insider.login.department.dto;

public class DepartmentDetailsDTO {

    private int departNo;
    private String departName;
    private int noOfPeople;

    public DepartmentDetailsDTO() {
    }

    public DepartmentDetailsDTO(int departNo, String departName, int noOfPeople) {
        this.departNo = departNo;
        this.departName = departName;
        this.noOfPeople = noOfPeople;
    }

    public int getDepartNo() {
        return departNo;
    }

    public void setDepartNo(int departNo) {
        this.departNo = departNo;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    @Override
    public String toString() {
        return "DepartmentDetailsDTO{" +
                "departNo=" + departNo +
                ", departName='" + departName + '\'' +
                ", noOfPeople=" + noOfPeople +
                '}';
    }
}
