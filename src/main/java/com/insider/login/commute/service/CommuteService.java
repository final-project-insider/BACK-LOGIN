package com.insider.login.commute.service;

import com.insider.login.commute.dto.CommuteDTO;
import com.insider.login.commute.dto.CorrectionDTO;
import com.insider.login.commute.dto.UpdateProcessForCorrectionDTO;
import com.insider.login.commute.dto.UpdateTimeOfCommuteDTO;
import com.insider.login.commute.entity.Commute;
import com.insider.login.commute.entity.CommuteDepartment;
import com.insider.login.commute.entity.Correction;
import com.insider.login.commute.repository.CommuteRepository;
import com.insider.login.commute.repository.CorrectionRepository;

import com.insider.login.commute.entity.CommuteMember;

import com.insider.login.commute.repository.CommuteDepartmentRepository;
import com.insider.login.commute.repository.CommuteMemberRepository;
import com.insider.login.other.notice.dto.NoticeDTO;
import com.insider.login.other.notice.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommuteService {

    private final CommuteRepository commuteRepository;
    private final CorrectionRepository correctionRepository;
    private final CommuteDepartmentRepository commuteDepartmentRepository;
    private final CommuteMemberRepository commuteMemberRepository;
    private final NoticeService noticeService;
    private final CommuteMemberService commuteMemberService;
    private final ModelMapper modelMapper;

    public CommuteService(CommuteRepository commuteRepository,
                          ModelMapper modelMapper,
                          CorrectionRepository correctionRepository,
                          CommuteDepartmentRepository commuteDepartmentRepository,
                          CommuteMemberRepository commuteMemberRepository,
                          NoticeService noticeService,
                          CommuteMemberService commuteMemberService) {

        this.commuteRepository = commuteRepository;
        this.modelMapper = modelMapper;
        this.correctionRepository = correctionRepository;
        this.commuteDepartmentRepository = commuteDepartmentRepository;
        this.commuteMemberRepository = commuteMemberRepository;
        this.noticeService = noticeService;
        this.commuteMemberService = commuteMemberService;
    }

    @Transactional
    public Map<String, Object> insertTimeOfCommute(CommuteDTO newCommute) {

        log.info("[CommuteService] insertTimeOfCommute");
        log.info("[CommuteService] CommuteDTO : " + newCommute);

        Map<String, Object> result = new HashMap<>();

        try {
            commuteRepository.save(modelMapper.map(newCommute, Commute.class));
            result.put("result", true);

        } catch (Exception e) {
            result.put("result", false);
        }

        log.info("[CommuteService] commuteNo : " + newCommute.getCommuteNo());
        log.info("[CommuteService] insertTimeOfCommute End ===========");
        return result;
    }


    @Transactional
    public Map<String, Object> updateTimeOfCommuteByCommuteNo(int commuteNo , UpdateTimeOfCommuteDTO updateTimeOfCommute) {

        Map<String, Object> result = new HashMap<>();

        Commute commute = commuteRepository.findByCommuteNo(commuteNo);

        log.info("update 전 : " , commute);

        if(commute != null) {
            CommuteDTO commuteDTO = modelMapper.map(commute, CommuteDTO.class);

            commuteDTO.setEndWork(updateTimeOfCommute.getEndWork());
            commuteDTO.setWorkingStatus(updateTimeOfCommute.getWorkingStatus());
            commuteDTO.setTotalWorkingHours(updateTimeOfCommute.getTotalWorkingHours());

            Commute updateCommute = modelMapper.map(commuteDTO, Commute.class);
            commuteRepository.save(updateCommute);

            log.info("update 후 : ", commuteRepository.save(updateCommute));

            result.put("result", true);

        } else {
            result.put("result", false);
        }
        return result;
    }

    @Transactional
    public List<CommuteDTO> selectCommuteListByDepartNo(int departNo, LocalDate startDayOfMonth, LocalDate endDayOfMonth) {

        log.info("[CommuteService] selectCommuteListByDepartNo");
        log.info("[CommuteService] departNo : ", departNo);

        CommuteDepartment findCommuteDepartmentByDepartNo = commuteDepartmentRepository.findByDepartNo(departNo);

        List<CommuteMember> findCommuteMemberByDepartment = commuteMemberRepository.findByDepartNo(findCommuteDepartmentByDepartNo.getDepartNo());

        List<Commute> findCommuteByMember = new ArrayList<>();

        for(CommuteMember commuteMember : findCommuteMemberByDepartment) {
            List<Commute> memberCommuteList = commuteRepository.findByCommuteMemberAndWorkingDateBetween(commuteMember, startDayOfMonth, endDayOfMonth);
            findCommuteByMember.addAll(memberCommuteList);
        }

        List<CommuteDTO> commuteDTOList = findCommuteByMember.stream()
                                            .map(commute -> modelMapper.map(commute, CommuteDTO.class))
                                            .collect(Collectors.toList());

        log.info("[CommuteService] selectCommuteListByDepartNo End ==================");

        return commuteDTOList;
    }

    @Transactional
    public List<CommuteDTO> selectCommuteListByMemberId(int memberId, LocalDate startWeek, LocalDate endWeek) {

        log.info("[CommuteService] selectCommuteListByMemberId");
        log.info("[CommuteService] memberId : " , memberId);

        List<Commute> findCommuteByMember = commuteRepository.findByMemberIdAndWorkingDateBetween(memberId, startWeek, endWeek);

        List<CommuteDTO> commuteDTOList = findCommuteByMember.stream()
                                            .map(commute -> modelMapper.map(commute, CommuteDTO.class))
                                            .collect(Collectors.toList());

        log.info("[CommuteService] selectCommuteListByMemberId End ================");

        return commuteDTOList;
    }

    @Transactional
    public Map<String, Object> insertRequestForCorrect(CorrectionDTO newCorrection) {

        log.info("[CommuteService] insertRequestForCorrect");
        log.info("[CommuteService] newCorrection : ", newCorrection);

        Map<String, Object> result = new HashMap<>();

        try {

            int commuteNo = newCorrection.getCommuteNo();
            Commute commute = commuteRepository.findByCommuteNo(commuteNo);

            CorrectionDTO correctionDTO = new CorrectionDTO();

            correctionDTO.setCommuteNo(commuteNo);
            correctionDTO.setReasonForCorr(newCorrection.getReasonForCorr());
            correctionDTO.setCorrRegistrationDate(newCorrection.getCorrRegistrationDate());
            correctionDTO.setCorrStatus(newCorrection.getCorrStatus());

            /** 1. 출퇴근 내역에서 출근 시간과 퇴근 시간이 모두 존재하는 경우 */
            if(commute.getStartWork() != null && commute.getEndWork() != null) {

                /** 1-1. 출근 시간과 퇴근시간 모두 정정 요청 */
                if(newCorrection.getReqStartWork() != null && newCorrection.getReqEndWork() != null) {
                    correctionDTO.setReqStartWork(newCorrection.getReqStartWork());
                    correctionDTO.setReqEndWork(newCorrection.getReqEndWork());

                /** 1-2. 출근 시간만 정정 요청 */
                } else if(newCorrection.getReqStartWork() != null && newCorrection.getReqEndWork() == null) {
                    correctionDTO.setReqStartWork(newCorrection.getReqStartWork());

                /** 1-3. 퇴근 시간만 정정 요청 */
                } else if(newCorrection.getReqEndWork() != null && newCorrection.getReqStartWork() == null) {
                    correctionDTO.setReqEndWork(newCorrection.getReqEndWork());

                /** 1-4. 출퇴근 정정 요청 시간이 존재하지 않는 경우 */
                } else {
                    System.out.println("출퇴근 정정 요청 시간 null !!!!!!!!!!!!!!");
                }

            /** 2. 출퇴근 내역에서 출근 시간만 존재하는 경우 */
            } else if(commute.getStartWork() != null && commute.getEndWork() == null) {

                /** 2-1. 출근 시간만 정정 요청 */
                if(newCorrection.getReqStartWork() != null & newCorrection.getReqEndWork() == null) {
                    correctionDTO.setReqStartWork(newCorrection.getReqStartWork());

                /** 2-2. 나머지 경우 */
                } else {
                    System.out.println("출퇴근 정정 요청 error !!!!!!!!!!!!!!!!!!");
                }

            /** 3. 출퇴근 내역이 존재하지 않는 경우 */
            } else {
                System.out.println("출퇴근 시간이 null !!!!!!!!!");
            }

            correctionRepository.save(modelMapper.map(correctionDTO, Correction.class));
            result.put("result", true);

            /** 출퇴근 정정 요청 등록 시 처리자에게 알림 */

            List<CommuteMember> adminCommuteMembers = commuteMemberService.getAdminMembers();
            for(CommuteMember adminCommuteMember : adminCommuteMembers) {
                NoticeDTO newNotice = new NoticeDTO();

                System.out.println("adminMember : " + adminCommuteMember);

                newNotice.setMemberId(adminCommuteMember.getMemberId());
                newNotice.setNoticeType("출퇴근");
                newNotice.setNoticeContent("새로운 출퇴근 정정 요청이 있습니다. 확인해주세요.");
                newNotice.setNoticeDateTime(LocalDateTime.now());

                noticeService.insertNewNotice(newNotice);
            }

            log.info("[CommuteService] 출퇴근 정정 요청 등록 후 ");

        } catch (Exception e ) {
            System.out.println("출퇴근 정정 요청 Error");
            e.printStackTrace();
            result.put("result", false);
        }

        log.info("[CommuteService] insertRequestForCorrect End ================");

        return result;
    }

    @Transactional
    public Map<String, Object> updateProcessForCorrectByCorrNo(int corrNo, UpdateProcessForCorrectionDTO updateProcessForCorrection) {

        log.info("[CommuteService] updateProcessForCorrectByCorrNo");
        log.info("[CommuteService] updateProcessForCorrection : ", updateProcessForCorrection);

        Map<String, Object> result = new HashMap<>();

        Correction correction = correctionRepository.findByCorrNo(corrNo);

        int commuteNo = correction.getCommuteNo();
        Commute commute = commuteRepository.findByCommuteNo(commuteNo);

        if(correction != null && commute != null) {

            /** 1. 출퇴근 정정 요청 내역 update */
            CorrectionDTO correctionDTO = modelMapper.map(correction, CorrectionDTO.class);

            correctionDTO.setCorrStatus(updateProcessForCorrection.getCorrStatus());
            correctionDTO.setCorrProcessingDate(updateProcessForCorrection.getCorrProcessingDate());

            /** 2. 출퇴근 내역 update */
            CommuteDTO commuteDTO = modelMapper.map(commute, CommuteDTO.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            /** 1-1. 출퇴근 정정 처리 - 승인 */
            if(updateProcessForCorrection.getReasonForRejection() == null) {

                Correction updateCorrection = modelMapper.map(correctionDTO, Correction.class);
                correctionRepository.save(updateCorrection);

                /** 1-1-1. 출근 시간만 존재할 때 => 출근 시간만 정정 가능 */
                if(commute.getStartWork() != null && commute.getEndWork() == null) {

                    LocalTime updateStartWork = LocalTime.parse(correction.getReqStartWork(), formatter);
                    commuteDTO.setStartWork(updateStartWork);

                    log.info("[CommuteService] 출근시간만 존재할 때 출근시간 정정 처리 후 ");

                    /** 1-1-2. 출근시간, 퇴근시간 모두 존재할 때 */
                } else if (commute.getStartWork() != null && commute.getEndWork() != null) {

                    /** 1-1-2-1. 출근시간, 퇴근시간 모두 정정 */
                    if(correction.getReqStartWork() != null && correction.getReqEndWork() != null) {

                        LocalTime updateStartWork = LocalTime.parse(correction.getReqStartWork(), formatter);
                        LocalTime updateEndWork = LocalTime.parse(correction.getReqEndWork(), formatter);

                        Duration workingDuration = Duration.between(updateStartWork, updateEndWork).minusHours(1);
                        int totalWorkingHours = (int) workingDuration.toMinutes();

                        commuteDTO.setStartWork(updateStartWork);
                        commuteDTO.setEndWork(updateEndWork);
//                        commuteDTO.setWorkingStatus("퇴근");                  // 무단 결근 상황에서 개인 연차를 사용하여 출퇴근 시간을 정상으로 정정 요청 할 때
                        commuteDTO.setTotalWorkingHours(totalWorkingHours);

                        log.info("[CommuteService] 출근시간, 퇴근시간 모두 정정 처리 후 ");

                        /** 1-1-2-2. 출근시간만 정정 */
                    } else if (correction.getReqStartWork() != null && correction.getReqEndWork() == null) {

                        LocalTime updateStartWork = LocalTime.parse(correction.getReqStartWork(), formatter);
                        LocalTime originalEndWork = commute.getEndWork();

                        Duration workingDuration = Duration.between(updateStartWork, originalEndWork).minusHours(1);
                        int totalWorkingHours = (int) workingDuration.toMinutes();

                        commuteDTO.setStartWork(updateStartWork);
                        commuteDTO.setTotalWorkingHours(totalWorkingHours);

                        log.info("[CommuteService] 출근시간만 정정 처리 후 ");

                        /** 1-1-2-3. 퇴근시간만 정정 */
                    } else if (correction.getReqStartWork() == null && correction.getReqEndWork() != null) {

                        LocalTime originalStartWork = commute.getStartWork();
                        LocalTime updateEndWork = LocalTime.parse(correction.getReqEndWork(), formatter);

                        Duration workingDuration = Duration.between(originalStartWork, updateEndWork).minusHours(1);
                        int totalWorkingHours = (int) workingDuration.toMinutes();

                        commuteDTO.setEndWork(updateEndWork);
                        commuteDTO.setTotalWorkingHours(totalWorkingHours);

                        log.info("[CommuteService] 퇴근시간만 정정 처리 후 ");

                    /** 1-1-2-4. 출퇴근 정정 요청 시간 모두 null */
                    } else {
                        System.out.println("출퇴근 정정 요청 시간 모두 null !!!!");
                    }

                /** 1-1-3. 출근시간, 퇴근시간 모두 존재하지 않을 때 */
                } else {
                    System.out.println("출퇴근 시간 모두 null !!!!!");
                }

                log.info("[CommuteService] Correction update 승인 처리 후 ");

            /** 1-2. 출퇴근 정정 처리 - 반려 */
            } else {
                correctionDTO.setReasonForRejection(updateProcessForCorrection.getReasonForRejection());

                Correction updateCorrection = modelMapper.map(correctionDTO, Correction.class);
                correctionRepository.save(updateCorrection);

                log.info("[CommuteService] Correction update 반려 처리 후 ");
            }

            Commute updateCommute = modelMapper.map(commuteDTO, Commute.class);

            commuteRepository.save(updateCommute);

            log.info("[CommuteService] Commute update 후 ");

            /** 출퇴근 정정 처리 시 요청자에게 알림 */

            NoticeDTO newNotice = new NoticeDTO();
            newNotice.setMemberId(commute.getMemberId());
            newNotice.setNoticeType("출퇴근");
            newNotice.setNoticeContent("출퇴근 정정 요청이 처리되었습니다. 확인해주세요.");
            newNotice.setNoticeDateTime(LocalDateTime.now());

            noticeService.insertNewNotice(newNotice);

            result.put("result", true);

        } else {
            result.put("result", false);

            log.info("[CommuteService] Error !!! ");
        }

        log.info("[CommuteService] updateProcessForCorrectByCorrNo End ================");

        return result;
    }

    @Transactional
    public Page<CorrectionDTO> selectRequestForCorrectList(LocalDate startDayOfMonth, LocalDate endDayOfMonth, Pageable pageable) {
        log.info("[CommuteService] selectRequestForCorrectList");

        Page<Correction> correctionList = correctionRepository.findAllByCorrRegistrationDateBetween(startDayOfMonth, endDayOfMonth, pageable);

        if(correctionList != null) {
            return correctionList.map(correction -> modelMapper.map(correction, CorrectionDTO.class));
        } else {
            return Page.empty();
        }
    }

    @Transactional
    public Page<CorrectionDTO> selectRequestForCorrectListByMemberId(int memberId, LocalDate startDayOfMonth, LocalDate endDayOfMonth, Pageable pageable) {
        log.info("[CommuteService] selectRequestForCorrectListByMemberId");
        log.info("[CommuteService] memberId : ", memberId);

        Page<Correction> correctionListByMemberId = correctionRepository.findByCommuteMemberIdAndCorrRegistrationDateBetween(memberId, startDayOfMonth, endDayOfMonth, pageable);

        if(correctionListByMemberId != null) {
            return correctionListByMemberId.map(correction -> modelMapper.map(correction, CorrectionDTO.class));
        } else {
            return Page.empty();
        }
    }


    @Transactional
    public CorrectionDTO selectRequestForCorrectByCorrNo(int corrNo) {
        log.info("[CommuteService] selectRequestForCorrectByCorrNo");
        log.info("[CommuteService] corrNo : ", corrNo);

        Correction correction = correctionRepository.findByCorrNo(corrNo);

        CorrectionDTO correctionDTO = modelMapper.map(correction, CorrectionDTO.class);

        return correctionDTO;
    }

    @Transactional
    public CommuteDTO searchLastCommuteNoByMemberId(int memberId) {
        log.info("[CommuteService] searchLastCommuteNoByMemberId");
        log.info("[CommuteService] memberId : ", memberId);

        CommuteMember findMemberByMemberId = commuteMemberRepository.findByMemberId(memberId);

        List<Commute> findCommuteListByMember = findMemberByMemberId.getCommuteList();

        Commute lastCommute = findCommuteListByMember.stream()
                .max(Comparator.comparing(Commute::getStartWork))
                .orElse(null);

        CommuteDTO lastCommuteDTO = modelMapper.map(lastCommute, CommuteDTO.class);

        log.info("lastCommuteNo : " + lastCommuteDTO.getCommuteNo());

        if (lastCommuteDTO != null) {
            return lastCommuteDTO;
        } else {
            return null;
        }
    }

}
