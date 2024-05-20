package com.insider.login.approval.dto;

public class AttachmentDTO {

    //AttachmentDTO

    private String fileNo;              //첨부파일번호
    private String fileOriname;         //첨부파일원본명
    private String fileSavepath;        //첨부파일저장경로
    private String fileSavename;         //첨부파일저장명
    private String approvalNo;          //결재 번호

    public AttachmentDTO() {
    }

    public AttachmentDTO(String fileNo, String fileOriname, String fileSavepath, String fileSavename, String approvalNo) {
        this.fileNo = fileNo;
        this.fileOriname = fileOriname;
        this.fileSavepath = fileSavepath;
        this.fileSavename = fileSavename;
        this.approvalNo = approvalNo;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getFileOriname() {
        return fileOriname;
    }

    public void setFileOriname(String fileOriname) {
        this.fileOriname = fileOriname;
    }

    public String getFileSavepath() {
        return fileSavepath;
    }

    public void setFileSavepath(String fileSavepath) {
        this.fileSavepath = fileSavepath;
    }

    public String getFileSavename() {
        return fileSavename;
    }

    public void setFileSavename(String fileSavename) {
        this.fileSavename = fileSavename;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo;
    }

    @Override
    public String toString() {
        return "AttachmentDTO{" +
                "fileNo='" + fileNo + '\'' +
                ", fileOriname='" + fileOriname + '\'' +
                ", fileSavepath='" + fileSavepath + '\'' +
                ", fileSavenam='" + fileSavename + '\'' +
                ", approvalNo='" + approvalNo + '\'' +
                '}';
    }
}
