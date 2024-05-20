package com.insider.login.approval.service;

import com.insider.login.approval.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
//@AutoConfigureMockMvc
public class ApprovalServiceTest {

    @Autowired
    private ApprovalService approvalService;


    @DisplayName("양식 목록 조회")
    @Test
    void testSelectFormList(){
        //given
        //when
        List<FormDTO> formDTOList = approvalService.selectFormList();

        //then
        Assertions.assertNotNull(formDTOList);
    }

    @DisplayName("양식 조회")
    @Test
    void testSelectForm(){
        //given
        String formNo = "abs";

        //when
        FormDTO formDTO = approvalService.selectForm(formNo);

        //then
        Assertions.assertNotNull(formDTO);
        System.out.println(formDTO);
    }


    //전자결재 기안 테스트
    @Test
    void testInsertApproval(){
        //given
        ApprovalDTO approvalDTO = new ApprovalDTO();

        List<ApproverDTO> approverList  = new ArrayList<>();
        List<ReferencerDTO> referencerList = new ArrayList<>();
        List<MultipartFile> files = new ArrayList<>();

        //결재번호 : 현재 날짜(연도) - form번호 0000순번(해당 연도-form의 순번)
        //결재자번호 : 결재번호_apr00순번
        //참조자번호 : 결재번호_ref00순번
        //첨부파일번호: 결재번호_f00순번
        ApproverDTO approverDTO1 = new ApproverDTO("2024-exp00003_apr001", "2024-exp00003", 1, "대기", null, 240401004);
        ApproverDTO approverDTO2 = new ApproverDTO("2024-exp00003_apr002", "2024-exp00003", 2, "대기", null, 2024001003);

        ReferencerDTO referencerDTO1 = new ReferencerDTO("2024-exp00003_ref001", "2024-exp00003", 2024001001, 1);
        ReferencerDTO referencerDTO2 = new ReferencerDTO("2024-exp00003_ref002", "2024-exp00003", 241811, 2);

        approverList.add(approverDTO1);
        approverList.add(approverDTO2);

        referencerList.add(referencerDTO1);
        referencerList.add(referencerDTO2);

        approvalDTO.setApprovalNo("2024-exp00003");
        approvalDTO.setMemberId(2024001002);
        approvalDTO.setApprovalTitle("경조금 지급 신청합니다.");
        approvalDTO.setApprovalContent("<form name=\"form\">\n" +
                "\t\t\t\t\t\t\t<div name=\"wholeForm\"id=\"wholeForm\">\n" +
                "\t\t\t\t\t\t\t<div name=\"titleform\" id=\"titleform\">\n" +
                "\t\t\t\t\t\t\t  \n" +
                "\t\t\t\t\t\t\t\t<input type=\"text\" name=\"title\" id=\"title\" placeholder=\"제목\">\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t<table name=\"sideTable\" id=\"sideTable\">\n" +
                "\t\t\t\t\t\t\t  <tr>\n" +
                "\t\t\t\t\t\t\t\t<th>금액</th>\n" +
                "\t\t\t\t\t\t\t\t<td>100000</td>\n" +
                "\t\t\t\t\t\t\t  </tr>\n" +
                "\t\t\t\t\t\t\t  <tr>\n" +
                "\t\t\t\t\t\t\t\t<th>지출사유</th>\n" +
                "\t\t\t\t\t\t\t\t<td>사무용품 구입 및 출장 교통비</td>\n" +
                "\t\t\t\t\t\t\t  </tr>\n" +
                "\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t<table>\n" +
                "\t\t\t\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t\t\t\t<th>일자</th>\n" +
                "\t\t\t\t\t\t\t\t\t<th>분류</th>\n" +
                "\t\t\t\t\t\t\t\t\t<th name=\"useDetail\" id=\"useDetail\">사용내역</th>\n" +
                "\t\t\t\t\t\t\t\t\t<th name=\"usePrice\" id=\"usePrice\">금액</th>\n" +
                "\t\t\t\t\t\t\t\t\t<th>비고</th>\n" +
                "\t\t\t\t\t\t\t\t  </tr>\n" +
                "\t\t\t\t\t\t\t\t  <tr >\n" +
                "\t\t\t\t\t\t\t\t\t<td name=\"exp_date\" class=\"exp_date\">2024-05-07</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td>물품구입비</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td>필기구 외 물품 구입</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td>20000</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t  </tr>\n" +
                "\t\t\t\t\t\t\t\t  <tr >\n" +
                "\t\t\t\t\t\t\t\t\t<td name=\"exp_date\" class=\"exp_date\">2024-05-07</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td>교통비</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td>출장 교통비</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td>80000</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td  name=\"exp_date\" class=\"exp_date\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td  name=\"exp_date\" class=\"exp_date\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t  <th colspan=\"3\">\n" +
                "\t\t\t\t\t\t\t\t\t합계\n" +
                "\t\t\t\t\t\t\t\t  </th>\n" +
                "\t\t\t\t\t\t\t\t  <td>100000</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t   </table>\n" +
                "\t\t\t\t\t\t\t </div>\n" +
                "\t\t\t\t\t\t  <div name=\"date\" id=\"date\">\n" +
                "\t\t\t\t\t\t\t<div>2024-05-07</div>\n" +
                "\t\t\t\t\t\t   </div>\n" +
                "\t\t\t\t\t\t</form>");
        approvalDTO.setApprovalDate("2024-05-07 15:13:13");
        approvalDTO.setApprovalStatus("처리 중");
        approvalDTO.setFormNo("exp");


            //파일 처리
            //테스트용 PDF 파일 생성
            MultipartFile pdfFile = null;
            byte[] pdfFileContent = "Test PDF Content".getBytes();
            pdfFile = new MockMultipartFile("testfile.pdf", "testfile.pdf", "application/pdf", pdfFileContent);

            //테스트용 이미지 파일 생성
            MultipartFile imgFile = null;
            byte[] imageFileContent = "Test Image Content".getBytes();
            imgFile = new MockMultipartFile("testimage.jpg", "testimage.jpg", "image/jpeg", imageFileContent);

            files.add(pdfFile);
            files.add(imgFile);


            List<AttachmentDTO> attachmentList = new ArrayList<>();
            AttachmentDTO attachmentDTO1 = new AttachmentDTO();
            attachmentDTO1.setFileNo("2024-exp00003_f001");
            attachmentDTO1.setFileOriname(pdfFile.getOriginalFilename());
            attachmentDTO1.setFileSavename(pdfFile.getName());
            attachmentDTO1.setFileSavepath("C:/login/upload");
            attachmentDTO1.setApprovalNo("2024-exp00003");

            AttachmentDTO attachmentDTO2 = new AttachmentDTO();
            attachmentDTO2.setFileNo("2024-exp00003_f002");
            attachmentDTO2.setFileOriname(imgFile.getOriginalFilename());
            attachmentDTO2.setFileSavename(imgFile.getName());
            attachmentDTO2.setFileSavepath("C:/login/upload");
            attachmentDTO2.setApprovalNo("2024-exp00003");

            attachmentList.add(attachmentDTO1);
            attachmentList.add(attachmentDTO2);

            //파일 업로드 서비스에 테스트용 PDF 파일 전달

        approvalDTO.setAttachment(attachmentList);

        approvalDTO.setApprover(approverList);
        approvalDTO.setReferencer(referencerList);

        //when
//        approvalService.insertApproval(approvalDTO);

        //then
        Assertions.assertEquals(approvalService.insertApproval(approvalDTO, files), "성공");

    }

