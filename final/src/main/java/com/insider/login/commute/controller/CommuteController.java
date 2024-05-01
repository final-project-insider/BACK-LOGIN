//package com.insider.login.commute.controller;
//
//import com.insider.login.common.ResponseMessage;
//import com.insider.login.commute.dto.CommuteDTO;
//import com.insider.login.commute.dto.CorrectionDTO;
//import com.insider.login.commute.service.CommuteService;
//import com.insider.login.member.entity.Member;
//import com.sun.net.httpserver.Headers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.nio.charset.Charset;
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.temporal.TemporalAdjusters;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class CommuteController {
//
//    private final CommuteService commuteService;
//
//    @Autowired
//    public CommuteController(CommuteService commuteService) {
//        this.commuteService = commuteService;
//    }
//
//    /** 출퇴근 내역 조회 (부서별, 회원별) */
////    @GetMapping("/commutes")
////    public ResponseEntity<ResponseMessage> selectCommuteList(@RequestParam(value = "target") String target,
////                                                             @RequestParam(value = "targetValue") String targetValue,
////                                                             @RequestParam(value = "date") LocalDate date) {
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
////
////        List<CommuteDTO> commuteList;
////
////        /** 멤버별 출퇴근 내역 조회시 주간 조회에 사용할 변수들 */
////        LocalDate startWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
////        LocalDate endWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
////
////        /** 전체 출퇴근 내역 조회시 월간 조회에 사용할 변수들 */
////        LocalDate startDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
////        LocalDate endDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
////
////        if("depart".equals(target)) {
////            int departNo = Integer.parseInt(targetValue);
////            commuteList = commuteService.selectCommuteListByDepartNo(departNo, startDayOfMonth, endDayOfMonth);   // 부서별
////
////        } else if("member".equals(target)) {
////            int memberId = Integer.parseInt(targetValue);
////            commuteList = commuteService.selectCommuteListByMemberId(memberId, startWeek, endWeek);     // 회원별
////
////        } else {
////            Map<String, Object> error = new HashMap<>();
////            error.put("error", "잘못된 요청");
////            ResponseMessage responseMessage = new ResponseMessage(400, "잘못된 요청", error);
////
////            return new ResponseEntity<>(responseMessage, headers, HttpStatus.BAD_REQUEST);
////        }
////
////        Map<String, Object> result = new HashMap<>();
////        result.put("result", commuteList);
////        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", result);
////
////        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
////    }
//
//    /** 출퇴근 시간 정정 내역 조회 (전체, 멤버별) */
////    public ResponseEntity<ResponseMessage> selectRequestForCorrectList(@RequestParam(value = "memberId") Integer memberId,
////                                                                       @RequestParam(value = "date") LocalDate date) {
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
////
////        List<CorrectionDTO> correctionList;
////
////        LocalDate startDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
////        LocalDate endDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
////
////        if(memberId != null) {
////
////            correctionList = commuteService.selectReqeustForCorrectList(startDayOfMonth, endDayOfMonth);
////
////        } else {
////
////            correctionList = commuteService.selectRequestForCorrectListByMemberId(memberId, startDayOfMonth, endDayOfMonth);
////        }
////
////        Map<String, Object> result = new HashMap<>();
////        result.put("result", correctionList);
////        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", result);
////
////        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
////    }
//
//
//}
