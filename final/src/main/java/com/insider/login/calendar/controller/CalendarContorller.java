package com.insider.login.calendar.controller;

import com.insider.login.calendar.dto.CalendarCriteriaDTO;
import com.insider.login.calendar.dto.CalendarDTO;
import com.insider.login.calendar.service.CalendarService;
import com.insider.login.common.ResponseMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CalendarContorller {

    private final CalendarService calendarService;

    public CalendarContorller(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    /**
     * 일정 조회
     */
    @GetMapping("/calendars")
    public ResponseEntity<ResponseMessage> selectCalendar(@RequestBody CalendarCriteriaDTO criteriaDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<CalendarDTO> calendarList = calendarService.selectCalendar(criteriaDTO);

        if (calendarList.isEmpty()) {
            String errorMessage = "처리 과정에서 문제가 발생했습니다. 다시 시도해주세요.";
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), errorMessage, null);
            return new ResponseEntity<>(responseMessage, headers, HttpStatus.NOT_FOUND);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("calendarList", calendarList);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    /**
     * 일정 등록
     */
    @PostMapping("/calendars")
    public ResponseEntity<String> insertCalendar(@RequestBody CalendarDTO calendarDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

//        calendarDTO.setRegistrantId();
//        TODO: 현재 로그인 중인 관리자 사번 뽑아서 집어넣기 구현예정

        return ResponseEntity.ok().headers(headers).body(calendarService.insertCalendar(calendarDTO));

    }

    /**
     * 일정 수정
     */
    @PutMapping("/calendars")
    public ResponseEntity<String> updateCalendar(@RequestBody CalendarDTO calendarDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

//        calendarDTO.setRegistrantId();
//        TODO: 현재 로그인 중인 관리자 사번 뽑아서 집어넣기 구현예정

        return ResponseEntity.ok().headers(headers).body(calendarService.updateCalendar(calendarDTO));
    }

    /**
     * 일정 삭제
     */
    @DeleteMapping("/calendars/{calendarNo}")
    public ResponseEntity<String> deleteCalendar(@PathVariable("calendarNo") int calendarNo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return ResponseEntity.ok().headers(headers).body(calendarService.deleteCalendar(calendarNo));
    }
}
