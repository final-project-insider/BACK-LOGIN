package com.insider.login.department.dto;

public class DepartmentChangeNameDTO {

    private String departName;
    private String departNo;
    private String newDepartName;

    public DepartmentChangeNameDTO() {
    }

    public DepartmentChangeNameDTO(String departName, String departNo, String newDepartName) {
        this.departName = departName;
        this.departNo = departNo;
        this.newDepartName = newDepartName;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDepartNo() {
        return departNo;
    }

    public void setDepartNo(String departNo) {
        this.departNo = departNo;
    }

    public String getNewDepartName() {
        return newDepartName;
    }

    public void setNewDepartName(String newDepartName) {
        this.newDepartName = newDepartName;
    }

    @Override
    public String toString() {
        return "DepartmentChangeNameDTO{" +
                "departName='" + departName + '\'' +
                ", departNo='" + departNo + '\'' +
                ", newDepartName='" + newDepartName + '\'' +
                '}';
    }
}
