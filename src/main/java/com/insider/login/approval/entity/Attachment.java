package com.insider.login.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="Apr_attachment")
@Table(name="APR_ATTACHMENT")
public class Attachment {

    //Attachment 엔티티

    @Id
    @Column(name="FILE_NO")
    private String fileNo;          //첨부파일번호

    @Column(name="FILE_ORINAME")
    private String fileOriname;     //첨부파일원본명

    @Column(name="FILE_SAVEPATH")
    private String fileSavepath;    //첨부파일저장경로

    @Column(name="FILE_SAVENAME")
    private String fileSavename;    //첨부파일저장명

    @Column(name="APPROVAL_NO")
    private String approvalNo;      //결재번호

    protected Attachment (){}

    public Attachment(String fileNo, String fileOriname, String fileSavepath, String fileSavename, String approvalNo) {
        this.fileNo = fileNo;
        this.fileOriname = fileOriname;
        this.fileSavepath = fileSavepath;
        this.fileSavename = fileSavename;
        this.approvalNo = approvalNo;
    }

    public String getFileNo() {
        return fileNo;
    }

    public String getFileOriname() {
        return fileOriname;
    }

    public String getFileSavepath() {
        return fileSavepath;
    }

    public String getFileSavename() {
        return fileSavename;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "fileNo='" + fileNo + '\'' +
                ", fileOriname='" + fileOriname + '\'' +
                ", fileSavepath='" + fileSavepath + '\'' +
                ", fileSavename='" + fileSavename + '\'' +
                ", approvalNo='" + approvalNo + '\'' +
                '}';
    }
}
