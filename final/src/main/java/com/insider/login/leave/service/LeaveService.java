package com.insider.login.leave.service;

import com.insider.login.leave.dto.LeaveInfoDTO;
import com.insider.login.leave.entity.Leaves;
import com.insider.login.leave.util.LeaveUtil;
import com.insider.login.leave.dto.LeaveAccrualDTO;
import com.insider.login.leave.dto.LeavesDTO;
import com.insider.login.leave.dto.LeaveSubmitDTO;
import com.insider.login.leave.entity.LeaveAccrual;
import com.insider.login.leave.entity.LeaveSubmit;
import com.insider.login.leave.repository.LeaveAccrualRepository;
import com.insider.login.leave.repository.LeaveRepository;
import com.insider.login.leave.repository.LeaveSubmitRepository;
import com.insider.login.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LeaveService extends LeaveUtil {

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

    public Page<LeaveSubmitDTO> selectLeaveSubmitList(int applicantId, Pageable pageable) {

        log.info("[신청내역] 휴가 신청 내역 확인 시작 ======================================================");

        // 조건에 따라 휴가신청내역을 가져옴
        Page<LeaveSubmit> submitList = null;
        if (applicantId > 0) {
            submitList = leaveSubmitRepository.findByMemberId(applicantId, pageable);
        } else {
            submitList = leaveSubmitRepository.findAll(pageable);
        }

        List<LeaveSubmitDTO> DTOList = new ArrayList<>();

        for (LeaveSubmit submit : submitList) {
            LeaveSubmitDTO leaveSubmitDTO = modelMapper.map(submit, LeaveSubmitDTO.class);

            // 사번으로 사원명 조회해서 DTO에 넣기 ( member 안정화되면 추가 예정 )
//            leaveSubmitDTO.setApplicantName(memberRepository.findMemberNameByMemberId(applicantId));

            // 승인자 사번이 존재할 경우 승인자 사번으로 사원명을 조회해서 DTO에 넣기 ( member 안정화 후 추가 예정 )
//            if (submit.getLeaveSubApprover() != 0) {
//                leaveSubmitDTO.setApproverName(memberRepository.findById(submit.getLeaveSubApprover()));
//            }

            DTOList.add(leaveSubmitDTO);
        }

        // DTO로 변환된 내역들을 새로운 페이지로 만들어서 반환
        return new PageImpl<>(DTOList, submitList.getPageable(), submitList.getTotalElements());

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
        DTO.setLeaveSubType("취소");

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

            log.info("[취소요청] Exception!");

            result = 0;

        }

        return (result > 0) ? "취소요청 등록 성공" : "취소요청 등록 실패";
    }

    public Page<LeaveAccrualDTO> selectAccrualList(Pageable pageable) {

        log.info("[발생내역] 발생내역 조회 시작 ========================================");
        // 페이징 처리를 위해 pageable 변수 선언

        Page<LeaveAccrual> accrualList = leaveAccrualRepository.findAll(pageable);
        log.info("[발생내역] 엔티티 리스트 확인 ==============================");
        accrualList.forEach(System.out::println);

        if (accrualList != null) {
            return accrualList.map(accrual -> modelMapper.map(accrualList, LeaveAccrualDTO.class));
        } else {
            return Page.empty();
        }
    }

    @Transactional
    public String insertAccrual(LeaveAccrualDTO accrualDTO) {

        int result = 0;

        try {

            LeaveAccrual leaveAccrual = modelMapper.map(accrualDTO, LeaveAccrual.class);
            log.info("[발생등록] 엔티티 확인 ============================\n" + leaveAccrual);

            leaveAccrualRepository.save(leaveAccrual);

            result = 1;
        } catch (Exception e) {

            log.info("[발생등록] Exception!");

            result = 0;

        }

        return (result > 0) ? "휴가발생 등록 성공" : "휴가발생 등록 실패";
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

    @Transactional
    public String updateSubmit(LeaveSubmitDTO leaveSubmitDTO) {
        log.info("[휴가처리] 시작 ===============================");
        int result = 0;

        if (leaveSubmitDTO.getLeaveSubStatus().equals("승인")) {
            try {
                // 업데이트할 사번으로 현재 등록된 정보 조회
                Optional<LeaveSubmit> leaveSubmit = leaveSubmitRepository.findById(leaveSubmitDTO.getLeaveSubNo());

                // 엔티티를 dto로 변환 후 변경할 정보 삽입
                LeaveSubmitDTO tempDTO = modelMapper.map(leaveSubmit, LeaveSubmitDTO.class);
                tempDTO.setLeaveSubApprover(leaveSubmitDTO.getLeaveSubApprover());
                tempDTO.setLeaveSubStatus(leaveSubmitDTO.getLeaveSubStatus());
                tempDTO.setLeaveSubProcessDate(leaveSubmitDTO.getLeaveSubProcessDate());

                // 다시 dto를 엔티티로 전환 후 save
                LeaveSubmit newSubmit = modelMapper.map(tempDTO, LeaveSubmit.class);
                leaveSubmitRepository.save(newSubmit);

                log.info("[휴가처리] 엔티티 확인 =========================================\n" + newSubmit);

            } catch (Exception e) {
                log.info("[휴가처리] 예외발생 =============================");
                result = 0;
            }

        } else if (leaveSubmitDTO.getLeaveSubStatus().equals("반려")) {
            try {
                // 업데이트할 사번으로 현재 등록된 정보 조회
                Optional<LeaveSubmit> leaveSubmit = leaveSubmitRepository.findById(leaveSubmitDTO.getLeaveSubNo());

                // 엔티티를 dto로 변환 후 변경할 정보 삽입
                LeaveSubmitDTO tempDTO = modelMapper.map(leaveSubmit, LeaveSubmitDTO.class);
                tempDTO.setLeaveSubApprover(leaveSubmitDTO.getLeaveSubApprover());
                tempDTO.setLeaveSubStatus(leaveSubmitDTO.getLeaveSubStatus());
                tempDTO.setLeaveSubProcessDate(leaveSubmitDTO.getLeaveSubProcessDate());
                tempDTO.setLeaveSubReason(leaveSubmitDTO.getLeaveSubReason());

                // 다시 dto를 엔티티로 전환 후 save
                LeaveSubmit newSubmit = modelMapper.map(tempDTO, LeaveSubmit.class);
                leaveSubmitRepository.save(newSubmit);

                log.info("[휴가처리] 엔티티 확인 =========================================\n" + newSubmit);

            } catch (Exception e) {
                log.info("[휴가처리] 예외발생 =============================");
                result = 0;
            }

        } else {
            result = 0;
        }
        return (result > 0) ? "휴가처리 성공" : "휴가처리 실패";
    }

    public Page<LeaveInfoDTO> selectLeavesList(Pageable pageable) {
        log.info("[보유내역] 시작 ==========================================");
        try {

            Page<Leaves> leavesPage = leaveRepository.findAll(pageable);

            Map<Integer, List<Leaves>> leavesByMemberId = leavesPage.stream()
                    // Collectors.groupingBy()를 통해 Map 타입으로 반환, 괄호 안은 그룹화 기준이자 키
                    .collect(Collectors.groupingBy(Leaves::getMemberId));

            List<LeaveInfoDTO> infoDTOList = new ArrayList<>();

            for (Map.Entry<Integer, List<Leaves>> entry : leavesByMemberId.entrySet()) {

                // 밸류에는 하나의 사번이 가진 모든 휴가내역을 list로 가지고 있음
                LeaveInfoDTO info = leaveInfoCalc(entry.getValue());

                // addLeaveInfo()로 소진 일수와 잔여 일수를 추가한 후 리스트에 담음
                infoDTOList.add(addLeaveInfo(info));
            }

            // 리스트를 페이지로 전환
            Page<LeaveInfoDTO> infoDTOPage = new PageImpl<>(infoDTOList, pageable, leavesPage.getTotalElements());

            return infoDTOPage;
        } catch (Exception e) {

            log.info("[보유내역] 에러 ======================================");
        }

        return null;
    }

    // 사번을 매개변수로 주면 해당 사원의 휴가 정보를 DTO로 반환하는 메소드
    public LeaveInfoDTO getLeaveInfoById(int memberId) {

        List<Leaves> leavesList = leaveRepository.findByMemberId(memberId);

        LeaveInfoDTO info = leaveInfoCalc(leavesList);
        log.info("휴가 보유 조회 ===========\n"  + info);

        return addLeaveInfo(info);
    }

    // leaveInfo DTO에 소진 일수와 잔여 일수를 추가해서 다시 반환하는 메소드
    public LeaveInfoDTO addLeaveInfo(LeaveInfoDTO DTO) {

        // 소진 일수 (사번으로 신청내역을 조회해서 신청일수를 모두 더함)
        int consumedDays = 0;
        List<LeaveSubmit> submitList = leaveSubmitRepository.findByMemberId(DTO.getMemberId());
        for (LeaveSubmit submit : submitList) {
            consumedDays += leaveDaysCalc(submit);
        }

        // 잔여 일수 (총 부여 수에서 소진 일수를 뺌)
        int remainingDays = DTO.getTotalDays() - consumedDays;

        DTO.setConsumedDays(consumedDays);
        DTO.setRemainingDays(remainingDays);

        return DTO;
    }
}
