//package com.insider.login.commute.service;
//
//import com.insider.login.common.ResponseMessage;
//import com.insider.login.commute.dto.CommuteDTO;
//import com.insider.login.commute.dto.CorrectionDTO;
//import com.insider.login.commute.dto.UpdateProcessForCorrectionDTO;
//import com.insider.login.commute.dto.UpdateTimeOfCommuteDTO;
//import com.insider.login.commute.entity.Commute;
//import com.insider.login.commute.entity.Correction;
//import com.insider.login.commute.repository.CommuteRepository;
//import com.insider.login.commute.repository.CorrectionRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.coyote.Response;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.ModelMap;
//
//import java.sql.Time;
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class CommuteService {
//
//    private final CommuteRepository commuteRepository;
//    private final CorrectionRepository correctionRepository;
//
//    private final ModelMapper modelMapper;
//
//    public CommuteService(CommuteRepository commuteRepository, ModelMapper modelMapper, CorrectionRepository correctionRepository) {
//        this.commuteRepository = commuteRepository;
//        this.modelMapper = modelMapper;
//        this.correctionRepository = correctionRepository;
//    }
//
//    @Transactional
//    public void insertTimeOfCommute(CommuteDTO newCommute) {
//
//        log.info("[CommuteService] insertTimeOfCommute");
//        log.info("[CommuteService] CommuteDTO : " + newCommute);
//
//        /* 방법 1 */
//
////        try {
////            Commute startWork = new Commute(
////                    newCommute.getMemberId(),
////                    newCommute.getWorkingDate(),
////                    newCommute.getStartWork(),
////                    newCommute.getEndWork(),
////                    newCommute.getWorkingStatus(),
////                    newCommute.getTotalWorkingHours()
////            );
////
////            log.info("[CommuteService] startwork :", startWork);
////
////            commuteRepository.save(startWork);
////
////        } catch (Exception e) {
////            log.info("[insertCommute] Exception");
////        }
//
//        /* 방법 2 */
//
//        commuteRepository.save(modelMapper.map(newCommute, Commute.class));
//
//        log.info("[CommuteService] insertTimeOfCommute End ===========");
//    }
//
//
//    @Transactional
//    public Map<String, Object> updateTimeOfCommuteByCommuteNo(UpdateTimeOfCommuteDTO updateTimeOfCommute) {
//
//        Map<String, Object> result = new HashMap<>();
//
//        int commuteNo = updateTimeOfCommute.getCommuteNo();
//        Commute commute = commuteRepository.findByCommuteNo(commuteNo);
//
//        log.info("update 전 : " , commute);
//
//        if(commute != null) {
//            CommuteDTO commuteDTO = modelMapper.map(commute, CommuteDTO.class);
//
//            commuteDTO.setEndWork(updateTimeOfCommute.getEndWork());
//            commuteDTO.setWorkingStatus(updateTimeOfCommute.getWorkingStatus());
//            commuteDTO.setTotalWorkingHours(updateTimeOfCommute.getTotalWorkingHours());
//
//            Commute updateCommute = modelMapper.map(commuteDTO, Commute.class);
//            commuteRepository.save(updateCommute);
//
//            log.info("update 후 : ", commuteRepository.save(updateCommute));
//
//            result.put("result", true);
//
//        } else {
//            result.put("result", false);
//        }
//        return result;
//    }
//
//    @Transactional
//    public List<CommuteDTO> selectCommuteListByDepartNo(int departNo, LocalDate startDayOfMonth, LocalDate endDayOfMonth) {
//
//        log.info("[CommuteService] selectCommuteListByDepartNo");
//        log.info("[CommuteService] departNo : ", departNo);
//
//        List<Commute> commuteListByDepartNo = commuteRepository.findByMemberDepartmentDepartNoAndWorkingDateBetween(departNo, startDayOfMonth, endDayOfMonth);
//
//        List<CommuteDTO> commuteDTOList = commuteListByDepartNo.stream()
//                                            .map(commute -> modelMapper.map(commute, CommuteDTO.class))
//                                            .collect(Collectors.toList());
//
//        log.info("[CommuteService] selectCommuteListByDepartNo End ==================");
//
//        return commuteDTOList;
//    }
//
//    @Transactional
//    public List<CommuteDTO> selectCommuteListByMemberId(int memberId, LocalDate startWeek, LocalDate endWeek) {
//
//        log.info("[CommuteService] selectCommuteListByMemberId");
//        log.info("[CommuteService] memberId : " , memberId);
//
////        List<Commute> commuteListByMemberId = commuteRepository.findByMemberId(memberId);
//        List<Commute> commuteListByMemberId = commuteRepository.findByMemberIdAndWorkingDateBetween(memberId, startWeek, endWeek);
//
//        List<CommuteDTO> commuteDTOList = commuteListByMemberId.stream()
//                                            .map(commute -> modelMapper.map(commute, CommuteDTO.class))
//                                            .collect(Collectors.toList());
//
//        log.info("[CommuteService] selectCommuteListByMemberId End ================");
//
//        return commuteDTOList;
//    }
//
//    @Transactional
//    public void insertRequestForCorrect(CorrectionDTO newCorrection) {
//
//        log.info("[CommuteService] insertRequestForCorrect");
//        log.info("[CommuteService] newCorrection : ", newCorrection);
//
//        /* 방법1 */
//
////        try {
////            Correction correctionTimeOfCommute = new Correction(
////                    newCorrection.getCorrNo(),
////                    newCorrection.getCommuteNo(),
////                    newCorrection.getReqStartWork(),
////                    newCorrection.getReqEndWork(),
////                    newCorrection.getReasonForCorr(),
////                    newCorrection.getCorrRegistrationDate(),
////                    newCorrection.getCorrStatus(),
////                    newCorrection.getReasonForRejection(),
////                    newCorrection.getCorrProcessingDate()
////            );
////
////            log.info("[CommuteService] insertRequestForCorrect correctionTimeOfCommute : ", correctionTimeOfCommute);
////
////            correctionRepository.save(correctionTimeOfCommute);
////
////        } catch (Exception e) {
////            log.info("[CommuteService] insertRequestForCorrect Error");
////        }
//
//        /* 방법 2 */
//        correctionRepository.save(modelMapper.map(newCorrection, Correction.class));
//
//        log.info("[CommuteService] insertRequestForCorrect End ================");
//    }
//
//    @Transactional
//    public Map<String, Object> updateProcessForCorrectByCorrNo(UpdateProcessForCorrectionDTO updateProcessForCorrection) {
//
//        log.info("[CommuteService] updateProcessForCorrectByCorrNo");
//        log.info("[CommuteService] updateProcessForCorrection : ", updateProcessForCorrection);
//
//        Map<String, Object> result = new HashMap<>();
//
//        int corrNo = updateProcessForCorrection.getCorrNo();
//        Correction correction = correctionRepository.findByCorrNo(corrNo);
//
//        int commuteNo = correction.getCommuteNo();
//        Commute commute = commuteRepository.findByCommuteNo(commuteNo);
//
//        if(correction != null && commute != null) {
//            /** 1. 출퇴근 정정 요청 내역 update */
//            CorrectionDTO correctionDTO = modelMapper.map(correction , CorrectionDTO.class);
//
//            correctionDTO.setCorrStatus(updateProcessForCorrection.getCorrStatus());
//            correctionDTO.setReasonForRejection(updateProcessForCorrection.getReasonForRejection());
//            correctionDTO.setReasonForRejection(updateProcessForCorrection.getReasonForRejection());       // 승인되어 null 이어도 통과!
//            correctionDTO.setCorrProcessingDate(updateProcessForCorrection.getCorrProcessingDate());
//
//            Correction updateCorrection = modelMapper.map(correctionDTO, Correction.class);
//            correctionRepository.save(updateCorrection);
//
//            log.info("[CommuteService] Correction update 후 ");
//
//            /** 2. 출퇴근 내역 update */
//            CommuteDTO commuteDTO = modelMapper.map(commute, CommuteDTO.class);
//
//            System.out.println( correction.getReqStartWork());
//            System.out.println( correction.getReqEndWork());
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//
//            /** 2-1. 출근, 퇴근 시간 정정 */
//            if(correction.getReqStartWork() != null && correction.getReqEndWork() != null) {
//                LocalTime updateStartWork = LocalTime.parse(correction.getReqStartWork(), formatter);
//                LocalTime updateEndWork = LocalTime.parse(correction.getReqEndWork(), formatter);
//
//                Duration workingDuration = Duration.between(updateStartWork, updateEndWork).minusHours(1);
//                int totalWorkingHours = (int) workingDuration.toMinutes();
//
//                commuteDTO.setStartWork(updateStartWork);
//                commuteDTO.setEndWork(updateEndWork);
//                commuteDTO.setWorkingStatus("퇴근");
//                commuteDTO.setTotalWorkingHours(totalWorkingHours);
//
//            /** 2-2. 출근 시간만 정정 */
//            } else if(correction.getReqStartWork() != null) {
//                LocalTime updateStartWork = LocalTime.parse(correction.getReqStartWork(), formatter);
//                LocalTime originalEndWork = commute.getEndWork();
//
//                Duration workingDuration = Duration.between(updateStartWork, originalEndWork).minusHours(1);
//                int totalWorkingHours = (int) workingDuration.toMinutes();
//
//                commuteDTO.setStartWork(updateStartWork);
//                commuteDTO.setTotalWorkingHours(totalWorkingHours);
//
//                /** 2-2. 퇴근 시간만 정정 */
//            } else if(correction.getReqEndWork() != null) {
//                LocalTime originalStartWork = commute.getStartWork();
//                LocalTime updateEndWork = LocalTime.parse(correction.getReqEndWork(), formatter);
//
//                Duration workingDuration = Duration.between(originalStartWork, updateEndWork).minusHours(1);
//                int totalWorkingHours = (int) workingDuration.toMinutes();
//
//                commuteDTO.setEndWork(updateEndWork);
//                commuteDTO.setTotalWorkingHours(totalWorkingHours);
//
//                /** 2-3. 출퇴근 시간 모두 null */
//            } else {
//                System.out.println("출퇴근 시간 모두 null !!!!");
//            }
//
//            Commute updateCommute = modelMapper.map(commuteDTO, Commute.class);
//
//            commuteRepository.save(updateCommute);
//
//            log.info("[CommuteService] Commute update 후 ");
//
//            result.put("result", true);
//
//        } else {
//            result.put("result", false);
//
//            log.info("[CommuteService] Error !!! ");
//        }
//
//        log.info("[CommuteService] updateProcessForCorrectByCorrNo End ================");
//
//        return result;
//    }
//
//
////    @Transactional
////    public List<CorrectionDTO> selectReqeustForCorrectList(LocalDate startDayOfMonth, LocalDate endDayOfMonth) {
////        log.info("[CommuteService] selectReqeustForCorrectList");
////
////        List<Correction> correctionList = correctionRepository.findAllBetween(startDayOfMonth, endDayOfMonth);
////
////        List<CorrectionDTO> correctionDTOList = correctionList.stream()
////                                                    .map(correction -> modelMapper.map(correction, CorrectionDTO.class))
////                                                    .collect(Collectors.toList());
////        return correctionDTOList;
////    }
//
////    @Transactional
////    public List<CorrectionDTO> selectRequestForCorrectListByMemberId(int memberId, LocalDate startDayOfMonth, LocalDate endDayOfMonth) {
////        log.info("[CommuteService] selectRequestForCorrectListByMemberId");
////        log.info("[CommuteService] memberId : ", memberId);
////
////        return null;
////    }
//}
