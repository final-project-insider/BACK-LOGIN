package com.insider.login.calendar.service;

import com.insider.login.calendar.dto.CalendarCriteriaDTO;
import com.insider.login.calendar.dto.CalendarDTO;
import com.insider.login.calendar.entity.Calendar;
import com.insider.login.calendar.repository.CalendarRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final ModelMapper modelMapper;

    public CalendarService(CalendarRepository calendarRepository, ModelMapper modelMapper) {
        this.calendarRepository = calendarRepository;
        this.modelMapper = modelMapper;
    }


    public List<CalendarDTO> selectCalendar(CalendarCriteriaDTO criteriaDTO) {

        log.info("[일정조회] 시작 ======================================================");
        log.info("[일정조회] 매개변수 확인 =========================================\n" + criteriaDTO);
        List<Calendar> calendars = new ArrayList<>();

        switch (criteriaDTO.getType()) {
            case "monthly":
                calendars = calendarRepository.findByMonth(criteriaDTO.getYear(), criteriaDTO.getMonth(), criteriaDTO.getDepartment());
                break;
            case "weekly":
                calendars = calendarRepository.findByWeek(criteriaDTO.getYear(), criteriaDTO.getMonth(), criteriaDTO.getWeek(), criteriaDTO.getDepartment());
                break;
            case "daily":
                calendars = calendarRepository.findByDay(criteriaDTO.getYear(), criteriaDTO.getMonth(), criteriaDTO.getDay(), criteriaDTO.getDepartment());
                break;
            default:
                return new ArrayList<>();
        }

        return calendars.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public String insertCalendar(CalendarDTO calendarDTO) {
        log.info("[일정등록] 시작 =========================================================");
        int result = 0;

        try {
            Calendar calendar = modelMapper.map(calendarDTO, Calendar.class);
            log.info("[일정등록] 엔티티 확인 ====================================\n" + calendar);

            calendarRepository.save(calendar);
            result = 1;

        } catch (Exception e) {
            log.info("[일정등록] 에러 =====================================================");
            result = 0;
        }

        return (result > 0) ? "일정 등록 성공" : "일정 등록 실패";
    }

    @Transactional
    public String updateCalendar(CalendarDTO calendarDTO) {
        log.info("[일정수정] 시작 =========================================================");
        int result = 0;

        try {
            Optional<Calendar> temp = calendarRepository.findById(calendarDTO.getCalendarNo());
            CalendarDTO tempDTO = modelMapper.map(temp, CalendarDTO.class);
            tempDTO.setCalendarName(calendarDTO.getCalendarName());
            tempDTO.setCalendarStart(calendarDTO.getCalendarStart());
            tempDTO.setCalendarEnd(calendarDTO.getCalendarEnd());
            tempDTO.setColor(calendarDTO.getColor());

            Calendar calendar = modelMapper.map(tempDTO, Calendar.class);
            log.info("[일정수정] 엔티티 확인 ====================================\n" + calendar);

            calendarRepository.save(calendar);
            result = 1;

        } catch (Exception e) {
            log.info("[일정수정] 에러 =====================================================");
            result = 0;
        }

        return (result > 0) ? "일정 수정 성공" : "일정 수정 실패";
    }

    @Transactional
    public String deleteCalendar(int calendarNo) {
        log.info("[일정삭제] 시작 =========================================================");
        int result = 0;

        try {
            calendarRepository.deleteById(calendarNo);
            log.info("[일정삭제] 삭제 확인 ====================================\n" + calendarRepository.findById(calendarNo));
            result = 1;

        } catch (Exception e) {
            log.info("[일정삭제] 에러 =====================================================");
            result = 0;
        }

        return (result > 0) ? "일정 삭제 성공" : "일정 삭제 실패";
    }

}
