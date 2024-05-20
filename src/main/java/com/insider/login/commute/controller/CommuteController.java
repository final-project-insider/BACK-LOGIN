package com.insider.login.commute.controller;

import com.insider.login.common.CommonController;
import com.insider.login.common.ResponseMessage;
import com.insider.login.commute.dto.CommuteDTO;
import com.insider.login.commute.dto.CorrectionDTO;
import com.insider.login.commute.dto.UpdateProcessForCorrectionDTO;
import com.insider.login.commute.dto.UpdateTimeOfCommuteDTO;
import com.insider.login.commute.entity.Commute;
import com.insider.login.commute.service.CommuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommuteController {

    private final CommuteService commuteService;

    @Autowired
    public CommuteController(CommuteService commuteService) {
        this.commuteService = commuteService;
    }

    /** 출근 시간 등록 */
    @PostMapping("/commutes")
    public ResponseEntity<ResponseMessage> insertTimeOfCommute(@RequestBody CommuteDTO newCommute) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "등록 성공", commuteService.insertTimeOfCommute(newCommute)));
    }

    /** 퇴근 시간 등록 (update) */
    @PutMapping("/commutes/{commuteNo}")
    public ResponseEntity<ResponseMessage> updateTimeOfCommute(@PathVariable("commuteNo") int commuteNo,
                                                                @RequestBody UpdateTimeOfCommuteDTO updateCommute) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "추가 등록 성공", commuteService.updateTimeOfCommuteByCommuteNo(commuteNo, updateCommute)));
    }

    /** 출퇴근 내역 조회 (부서별, 회원별) */
    @GetMapping("/commutes")
    public ResponseEntity<ResponseMessage> selectCommuteList(@RequestParam(value = "target") String target,
                                                             @RequestParam(value = "targetValue") String targetValue,
                                                             @RequestParam(value = "date") LocalDate date) {

        System.out.println("date : " + date);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<CommuteDTO> commuteList;

        /** 멤버별 출퇴근 내역 조회시 주간 조회에 사용할 변수들 */
        LocalDate startWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        /** 전체 출퇴근 내역 조회시 월간 조회에 사용할 변수들 */
        LocalDate startDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        if("depart".equals(target)) {
            int departNo = Integer.parseInt(targetValue);
            commuteList = commuteService.selectCommuteListByDepartNo(departNo, startDayOfMonth, endDayOfMonth);   // 부서별

        } else if("member".equals(target)) {
            int memberId = Integer.parseInt(targetValue);
            commuteList = commuteService.selectCommuteListByMemberId(memberId, startWeek, endWeek);     // 회원별

        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "잘못된 요청");
            ResponseMessage responseMessage = new ResponseMessage(400, "잘못된 요청", error);

            return new ResponseEntity<>(responseMessage, headers, HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("result", commuteList);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", result);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    /** 출퇴근 시간 정정 요청 등록 */
    @PostMapping("/corrections")
    public ResponseEntity<ResponseMessage> insertRequestForCorrect(@RequestBody CorrectionDTO newCorrection) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "등록 성공", commuteService.insertRequestForCorrect(newCorrection)));
    }

    /** 출퇴근 시간 정정 처리 등록 (update) */
    @PutMapping("/corrections/{corrNo}")
    public ResponseEntity<ResponseMessage> updateProcessForCorrectByCorrNo(@PathVariable("corrNo") int corrNo,
                                                                           @RequestBody UpdateProcessForCorrectionDTO updateCorrection) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "정정 처리 성공",commuteService.updateProcessForCorrectByCorrNo(corrNo, updateCorrection)));
    }

    /** 출퇴근 시간 정정 내역 조회 (전체, 멤버별) */
    @GetMapping("/corrections")
    public ResponseEntity<ResponseMessage> selectRequestForCorrectList(@RequestParam(value = "memberId", required = false) Integer memberId,
                                                                       @RequestParam(value = "date") LocalDate date,
                                                                       @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                       @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                                       @RequestParam(value = "sort", defaultValue = "sort", required = false) String sort,
                                                                       @RequestParam(value = "direction", defaultValue = "DESC", required = false) String direction) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Pageable pageable = CommonController.getPageable(page, size, sort, direction);
        Page<CorrectionDTO> correctionList;

        LocalDate startDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        if(memberId != null) {

            correctionList = commuteService.selectRequestForCorrectListByMemberId(memberId, startDayOfMonth, endDayOfMonth, pageable);

        } else {

            correctionList = commuteService.selectRequestForCorrectList(startDayOfMonth, endDayOfMonth, pageable);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", correctionList.getContent());
        responseMap.put("currentPage", correctionList.getNumber());
        responseMap.put("totalItems", correctionList.getTotalElements());
        responseMap.put("totalPages", correctionList.getTotalPages());

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @GetMapping("/corrections/{corrNo}")
    public ResponseEntity<ResponseMessage> selectRequestForCorrectByCorrNo(@PathVariable("corrNo") int corrNo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        CorrectionDTO correction = commuteService.selectRequestForCorrectByCorrNo(corrNo);

        Map<String, Object> result = new HashMap<>();
        result.put("result", correction);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", result);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @GetMapping("/commutes/{memberId}")
    public int searchLastCommuteNoByMemberId(@PathVariable("memberId") int memberId) {

        CommuteDTO lastCommute = commuteService.searchLastCommuteNoByMemberId(memberId);

        return lastCommute.getCommuteNo();
    }

}
