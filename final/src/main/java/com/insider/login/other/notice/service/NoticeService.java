package com.insider.login.other.notice.service;

import com.insider.login.other.notice.dto.NoticeDTO;
import com.insider.login.other.notice.entity.Notice;
import com.insider.login.other.notice.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    public NoticeService(NoticeRepository noticeRepository,
                         ModelMapper modelMapper) {
        this.noticeRepository = noticeRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Map<String, Object> insertNewNotice(NoticeDTO newNotice) {
        /** 새로운 알림 수신 (등록) */
        log.info("[NoticeService] insertNewNotice");

        noticeRepository.save(modelMapper.map(newNotice, Notice.class));

        Map<String, Object> result = new HashMap<>();
        result.put("result", true);

        return result;
    }

    @Transactional
    public Map<String, Object> selectNoticeListByMemberId(int memberId) {
        /** 알림 조회 */
        log.info("[NoticeService] selectNoticeList");

        List<Notice> findNoticeListByMemberId = noticeRepository.findByMemberId(memberId);

        List<NoticeDTO> noticeDTOList = findNoticeListByMemberId.stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());

        log.info("[NoticeService] selectNoticeListByMemberId End ================");
        Map<String, Object> result = new HashMap<>();
        result.put("result", noticeDTOList);

        return result;
    }

    @Transactional
    public Map<String, Object> deleteNoticeByNoticeNo(int noticeNo) {
        /** 알림 선택 삭제 */
        log.info("[NoticeService] deleteNoticeByNoticeNo");

        Notice findNoticeByNoticeNo = noticeRepository.findByNoticeNo(noticeNo);

        noticeRepository.delete(findNoticeByNoticeNo);

        Map<String, Object> result = new HashMap<>();
        result.put("result", true);

        return result;
    }

    @Transactional
    public Map<String, Object> deleteNoticeListByMemberId(int memberId) {
        /**알림 전체 삭제 */
        log.info("[NoticeService] deleteNoticeListByMemberId");

        List<Notice> findNoticeListByMemberId = noticeRepository.findByMemberId(memberId);

        noticeRepository.deleteAll(findNoticeListByMemberId);

        Map<String, Object> result = new HashMap<>();
        result.put("result", true);

        return result;
    }
}
