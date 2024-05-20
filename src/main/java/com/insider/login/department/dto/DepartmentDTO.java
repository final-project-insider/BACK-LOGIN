package com.insider.login.department.dto;

import com.insider.login.department.entity.Department;

public class DepartmentDTO {

    private int departNo;
    private String departName;

    public DepartmentDTO() {
    }

    public DepartmentDTO(int departNo, String departName) {
        this.departNo = departNo;
        this.departName = departName;
    }

    public static DepartmentDTO mapToDTO(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartNo(department.getDepartNo());
        departmentDTO.setDepartName(department.getDepartName());
        return departmentDTO;
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