    //전자결재용 부서 목록 조회 테스트
    @DisplayName("부서 목록 조회 테스트")
    @Test
    void testSelectDepartList(){
        List<DepartmentDTO> departmentDTOList = approvalService.selectDepartList();

        Assertions.assertNotNull(departmentDTOList);
        for(int i = 0; i < departmentDTOList.size(); i++){
            System.out.println(departmentDTOList.get(i));
        }
    }

    //전자결재용 부서별 사원 목록 조회 테스트
    @DisplayName("부서별 사원 목록 조회 테스트")
    @Test
    void testSelectMemberList(){
        //given
        int departNo = 1;
        //when
        List<MemberDTO> memberDTOList = approvalService.selectMemberList(departNo);
        //then
        Assertions.assertNotNull(memberDTOList);
        for(int i = 0; i < memberDTOList.size(); i++){
            System.out.println(memberDTOList.get(i));
        }
    }

    //전자결재 상세조회 테스트
    @DisplayName("전자결재 상세조회 테스트")
    @ParameterizedTest
    @CsvSource("2024-abs00006")
    void testSelectApproval(String approvalNo){
        //given

        //when
        ApprovalDTO approvalDTO = approvalService.selectApproval(approvalNo);

        //then
        Assertions.assertNotNull(approvalDTO);
        System.out.println(approvalDTO);
    }


