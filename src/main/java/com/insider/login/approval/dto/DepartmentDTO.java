package com.insider.login.approval.dto;

public class DepartmentDTO {

    private int departNo;               //부서번호
    private String departName;          //부서명

    public DepartmentDTO(){}

    public DepartmentDTO(int departNo, String departName) {
        this.departNo = departNo;
        this.departName = departName;
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

    @Override
    public String toString() {
        return "DepartmentDTO{" +
                "departNo=" + departNo +
                ", departName='" + departName + '\'' +
                '}';
    }
}
