package com.insider.login.other.announce.service;

import com.insider.login.config.YmlConfig;
import com.insider.login.other.announce.dto.AncFileDTO;
import com.insider.login.other.announce.dto.AnnounceDTO;
import com.insider.login.other.announce.entity.AncFile;
import com.insider.login.other.announce.entity.Announce;
import com.insider.login.other.announce.repository.AnnounceFileRepository;
import com.insider.login.other.announce.repository.AnnounceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.FileUtils.delete;
import static org.apache.commons.io.FileUtils.getFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnnounceService {

    private final AnnounceRepository announceRepository;

    private final AnnounceFileRepository announceFileRepository;

    private final ModelMapper modelMapper;

    private final YmlConfig ymlConfig;


    /** 공지사항 전체 조회 + 페이징 */
    public Page<AnnounceDTO> selectAncList(Pageable pageable){

        Page<Announce> announces;

        announces = announceRepository.findAll(pageable);

        if (announces != null) {
            return announces.map(announce -> modelMapper.map(announce, AnnounceDTO.class));
        } else {
            return Page.empty();
        }

    }



    /** 공지사항 상세 조회 */
    public Announce findAncWithFile(int ancNo) {
        Announce announce = announceRepository.findAnnounceWithFiles(ancNo);
        if (announce != null) {
            announce.increaseHits(); // 조회수 증가 비즈니스 로직 메서드
            announceRepository.save(announce);
        }
        return announce;
    }

    /** 파일과 공지사항 모두 있을 때 insert */
    public Map<String, Object> insertAncWithFile(AnnounceDTO ancDTO, List<MultipartFile> files) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 공지사항 저장
            Announce announce = modelMapper.map(ancDTO, Announce.class);
            Announce savedAnnounce = announceRepository.save(announce);

            // 저장된 공지사항의 ancNo 가져오기
            int ancNo = savedAnnounce.getAncNo();


            // files가 null이 아닌 경우에만 파일 저장 수행
            if (files != null) {
                for (MultipartFile file : files) {
                    // 파일 저장
                    String fileName = file.getOriginalFilename();
                    String fileType = file.getContentType();
                    String uploadDirectory = ymlConfig.getDirectory();
                    String filePath = uploadDirectory + "\\" + fileName;   // 파일을 저장할 경로 지정

                    File newFile = new File(filePath);
                    file.transferTo(newFile);

                    // 파일 정보를 AncFileDTO로 생성
                    AncFileDTO ancFileDTO = new AncFileDTO();
                    ancFileDTO.setFileName(fileName);
                    ancFileDTO.setFileType(fileType);
                    ancFileDTO.setFilePath(filePath);
                    ancFileDTO.setAncNo(ancNo);

                    // AncFileDTO를 엔티티로 변환하여 저장
                    AncFile ancFile = modelMapper.map(ancFileDTO, AncFile.class);
                    announceFileRepository.save(ancFile);
                }
            }

            result.put("result", true);
        } catch (Exception e) {
            log.error("Error while inserting Announce with Files: " + e.getMessage());
            result.put("result", false);
        }

        return result;
    }

    /** 파일 없을 때 insert */
    public Map<String, Object> insertAnc(AnnounceDTO announceDTO) {

        Map<String, Object> result = new HashMap<>();

        try {
            Announce announce = modelMapper.map(announceDTO, Announce.class);
            announceRepository.save(announce);

            result.put("result", true);
        } catch (Exception e) {

            log.error(e.getMessage());
            result.put("result", false);
        }
        return result;

    }

    /** 공지사항 수정 */
    public Map<String, Object> updateAnc(int ancNo, AnnounceDTO updateAncDTO) {

        Map<String, Object> result = new HashMap<>();

        Announce announce = announceRepository.findByAncNo(ancNo);

        if (announce != null) {

            AnnounceDTO announceDTO = modelMapper.map(announce, AnnounceDTO.class);

            announceDTO.setAncTitle(updateAncDTO.getAncTitle());
            announceDTO.setAncContent(updateAncDTO.getAncContent());

            Announce updateAnc = modelMapper.map(announceDTO, Announce.class);
            announceRepository.save(updateAnc);

            result.put("result", true);
        } else {
            result.put("result", false);

        }
        return result;
    }

    /** 공지사항 삭제 */
    @Transactional
    public Map<String, Object> deleteAncAndFile(int ancNo) {

        Map<String, Object> result = new HashMap<>();
        result.put("result", true);

        // 공지사항과 파일을 동시에 삭제 : 동일한 트랜잭션 내 로직
        Announce announce = announceRepository.findByAncNo(ancNo);
        if (announce != null) {

            List<AncFile> ancFiles = announceFileRepository.findByAncNo(ancNo);
            if (!ancFiles.isEmpty()) {
                announceFileRepository.deleteAll(ancFiles);
            }
            announceRepository.delete(announce);
        }

        return result;
    }

    public List<AncFile> selectFileList(int ancNo) {

        List<AncFile> fileList = announceFileRepository.findByAncNo(ancNo);


        return fileList;
    }
}
