package com.insider.login.other.announce.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "ANNOUNCE")
@Getter
public class Announce {

    @Id
    @Column(name = "anc_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ancNo;

    @Column(name = "anc_title")
    private String ancTitle;

    @Column(name = "anc_content")
    private String ancContent;

    @Column(name = "anc_date")
    private String ancDate;

    @Column(name = "anc_writer")
    private String ancWriter;

    @Column(name = "hits")
    private int hits;

    @Column(name = "file_path")
    private String filePath;

    protected Announce() {}




}
