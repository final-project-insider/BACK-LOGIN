package com.insider.login.department.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "department_info")
public class Department {

    @Id
    @Column(name = "depart_no", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departNo;
    @Column(name = "depart_name", nullable = false)
    private String departName;

    protected Department() {
    }

    public Department(int departNo, String departName) {
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
        return "Department{" +
                "departNo=" + departNo +
                ", departName='" + departName + '\'' +
                '}';
    }
}
