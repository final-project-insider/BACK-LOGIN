package com.insider.login.approval.controller;

import com.insider.login.approval.dto.ApprovalDTO;
import com.insider.login.approval.dto.AttachmentDTO;
import com.insider.login.approval.service.ApprovalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/approvals")
public class ApprovalController {


    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    @Value("${file.file-dir}")
    private String FILE_DIR;


    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService){
        this.approvalService = approvalService;
    }


    //전자결재 기안 화면으로
    @GetMapping("/")
    public void goInsertApproval() {

    }

    //전자결재 기안
  /*  @PostMapping("/")
    public ModelAndView insertApproval(@RequestParam ApprovalDTO approvalDTO,
                                       @RequestParam(value = "files", required = false) MultipartFile[] files,
                                       ModelAndView mv) {

        if (files.length > 0) {
            String rootLocation = UPLOAD_DIR + FILE_DIR;

            String fileUploadDirectory = rootLocation + "/approval";

            File directory = new File(fileUploadDirectory);

            //파일 저장경로가 존재하지 않은 경우 디렉토리를 생성한다.
            if (!directory.exists()) {
                directory.mkdirs();
            }

            //업로드 파일 하나하나에 대한 정보를 담을 리스트
            List<Map<String, String>> fileList = new ArrayList<>();

            try {
                for (MultipartFile File : files) {
                    //파일 하나씩 꺼내기

                    if(File.getSize() > 0){ //파일이 있다면
                        List<MultipartFile> FileList = new ArrayList<>();

                        String originFileName = File.getOriginalFilename();
                        System.out.println("originFileName : " + originFileName);

                        String ext= originFileName.substring(originFileName.lastIndexOf("."));
                        String savedFileName = UUID.randomUUID().toString().replace("-", "") + ext;

                        File.transferTo(new File(fileUploadDirectory + "/" + savedFileName));
                        //저장 경로에 파일 저장

                        Map<String, String> fileMap = new HashMap<>();
                        fileMap.put("originFileName", originFileName);
                        fileMap.put("savedFileName", savedFileName);
                        fileMap.put("savePath", fileUploadDirectory);

                        fileList.add(fileMap);
                        //파일 하나하나에 정보를 저장하고 그걸 Map에 담아 각 Map을 List에 담기
                        //list({map}, {map}, {map},...)
                    }
                }

                List<AttachmentDTO> list = approvalDTO.getAttachment();

                for(int i = 0; i < fileList.size(); i++){
                    //list에 들어간 map 을 하나씩 꺼내기
                    Map<String, String> file = fileList.get(i);
                    System.out.println("fileList.get(" + i + ")번째 : " + fileList.get(i));

                    AttachmentDTO fileInfo = new AttachmentDTO();

                    fileInfo.setFileOriname(file.get("originFileName"));
                    fileInfo.setFileSavename(file.get("savedFileName"));
                    fileInfo.setFileSavepath(file.get("savePath"));
                    //꺼낸 map의 key로 value를 불러와 DTO에 넣어주기


                    list.add(fileInfo);
                    //정보를 담은 DTO를 list에 넣기
                }
                System.out.println("파일 list: " + list);

                approvalDTO.setAttachment(list);
                //DTO를 담은 list를 전체 Approval DTO에 담기


                *//* 현재 로그인중인 사번 가져오기 *//*
                int memberId = 2024001001;

                approvalDTO.setMemberId(memberId);              //memberId 저장
                approvalDTO.setApprovalDate(now().toString());  //date 저장

                String thisYear = approvalDTO.getApprovalDate().substring(0,3); //연도만 빼오기
                int appCount = approvalService.findCountApproval(thisYear);     //현재 연도의 전자결재 수 가져오기

                approvalDTO.setApprovalNo(thisYear + "-" + approvalDTO.getFormNo() + String.format("%05d", (appCount + 1)));
                //전자결재No = 현재연도-양식번호0000순번  ex) 2024-con00001

                approvalDTO.setApprovalStatus("처리중");           //최초 상태 저장

                System.out.println("controller 들어온 데이터 : " + approvalDTO);

                approvalService.insertApproval(approvalDTO);
                System.out.println("서비스 다녀왔대요");


            } catch (IllegalStateException | IOException e) {
                //무슨 에러가 발생해도 넣어준 파일 지워주기

                e.printStackTrace();
            }

        } else {

        }


        return mv;
    }*/


}
