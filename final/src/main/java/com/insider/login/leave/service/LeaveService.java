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

    private final LeaveAccrualRepository leaveAccrualRepository;
    private final LeaveRepository leaveRepository;
    private final LeaveSubmitRepository leaveSubmitRepository;
    private final ModelMapper modelMapper;

    public LeaveService(LeaveAccrualRepository leaveAccrualRepository, LeaveRepository leaveRepository, LeaveSubmitRepository leaveSubmitRepository, ModelMapper modelMapper) {
        this.leaveAccrualRepository = leaveAccrualRepository;
        this.leaveRepository = leaveRepository;
        this.leaveSubmitRepository = leaveSubmitRepository;
        this.modelMapper = modelMapper;
    }

    public Page<LeaveSubmitDTO> selectLeaveSubmitList(int applicantId, Pageable pageable) {
        try {
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

                // TODO:사번으로 사원명 조회해서 DTO에 넣기 ( member 안정화되면 추가 예정 )
//            leaveSubmitDTO.setApplicantName(memberRepository.findMemberNameByMemberId(applicantId));

                // TODO:승인자 사번이 존재할 경우 승인자 사번으로 사원명을 조회해서 DTO에 넣기 ( member 안정화 후 추가 예정 )
//            if (submit.getLeaveSubApprover() != 0) {
//                leaveSubmitDTO.setApproverName(memberRepository.findById(submit.getLeaveSubApprover()));
//            }

                DTOList.add(leaveSubmitDTO);
            }

            // DTOList를 기존 pageable 정보를 가진 새로운 페이지로 만들어서 반환
            return new PageImpl<>(DTOList, submitList.getPageable(), submitList.getTotalElements());
        } catch (Exception e) {
            return Page.empty();
        }
    }

    @Transactional
    public String insertSubmit(LeaveSubmitDTO leaveSubmitDTO) {
        try {
            leaveSubmitDTO.setLeaveSubStatus("대기");

            LeaveSubmit leaveSubmit = modelMapper.map(leaveSubmitDTO, LeaveSubmit.class);

            leaveSubmitRepository.save(leaveSubmit);

            return "신청 등록 성공";
        } catch (Exception e) {
            return "신청 등록 실패";
        }
    }

    @Transactional
    public String deleteSubmit(int leaveSubNo) {
        try {
            leaveSubmitRepository.deleteById(leaveSubNo);

            return "신청 취소 성공";
        } catch (Exception e) {
            return "신청 취소 실패";
        }
    }

    public String insertSubmitCancel(LeaveSubmitDTO DTO) {
        try {
            // 화면에서 받아온 정보를 취소 요청 등록 폼으로 수정
            LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(DTO.getLeaveSubNo(), DTO.getLeaveSubApplicant(), DTO.getLeaveSubStartDate(), DTO.getLeaveSubEndDate(), DTO.getLeaveSubApplyDate(), "취소", "대기", DTO.getLeaveSubReason());

            LeaveSubmit leaveSubmit = modelMapper.map(leaveSubmitDTO, LeaveSubmit.class);

            leaveSubmitRepository.save(leaveSubmit);

            return "취소요청 등록 성공";
        } catch (Exception e) {
            return "취소요청 등록 실패";
        }
    }

    public Page<LeaveAccrualDTO> selectAccrualList(Pageable pageable) {
        try {
            Page<LeaveAccrual> accrualList = leaveAccrualRepository.findAll(pageable);

            List<LeaveAccrualDTO> DTOList = new ArrayList<>();
            if (accrualList != null) {

                for (LeaveAccrual accrual : accrualList) {
                    DTOList.add(modelMapper.map(accrual, LeaveAccrualDTO.class));
                }

                // DTOList를 기존 pageable 정보를 가진 새로운 페이지로 만들어서 반환
                return new PageImpl<>(DTOList, accrualList.getPageable(), accrualList.getTotalElements());
            } else {
                return Page.empty();
            }
        } catch (Exception e) {
            return Page.empty();
        }
    }

    @Transactional
    public String insertAccrual(LeaveAccrualDTO accrualDTO) {
        try {
            LeaveAccrual leaveAccrual = modelMapper.map(accrualDTO, LeaveAccrual.class);

            leaveAccrualRepository.save(leaveAccrual);

            return "휴가발생 등록 성공";
        } catch (Exception e) {
            return "휴가발생 등록 실패";
        }
    }


    public LeaveSubmitDTO selectSubmitByLeaveSubNo(int leaveSubNo) {
        try {
            Optional<LeaveSubmit> leaveSubmit = leaveSubmitRepository.findById(leaveSubNo);

            LeaveSubmitDTO leaveSubmitDTO = modelMapper.map(leaveSubmit, LeaveSubmitDTO.class);

            return leaveSubmitDTO;

        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public String updateSubmit(LeaveSubmitDTO leaveSubmitDTO) {
        try {
            // 업데이트할 신청 번호로 해당 휴가 신청 정보 조회
            Optional<LeaveSubmit> leaveSubmit = leaveSubmitRepository.findById(leaveSubmitDTO.getLeaveSubNo());

            // 엔티티를 dto로 변환 후 변경할 정보 삽입
            LeaveSubmitDTO tempDTO = modelMapper.map(leaveSubmit, LeaveSubmitDTO.class);
            tempDTO.setLeaveSubApprover(leaveSubmitDTO.getLeaveSubApprover());
            tempDTO.setLeaveSubStatus(leaveSubmitDTO.getLeaveSubStatus());
            tempDTO.setLeaveSubProcessDate(leaveSubmitDTO.getLeaveSubProcessDate());

            // 반려시 반려 사유 세팅
            if (leaveSubmitDTO.getLeaveSubStatus().equals("반려")) {
                tempDTO.setLeaveSubReason(leaveSubmitDTO.getLeaveSubReason());
            }

            // 다시 dto를 엔티티로 전환 후 save
            LeaveSubmit newSubmit = modelMapper.map(tempDTO, LeaveSubmit.class);
            leaveSubmitRepository.save(newSubmit);

            return "휴가처리 성공";
        } catch (Exception e) {
            return "휴가처리 실패";
        }
    }

    public Page<LeaveInfoDTO> selectLeavesList(Pageable pageable) {
        try {
            Page<Leaves> leavesPage = leaveRepository.findAll(pageable);

            // Collectors.groupingBy()를 통해 Map 타입으로 반환, 괄호 안은 그룹화 기준이자 키
            Map<Integer, List<Leaves>> leavesByMemberId = leavesPage.stream()
                    .collect(Collectors.groupingBy(Leaves::getMemberId));

            List<LeaveInfoDTO> infoDTOList = new ArrayList<>();
            for (Map.Entry<Integer, List<Leaves>> entry : leavesByMemberId.entrySet()) {

                // 밸류에는 하나의 사번이 가진 모든 휴가내역을 list로 가지고 있음
                LeaveInfoDTO info = leaveInfoCalc(entry.getValue());

                // addLeaveInfo()로 소진 일수와 잔여 일수를 추가한 후 리스트에 담음
                infoDTOList.add(addLeaveInfo(info));
            }

            // DTOList를 기존 pageable 정보를 가진 새로운 페이지로 만들어서 반환
            Page<LeaveInfoDTO> infoDTOPage = new PageImpl<>(infoDTOList, pageable, leavesPage.getTotalElements());

            return infoDTOPage;
        } catch (Exception e) {
            return null;
        }
    }

    // 사번을 매개변수로 주면 해당 사원의 휴가 정보를 DTO로 반환하는 메소드
    public LeaveInfoDTO getLeaveInfoById(int memberId) {

        List<Leaves> leavesList = leaveRepository.findByMemberId(memberId);

        LeaveInfoDTO info = leaveInfoCalc(leavesList);

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
