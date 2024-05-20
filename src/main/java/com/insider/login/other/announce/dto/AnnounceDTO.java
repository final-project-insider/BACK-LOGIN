package com.insider.login.other.announce.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AnnounceDTO {

    private int ancNo;

    private String ancTitle;

    private String ancContent;

    private String ancDate;

    private String ancWriter;

    private int hits;

    private String filePath;

    public AnnounceDTO(String ancTitle, String ancContent) {
        this.ancTitle = ancTitle;
        this.ancContent = ancContent;
    }
}
