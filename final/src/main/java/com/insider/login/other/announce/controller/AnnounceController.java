package com.insider.login.other.announce.controller;

import com.insider.login.common.CommonController;
import com.insider.login.common.ResponseMessage;
import com.insider.login.config.YmlConfig;
import com.insider.login.other.announce.dto.AnnounceDTO;
import com.insider.login.other.announce.entity.AncFile;
import com.insider.login.other.announce.entity.Announce;
import com.insider.login.other.announce.service.AnnounceService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController
@AllArgsConstructor
public class AnnounceController extends FileController {

    private final AnnounceService announceService;

    private final YmlConfig ymlConfig;


    /** 공지사항 전체조회 + 페이징 */
    @GetMapping("/announces")
    public ResponseEntity<ResponseMessage> selectAncList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                         @RequestParam(value = "sort", defaultValue = "sort") String sort,
                                                         @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Pageable pageable = CommonController.getPageable(page, size, sort, direction);

        Page<AnnounceDTO> ancList = announceService.selectAncList(pageable);

        if (ancList.isEmpty()) {
            String errorMessage = "조회된 공지사항이 없습니다.";
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), errorMessage, null);
            return new ResponseEntity<>(responseMessage, headers, HttpStatus.NOT_FOUND);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("ancList", ancList.getContent());
        responseMap.put("currentPage", ancList.getNumber());
        responseMap.put("totalItems", ancList.getTotalElements());
        responseMap.put("totalPages", ancList.getTotalPages());

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

    }

    /** 공지사항 상세 페이지 */
    @GetMapping("/announces/{ancNo}")
    public ResponseEntity<?> selectAncWithFiles(@PathVariable("ancNo") int ancNo) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Announce announce = announceService.findAncWithFile(ancNo);
        List<AncFile> ancFiles = findAncFilesByAnc(ancNo);

        Map<String, Object> result = new HashMap<>();
        result.put("announce", announce);

        // 파일 목록을 저장할 리스트
        List<Map<String, Object>> fileResponses = new ArrayList<>();

        for (AncFile ancFile : ancFiles) {
            String filePath = ancFile.getFilePath();
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

            String fileName = ancFile.getFileName();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

            // 파일의 내용과 헤더를 클라이언트에 반환
            Map<String, Object> fileResponse = new HashMap<>();
            fileResponse.put("fileName", fileName);
            fileResponse.put("fileContent", Base64.getEncoder().encodeToString(fileBytes));
            fileResponses.add(fileResponse);
        }

        // 공지사항과 파일 정보를 함께 반환
        result.put("files", fileResponses);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    public List<AncFile> findAncFilesByAnc(int ancNo) {

        List<AncFile> fileList = announceService.selectFileList(ancNo);


        return fileList;
    }

    /** 공지사항 등록 */
    @PostMapping(value = "/announces", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseMessage> insertAnnounce(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                          @RequestPart("announceDTO") String announceDTOJson) {


        // announceDTOJson을 AnnounceDTO 객체로 변환
        AnnounceDTO announceDTO = FileController.convertJsonToAnnounceDTO(announceDTOJson);

        Map<String, Object> serviceResult;

        if (files != null) {
            // 파일이 있는 경우

            announceDTO.setFilePath(ymlConfig.getDirectory());
            serviceResult = announceService.insertAncWithFile(announceDTO, files);
            serviceResult.put("result", true);
        } else {
            // 파일이 없는 경우
            serviceResult = announceService.insertAnc(announceDTO);
            serviceResult.put("result", true);
        }

        if ((Boolean) serviceResult.get("result")) {
            return ResponseEntity.ok().body(new ResponseMessage(200, "등록 성공", serviceResult));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "등록 실패", serviceResult));
        }
    }

    /** 공지사항 수정 (제목 / 내용 ) */
    @PutMapping("/announces/{ancNo}")
    public ResponseEntity<ResponseMessage> updateAnc(@PathVariable("ancNo") int ancNo,
                                                     @RequestBody AnnounceDTO announceDTO){

        return ResponseEntity.ok().body(new ResponseMessage(200, "수정 성공", announceService.updateAnc(ancNo, announceDTO)));

    }

    /** 공지사항 삭제 / 파일 있으면 같이 삭제 */
    @DeleteMapping("/announces/{ancNo}")
    public ResponseEntity<ResponseMessage> deleteAnc(@PathVariable("ancNo") int ancNo) {
        Map<String, Object> result = announceService.deleteAncAndFile(ancNo);
        if ((boolean) result.get("result")) {
            return ResponseEntity.ok().body(new ResponseMessage(200, "삭제 성공", result));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(500, "삭제 실패", null));
        }
    }






}
