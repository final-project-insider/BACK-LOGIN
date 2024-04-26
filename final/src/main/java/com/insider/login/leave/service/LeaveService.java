package com.insider.login.leave.service;

import com.insider.login.leave.common.LeaveSubmitBuilder;
import com.insider.login.leave.dto.LeaveSubmitDTO;
import com.insider.login.leave.entity.Leave;
import com.insider.login.leave.entity.LeaveSubmit;
import com.insider.login.leave.repository.LeaveAccrualRepository;
import com.insider.login.leave.repository.LeaveRepository;
import com.insider.login.leave.repository.LeaveSubmitRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LeaveService {

    private LeaveAccrualRepository leaveAccrualRepository;
    private LeaveRepository leaveRepository;
    private LeaveSubmitRepository leaveSubmitRepository;
    private ModelMapper modelMapper;

    public LeaveService(LeaveAccrualRepository leaveAccrualRepository, LeaveRepository leaveRepository, LeaveSubmitRepository leaveSubmitRepository, ModelMapper modelMapper) {
        this.leaveAccrualRepository = leaveAccrualRepository;
        this.leaveRepository = leaveRepository;
        this.leaveSubmitRepository = leaveSubmitRepository;
        this.modelMapper = modelMapper;
    }

    public List<LeaveSubmitDTO> selectLeaveSubmitListByMemberId(int applicantId) {

        log.info("휴가 신청 내역 확인 시작 ======================================================");

        // 사번으로 해당 사원의 휴가신청내역을 가져옴
        List<LeaveSubmit> submitList = leaveSubmitRepository.findByMemberId(applicantId);

        log.info("엔티티 확인 ==========================", submitList);
        List<LeaveSubmitDTO> DTOList = new ArrayList<>();

        for (LeaveSubmit submit : submitList) {
            LeaveSubmitDTO leaveSubmitDTO = modelMapper.map(submit, LeaveSubmitDTO.class);

            // 사번으로 사원명 조회해서 DTO에 넣기 ( member 머지되면 추가 예정 )
//            leaveSubmitDTO.setApplicantName(leaveSubmitRepository.findMemberNameByMemberId(applicantId));
            DTOList.add(leaveSubmitDTO);
        }
        log.info("휴가 내역 조회 =================================");
        for (LeaveSubmitDTO DTO : DTOList) {
            log.info("내역", DTO);

        }

        return DTOList;

    }

    @Transactional
    public String insertSubmit(LeaveSubmitDTO leaveSubmitDTO) {
        log.info("[휴가신청] 시작 ================================");

        leaveSubmitDTO.setLeaveSubStatus("대기");

        int result = 0;

        try {

            log.info("[dto 확인] ============================\n" + leaveSubmitDTO);

            LeaveSubmit leaveSubmit = modelMapper.map(leaveSubmitDTO, LeaveSubmit.class);

            log.info("[휴가신청] 엔티티 확인 =======================\n" + leaveSubmit);


            leaveSubmitRepository.save(leaveSubmit);
            result = 1;

        } catch (Exception e) {
            log.info("[휴가신청] Exception!");

            result = 0;
        }

        return (result > 0) ? "신청 등록 성공" : "신청 등록 실패";
    }

    @Transactional
    public String deleteSubmit(int leaveSubNo) {
        log.info("[신청취소] 시작 ===================================");

        int result = 0;

        try {

            leaveSubmitRepository.deleteById(leaveSubNo);

            result = 1;

        } catch (Exception e) {
            log.info("[신청취소] Exception!");

            result = 0;
        }

        return (result > 0) ? "신청 취소 성공" : "신청 취소 실패";
    }

    public String insertSubmitCancel(LeaveSubmitDTO DTO) {
        log.info("[취소요청] 시작 ====================================");

        // 화면에서 받아온 정보를 취소 요청 등록 폼으로 수정
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(DTO.getLeaveSubNo(), DTO.getLeaveSubApplicant(), DTO.getLeaveSubStartDate(), DTO.getLeaveSubEndDate(), DTO.getLeaveSubApplyDate(), "취소", "대기", DTO.getLeaveSubReason());
        log.info("[취소요청] dto 확인 ==============================\n" + leaveSubmitDTO);

        int result = 0;

        try {

            LeaveSubmit leaveSubmit = modelMapper.map(leaveSubmitDTO, LeaveSubmit.class);

            log.info("[취소요청] 엔티티 확인 ==============================\n" + leaveSubmit);
            leaveSubmitRepository.save(leaveSubmit);

            result = 1;
        } catch (Exception e) {

        }

        return (result > 0) ? "취소요청 등록 성공" : "취소요청 등록 실패";
    }

    public LeaveSubmitDTO selectSubmitByLeaveSubNo(int leaveSubNo) {

        log.info("휴가 상세 조회 시작 ===================================");

        LeaveSubmitDTO leaveSubmitDTO = null;

        try {
            Optional<LeaveSubmit> leaveSubmit = leaveSubmitRepository.findById(leaveSubNo);

            log.info("엔티티 확인 ======================\n" + leaveSubmit);

            leaveSubmitDTO = modelMapper.map(leaveSubmit, LeaveSubmitDTO.class);

            log.info("DTO 확인\n" + leaveSubmitDTO);


        } catch (Exception e) {
            log.info("예외발생 =============================");
        }

        return leaveSubmitDTO;
    }
}
