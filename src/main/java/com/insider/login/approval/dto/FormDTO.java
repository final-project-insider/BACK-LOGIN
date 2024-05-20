package com.insider.login.approval.dto;

public class FormDTO {

    //FormDTO

    private String formNo;          //양식번호
    private String formName;        //양식이름
    private String formShape;       //양식

    public FormDTO () {}

    public FormDTO(String formNo, String formName, String formShape) {
        this.formNo = formNo;
        this.formName = formName;
        this.formShape = formShape;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormShape() {
        return formShape;
    }

    public void setFormShape(String formShape) {
        this.formShape = formShape;
    }

    @Override
    public String toString() {
        return "FormDTO{" +
                "formNo='" + formNo + '\'' +
                ", formName='" + formName + '\'' +
                ", formShape='" + formShape + '\'' +
                '}';
    }
}
