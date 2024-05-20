package com.insider.login.calendar.service;

import com.insider.login.calendar.dto.CalendarDTO;
import com.insider.login.calendar.entity.Calendar;
import com.insider.login.calendar.repository.CalendarRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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


    public List<CalendarDTO> selectCalendar(String department) {

        List<Calendar> calendars = calendarRepository.findBydepartment(department);

        return calendars.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public String insertCalendar(CalendarDTO calendarDTO) {

        try {
            Calendar calendar = modelMapper.map(calendarDTO, Calendar.class);

            calendarRepository.save(calendar);
            return "일정 등록 성공";
        } catch (Exception e) {
            return "일정 등록 실패";
        }

    }

    @Transactional
    public String updateCalendar(CalendarDTO calendarDTO) {
        try {
            // 수정할 일정의 번호로 해당 일정 검색해서 엔티티로 반환받고 dto로 변환
            CalendarDTO tempDTO = modelMapper.map(calendarRepository.findById(calendarDTO.getCalendarNo()), CalendarDTO.class);

            // 수정할 정보
            tempDTO.setCalendarName(calendarDTO.getCalendarName());
            tempDTO.setCalendarStart(calendarDTO.getCalendarStart());
            tempDTO.setCalendarEnd(calendarDTO.getCalendarEnd());
            tempDTO.setColor(calendarDTO.getColor());

            Calendar calendar = modelMapper.map(tempDTO, Calendar.class);

            calendarRepository.save(calendar);
            return "일정 수정 성공";
        } catch (Exception e) {
            return "일정 수정 실패";
        }
    }

    @Transactional
    public String deleteCalendar(int calendarNo) {
        try {
            calendarRepository.deleteById(calendarNo);

            return "일정 삭제 성공";
        } catch (Exception e) {
            return "일정 삭제 실패";
        }
    }

}
