package com.insider.login.proposal.Service;

import com.insider.login.proposal.DTO.ProposalDTO;
import com.insider.login.proposal.Entity.Proposal;
import com.insider.login.proposal.Repository.ProposalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProposalService {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ModelMapper modelMapper;



    public ProposalService(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    public Page<ProposalDTO> getProposalList(UserDetails member, Pageable pageable) {
        // ProposalRepository에서 Proposal 엔티티를 가져옵니다.
        Page<Proposal> proposalPage = (Page<Proposal>) proposalRepository.findByMemberId(member.getUsername(), pageable);

        // Proposal 엔티티를 ProposalDTO로 변환합니다.
        Page<ProposalDTO> proposalDTOPage = proposalPage.map(this::convertToDTO);

        return proposalDTOPage;
    }

    private ProposalDTO convertToDTO(Proposal proposal) {
        // ProposalDTO로 변환하는 로직을 구현합니다.
        ProposalDTO proposalDTO = new ProposalDTO();
        proposalDTO.setId(proposal.getId());
        proposalDTO.setContent(proposal.getContent());
        // 나머지 필드에 대해서도 엔티티에서 DTO로 값을 복사합니다.
        return proposalDTO;
    }


    public ProposalDTO registerProposal(ProposalDTO proposalDTO) {
        Proposal proposal = new Proposal();
        BeanUtils.copyProperties(proposalDTO, proposal);
        proposal.setProposalDate(LocalDate.from(LocalDateTime.now()));
        Proposal savedProposal = proposalRepository.save(proposal);
        ProposalDTO newProposal = modelMapper.map(savedProposal,ProposalDTO.class);
        return newProposal;
    }

    public ProposalDTO modifyProposal(long id, ProposalDTO proposalDTO) {
        Optional<Proposal> optionalProposal = proposalRepository.findById(id);
        if (optionalProposal.isPresent()) {
            Proposal existingProposal = optionalProposal.get();
            existingProposal.setContent(proposalDTO.getContent());
            Proposal updatedProposal = proposalRepository.save(existingProposal);
            return new ProposalDTO(updatedProposal.getContent(), updatedProposal.getMemberId(), updatedProposal.getProposalDate());
        } else {
            throw new IllegalArgumentException("Proposal not found with id: " + id);
        }
    }


    public Map<String, Object> deleteProposal(Long id) {
        Optional<Proposal> optionalProposal = proposalRepository.findById(id);
        if (optionalProposal.isPresent()) {
            proposalRepository.deleteById(id);
            Map<String, Object> result = new HashMap<>();
            result.put("삭제 결과", "성공");
            return result;
        } else {
            throw new IllegalArgumentException("Proposal not found with id: " + id);
        }
    }

}
