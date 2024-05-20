package com.insider.login.transferredHistory.service;

import com.insider.login.member.entity.Member;
import com.insider.login.transferredHistory.dto.TransferredHistoryDTO;
import com.insider.login.transferredHistory.entity.TransferredHistory;
import com.insider.login.transferredHistory.repository.TransferredHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class TransferredHistoryService {

    private final ModelMapper modelMapper;
    private final TransferredHistoryRepository transferredHistoryRepository;

    public TransferredHistoryService (ModelMapper modelMapper, TransferredHistoryRepository transferredHistoryRepository) {
        this.modelMapper = modelMapper;
        this.transferredHistoryRepository = transferredHistoryRepository;
    }

    @Transactional
    public void saveHistory(Member savedMember) {
        /* 회원 등록을 하면서 인사 발령 내역에다가도 저장 하는 logic */
        TransferredHistoryDTO transferredHistoryDTO = modelMapper.map(savedMember, TransferredHistoryDTO.class);
        transferredHistoryDTO.setTransferredDate(savedMember.getEmployedDate());
        transferredHistoryDTO.setNewDepartNo(savedMember.getDepartment().getDepartNo());
        transferredHistoryDTO.setNewPositionName(savedMember.getPosition().getPositionName());
        System.out.println("transferredHistory에 저장될 정보들: " + transferredHistoryDTO);
    }

    public String calculateDateDifference(int transferredNo) {
        TransferredHistory transferredHistory = transferredHistoryRepository.findById(transferredNo).orElseThrow(() -> new EntityNotFoundException("인사 발령내역 조회에 실패하였습니다."));

        LocalDate transferredDate = transferredHistory.getTransferredDate();
        LocalDate currentDate = LocalDate.now();
        long monthsDifference = ChronoUnit.MONTHS.between(transferredDate, currentDate);
        long yearsDifference = ChronoUnit.YEARS.between(transferredDate, currentDate);

        String result;
        if (yearsDifference >= 1) {
            result = yearsDifference + "년 " + monthsDifference + "월";
        } else {
            result = monthsDifference + "월";
        }
        return result;
    }
}
