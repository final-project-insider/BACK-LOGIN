package com.insider.login.proposal.Entity;

import com.insider.login.proposal.DTO.ProposalDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "proposal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Proposal extends ProposalDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "proposal_id")
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "member_id")
    private int memberId;

    @Column(name = "receiver_id")
    private int receiverId;

    @Column(name = "created_at")
    private LocalDate proposalDate;

    @Column(name = "delete_yn")
    private boolean deleteYn;



    public Proposal(String content, int memberId, LocalDate proposalDate) {
        this.content = content;
        this.memberId = memberId;
        this.setProposalDate(proposalDate);
    }

    public void setDate(LocalDate proposalDate) {
        this.setProposalDate(proposalDate);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getProposalDate() {
        return this.proposalDate;
    }

    public void setProposalDate(LocalDate proposalDate) {
        this.proposalDate = proposalDate;
    }


    public int getProposalNo() {
        return id;
    }


    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
