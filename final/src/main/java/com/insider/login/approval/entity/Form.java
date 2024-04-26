package com.insider.login.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Form")
@Table(name="FORM")
public class Form {

    @Id
    @Column(name = "FORM_NO")
    private String formNo;          //양식번호

    @Column(name = "FORM_NAME")
    private String formName;        //양식제목

    @Column(name = "FORM_SHAPE")
    private String formShape;       //양식

    protected Form() {}

    public Form(String formNo, String formName, String formShape) {
        this.formNo = formNo;
        this.formName = formName;
        this.formShape = formShape;
    }

    public String getFormNo() {
        return formNo;
    }

    public String getFormName() {
        return formName;
    }

    public String getFormShape() {
        return formShape;
    }

    @Override
    public String toString() {
        return "Form{" +
                "formNo='" + formNo + '\'' +
                ", formName='" + formName + '\'' +
                ", formShape='" + formShape + '\'' +
                '}';
    }
}
