package com.insider.login.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="ApprovalDepart")
@Table(name="department_info")
public class Department {

    @Id
    @Column(name="depart_no")
    private int departNo;               //부서번호
    @Column(name="depart_name")
    private String departName;          //부서명

    protected Department(){}

    public Department(int departNo, String departName) {
        this.departNo = departNo;
        this.departName = departName;
    }

    public int getDepartNo() {
        return departNo;
    }

    public String getDepartName() {
        return departName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departNo=" + departNo +
                ", departName='" + departName + '\'' +
                '}';
    }
}