    //전자결재 회수 테스트
    @DisplayName("전자결재 회수 테스트")
    @ParameterizedTest
    @CsvSource("2024-abs00001")
    void testUpdateApproval (String approvalNo){
        //회수

        // when
        ApprovalDTO approvalDTO = approvalService.updateApproval(approvalNo);

        // then
        Assertions.assertEquals(approvalDTO.getApprovalStatus(), "회수");
    }

    @DisplayName("전자결재 결재 테스트")
    @ParameterizedTest
    @CsvSource("2024-con00003_apr002")
    void testUpdateApprover (String approverNo){
        //when
        //결재처리 / 반려
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("approverStatus", "승인");
        statusMap.put("rejectReason","이러한 사유로 반려합니다.");

        ApproverDTO approverDTO = approvalService.updateApprover(approverNo, statusMap);
        System.out.println("***** TEST : approver : " + approverDTO );

        System.out.println("***** approverDTO.getApproverStatus(): "  + approverDTO.getApproverStatus());

        //then
        Assertions.assertEquals(approverDTO.getApproverStatus(), statusMap.get("approverStatus"));
    }


    @DisplayName("전자결재 목록 조회 테스트")
    @Test
    void testSelectApprovalList(){
        //given
        int memberId = 240501629;
        int pageNo = 0;

        Map<String, Object> condition = new HashMap<>();
        condition.put("flag", "given");
        condition.put("offset", 0);
        condition.put("limit", 10);
        condition.put("direction", null);
        condition.put("title", "경조금");

        //when
//        List<ApprovalDTO> approvalList = approvalService.selectApprovalList(memberId, condition);
        Page<ApprovalDTO> approvalDTOPage  = approvalService.selectApprovalList(memberId, condition, pageNo);

//        System.out.println("*****TEST : 목록 size() : " + approvalList.size());

        List<ApprovalDTO> approvalDTOList = approvalDTOPage.getContent();

        for(int i=0; i < approvalDTOList.size(); i++){
            System.out.println(approvalDTOList.get(i).getApprovalDate() + " | "  + approvalDTOList.get(i).getApprovalTitle() + " | " + approvalDTOList.get(i).getApprovalNo() + " | 현재 진행 중 : " + approvalDTOList.get(i).getStandByApprover()) ;
        }

        //then
//        Assertions.assertNotNull(approvalList);
        Assertions.assertNotNull(approvalDTOPage);
//        approvalList.forEach(System.out::println);
    }


    @DisplayName("전자결재 삭제 테스트")
    @ParameterizedTest
    @CsvSource("2024-exp00002")
    void testDeleteApproval(String approvalNo){
        //given
        //when

        boolean isDeleted = approvalService.approvalDelete(approvalNo);

        //then
        Assertions.assertTrue(isDeleted);
    }


}
