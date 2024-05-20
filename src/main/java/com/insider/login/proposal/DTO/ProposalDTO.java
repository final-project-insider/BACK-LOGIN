package com.insider.login.proposal.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProposalDTO {
    private int id;               // 건의 아이디
    private String content;        // 건의 내용
    private int memberId;          // 작성자 사번
    private LocalDate proposalDate;  // 건의 등록일자

    public ProposalDTO(String content, int memberId, LocalDate proposalDate) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.proposalDate = proposalDate;
    }
    public int getId() {
        return this.id;
    }


}
