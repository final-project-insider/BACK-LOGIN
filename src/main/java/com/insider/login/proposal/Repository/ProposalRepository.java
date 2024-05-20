package com.insider.login.proposal.Repository;

import com.insider.login.proposal.DTO.ProposalDTO;
import com.insider.login.proposal.Entity.Proposal;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    Page<Proposal> findByMemberIdAndDeleteYn(int memberId, String deleteYn, Pageable pageable);

    Page<Proposal> findByReceiverIdAndDeleteYn(int receiverId, String deleteYn, Pageable pageable);

    void deleteById(Long id);

    Optional<Proposal> findById(Long id);

    Page<Proposal> findByMemberId(int memberId, Pageable pageable);

    Page<ProposalDTO> findById(Long id, Pageable pageable);

    Page<Proposal> findByMemberId(String memberId, Pageable pageable);


}
