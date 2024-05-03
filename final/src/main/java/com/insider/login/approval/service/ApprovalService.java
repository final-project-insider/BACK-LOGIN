package com.insider.login.approval.service;

import com.insider.login.approval.builder.ApprovalBuilder;
import com.insider.login.approval.builder.ApproverBuilder;
import com.insider.login.approval.dto.*;
import com.insider.login.approval.entity.*;
import com.insider.login.approval.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class ApprovalService {

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    @Value("${file.file-dir}")
    private String FILE_DIR;

    private ApprovalRepository approvalRepository;
    private ApproverRepository approverRepository;
    private AttachmentRepository attachmentRepository;
    private ReferencerRepository referencerRepository;

    private ApprovalMemberRepository approvalMemberRepository;
    private ApprovalDepartmentRepository approvalDepartmentRepository;

    private FormRepository formRepository;

    private final ModelMapper modelMapper;

    public ApprovalService(ApprovalRepository approvalRepository,
                           ApproverRepository approverRepository,
                           AttachmentRepository attachmentRepository,
                           ReferencerRepository referencerRepository,

                           ApprovalMemberRepository approvalMemberRepository,
                           ApprovalDepartmentRepository approvalDepartmentRepository,

                           FormRepository formRepository,
                           ModelMapper modelMapper){
        this.approvalRepository = approvalRepository;
        this.approverRepository = approverRepository;
        this.attachmentRepository = attachmentRepository;
        this.referencerRepository = referencerRepository;
        this.approvalMemberRepository = approvalMemberRepository;
        this.approvalDepartmentRepository = approvalDepartmentRepository;
        this.formRepository = formRepository;
        this.modelMapper = modelMapper;
    }

    //전자결재 기안(등록)
    @Transactional
    public void insertApproval(ApprovalDTO approvalDTO, List<MultipartFile> files) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Approval approval = new Approval(
                approvalDTO.getApprovalNo(),
                approvalDTO.getMemberId(),
                approvalDTO.getApprovalTitle(),
                approvalDTO.getApprovalContent(),
                now(),
                approvalDTO.getApprovalStatus(),
                approvalDTO.getRejectReason(),
                approvalDTO.getFormNo()
        );

        // Approval 엔티티 저장
        approvalRepository.save(approval);

        //추가한 결재자 이외 기안자도 결재자에 넣기 => 첫 결재자(기안자)는 결재처리 상태를 '승인' 으로 바꾸기
        Approver senderApprover = new Approver(
                approvalDTO.getApprovalNo().concat("_apr000"),
                approvalDTO.getApprovalNo(),
                0,
                "승인",
                now(),
                approvalDTO.getMemberId()
        );

        approverRepository.save(senderApprover);

        //결재선 꺼내기
        for(int i = 0; i < approvalDTO.getApprover().size(); i++){


            Approver approvers = new Approver(
                    approvalDTO.getApprover().get(i).getApproverNo(),
                    approvalDTO.getApprover().get(i).getApprovalNo(),
                    approvalDTO.getApprover().get(i).getApproverOrder(),
                    approvalDTO.getApprover().get(i).getApproverStatus(),
                    null,
                    approvalDTO.getApprover().get(i).getMemberId()
            );

            //Approver 엔티티 저장
            approverRepository.save(approvers);
        }

        //참조선 꺼내기
        for(int i = 0; i < approvalDTO.getReferencer().size(); i++){
            Referencer referencer = new Referencer(
                    approvalDTO.getReferencer().get(i).getRefNo(),
                    approvalDTO.getReferencer().get(i).getApprovalNo(),
                    approvalDTO.getReferencer().get(i).getMemberId(),
                    approvalDTO.getReferencer().get(i).getRefOrder()
            );

            //Referencer 엔티티 저장
            referencerRepository.save(referencer);
        }

        // 파일 꺼내기

        List<AttachmentDTO> attachmentList = approvalDTO.getAttachment();

        String savePath = UPLOAD_DIR + FILE_DIR;

        List<Map<String, String>> fileList = new ArrayList<>();


        if(files != null && !files.isEmpty()){
            try {
                String savedName = "";
                String ext = "";

                for(int i = 0; i < files.size(); i++)
                {
                    MultipartFile file = files.get(i);

                    AttachmentDTO attachmentDTO = approvalDTO.getAttachment().get(i);
                    ext = attachmentDTO.getFileOriname().substring(attachmentDTO.getFileOriname().lastIndexOf("."));
                    savedName = UUID.randomUUID().toString().replace("-", "") + ext;
                    //파일저장명 암호화

                    Map<String, String> fileMap = new HashMap<>();

                    fileMap.put("originFileName", file.getOriginalFilename());
                    fileMap.put("savedFileName", savedName);
                    fileMap.put("savePath", savePath);

                    attachmentDTO.setFileSavename(savedName);

                    Path uploadPath = Paths.get(savePath);

                    if(!Files.exists(uploadPath)){
                        Files.createDirectories(uploadPath);
                    }
                    //파일저장경로 없으면 만들어주기

                    Path filePath = uploadPath.resolve(savedName);

                    //**파일 경로에 저장
                    Files.copy(file.getInputStream(), filePath);


                    fileList.add(fileMap);


                    Attachment attachment = modelMapper.map(attachmentDTO, Attachment.class);

                    //** 첨부파일정보 DB에 저장
                    attachmentRepository.save(attachment);

                }

            } catch (IOException e) {
                //무슨 에러가 발생해도 파일 지워주기
                e.printStackTrace();

                int cnt = 0;
                for(int i = 0; i < fileList.size(); i++){
                    Map<String, String> file = fileList.get(i);
                    String savedFileName  = file.get("savedFileName");

                    File deleteFile = new File(savePath + "/" + savedFileName);

                    boolean isDeleted = deleteFile.delete();

                    if(isDeleted == true){
                        cnt++;
                    }
                }
                if(cnt == fileList.size()){
                    e.printStackTrace();
                }
                else{
                    e.printStackTrace();
                }
            }
        }
    }


    //한 전자결재 조회
    public ApprovalDTO selectApproval(String approvalNo){
        Approval approval = approvalRepository.findById(approvalNo);

        //기안자 정보 가져오기
        Member senderMember = approvalMemberRepository.findById(approval.getMemberId());
        //기안자의 부서 정보 가져오기
        Department senderDepart = approvalDepartmentRepository.findById(senderMember.getDepartNo());
        //양식 정보 가져오기
        Form approvalForm = formRepository.findById(approval.getFormNo());

        List<ApproverDTO> approver = new ArrayList<>();
        List<ReferencerDTO> referencer = new ArrayList<>();
        List<AttachmentDTO> attachment = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //날짜 포맷
        String approvalFormattedDateTime = approval.getApprovalDate().format(formatter);


        List<Approver> approverList = approverRepository.findByApprovalId(approvalNo);
//        log.info("*****selectApprover -- approverList " + approverList);

        for(int i = 0; i < approverList.size(); i++){

            //결재자 정보 가져오기
            Member receiverMember = approvalMemberRepository.findById(approverList.get(i).getMemberId());
            //결재자 부서 정보 가져오기
            Department receiverDepart = approvalDepartmentRepository.findById(receiverMember.getDepartNo());

            String approverFormattedDateTime = "";

            if(approverList.get(i).getApproverDate() != null){
                //날짜가 null이 아닐때
                approverFormattedDateTime = approverList.get(i).getApproverDate().format(formatter);
            }
            //날짜 포맷

            ApproverDTO approverDTO = new ApproverDTO(approverList.get(i).getApproverNo(), approverList.get(i).getApprovalNo(), approverList.get(i).getApproverOrder(), approverList.get(i).getApproverStatus(), approverFormattedDateTime, approverList.get(i).getMemberId(), receiverMember.getName(), receiverMember.getPositionName(), receiverDepart.getDepartName());

            approver.add(approverDTO);


        }
//        log.info("*****selectApproval -- Approver List " + approver );

        List<Referencer> referencerList = referencerRepository.findByApprovalId(approvalNo);
        for(int i = 0; i < referencerList.size(); i++){

            //참조자 정보 가져오기
            Member referencerMember = approvalMemberRepository.findById(referencerList.get(i).getMemberId());
            //참조자 부서 정보 가져오기
            Department referencerDepart = approvalDepartmentRepository.findById(referencerMember.getDepartNo());

            ReferencerDTO referencerDTO = new ReferencerDTO(referencerList.get(i).getRefNo(), referencerList.get(i).getApprovalNo(), referencerList.get(i).getMemberId(), referencerList.get(i).getRefOrder(), referencerMember.getName(), referencerMember.getPositionName(), referencerDepart.getDepartName());
            referencer.add(referencerDTO);
        }

        List<Attachment> attachmentList = attachmentRepository.findByApprovalId(approvalNo);
        for(int i = 0; i < attachmentList.size(); i++){
            AttachmentDTO attachmentDTO = new AttachmentDTO(attachmentList.get(i).getFileNo(), attachmentList.get(i).getFileOriname(), attachmentList.get(i).getFileSavepath(), attachmentList.get(i).getFileSavename(), attachmentList.get(i).getApprovalNo());
            attachment.add(attachmentDTO);
        }
//        log.info("***** formattedDateTime ***** " + approvalFormattedDateTime);


        //최종 승인 날짜
        String finalApproverDate = "";
        if(approval.getApprovalStatus() == "승인" || approval.getApprovalStatus().equals("승인")){
            finalApproverDate = approverList.get(approverList.size()-1).getApproverDate().format(formatter);
        }

        //진행중인 사람
        Approver standByApprover = approverRepository.findStatusById(approvalNo);

        String standByMemberName = "";

        if(standByApprover != null ){

            Member standByMember = approvalMemberRepository.findById(standByApprover.getMemberId());
            standByMemberName = standByMember.getName();
        }

        ApprovalDTO approvalDTO = new ApprovalDTO(approval.getApprovalNo(), approval.getMemberId(), approval.getApprovalTitle(), approval.getApprovalContent(), approvalFormattedDateTime, approval.getApprovalStatus(), approval.getRejectReason(), approval.getFormNo(), approvalForm.getFormName(), senderDepart.getDepartName(), senderMember.getName(), senderMember.getPositionName(), attachment, approver, referencer, finalApproverDate, standByMemberName);

        log.info("여기까지 했다...ApprovalDTO는 어디서 나오는건가?");
        return approvalDTO;
    }


    //전자결재 회수
    @Transactional
    public ApprovalDTO updateApproval(String approvalNo) {
        //***** 나를 제외한 다른 사람이 한사람이라도 처리했을 경우 회수 불가능

        //수정하고자 하는 전자결재 정보 조회
        ApprovalDTO approvalDTO = selectApproval(approvalNo);

        log.info("***** 날짜 확인 ***** " + approvalDTO.getApprovalDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse(approvalDTO.getApprovalDate(), formatter);

        Approval approval = new Approval(approvalDTO.getApprovalNo(), approvalDTO.getMemberId(), approvalDTO.getApprovalTitle(), approvalDTO.getApprovalContent(), parsedDateTime, approvalDTO.getApprovalStatus(), approvalDTO.getRejectReason(), approvalDTO.getFormNo());

        //상태 update
        approval = new ApprovalBuilder(approval).approvalStatus("회수").builder();

        //db update
        approvalRepository.update(approval);

        //DTO도 변경
        approvalDTO.setApprovalStatus(approval.getApprovalStatus());

        return approvalDTO;
    }



    //전자결재 처리(결재)
    @Transactional
    public ApproverDTO updateApprover(String approverNo, Map<String, String> statusMap){
        // DTO -> 엔티티 -> DTO

        //전자결재 번호 가져오기
        String approvalNo = approverNo.substring(0, approverNo.indexOf("_"));

        //전자결재 번호 받아서 해당 전자결재 정보 조회
        ApprovalDTO approvalDTO = selectApproval(approvalNo);

        //해당 전자결재에서 수정하고자 하는 전자결재자 정보 조회
        List<ApproverDTO> approverList = approvalDTO.getApprover();
        log.info("***** Service : approverList : " + approverList);

        ApproverDTO approverDTO = null;

        for(int i = 0; i < approverList.size(); i++)
        {
            approverDTO = approverList.get(i);

            if(approverDTO.getApproverNo().equals(approverNo) || approverDTO.getApproverNo() == approverNo){
                //가져온 결재자 번호가 존재한다면

                //날짜 변경
                Approver approver = new Approver(approverDTO.getApproverNo(), approverDTO.getApprovalNo(), approverDTO.getApproverOrder(), approverDTO.getApproverStatus(), now(), approverDTO.getMemberId());

                //승인 시 : 결재자 처리상태 변경 -> 결재자 처리 날짜 변경 -> (마지막 번호일경우) 전자결재 처리상태 변경 (승인)
                //반려 시 : 결재자 처리상태 변경 -> 결재자 처리 날짜 변경 -> 전자결재 처리상태 변경(반려) -> 전자결재 처리상태 변경(반려사유)
                String status = statusMap.get("approverStatus");

                String rejectReason = statusMap.get("rejectReason");

                //결재자 처리 상태 update
                approver = new ApproverBuilder(approver).approverStatus(status).builder();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime parsedDateTime = LocalDateTime.parse(approvalDTO.getApprovalDate(), formatter);
                Approval approval = new Approval(approvalDTO.getApprovalNo(),approvalDTO.getMemberId(),approvalDTO.getApprovalTitle(),approvalDTO.getApprovalContent(), parsedDateTime, approvalDTO.getApprovalStatus(), approvalDTO.getRejectReason(), approvalDTO.getFormNo());

                status :
                switch(status){
                    case "승인" :
                    {
                        if(i == approverList.size() -1 ){
                            //마지막 순서일 경우

                            //전자결재 처리상태 변경 (승인)
                            approval = new ApprovalBuilder(approval).approvalStatus(status).builder();
                        }
                        break status;
                    }
                    case "반려" :
                    {
                        approval = new ApprovalBuilder(approval).approvalStatus(status).rejectReason(rejectReason).builder();

                        break status;
                    }
                }
                //db 결재자 update
                approverRepository.update(approver);
                approverDTO.setApproverStatus(status);
                log.info("*****상태 : " + approverDTO.getApproverStatus());

                //db 결재정보 update
                approvalRepository.update(approval);


                log.info("***** Service 마지막 approverDTO : " + approverDTO);
                return approverDTO;
            }
        }

        return approverDTO;

    }

    public List<ApprovalDTO> selectApprovalList(int memberId, Map<String, Object> condition) {
        //상신함 조회 : given / member_id : 나 / 임시저장 제외 / 현재 처리중(ApprovalNo인 Approver중 '대기' 중 가장 첫번째)인 결재자(이름, 직급) 보여주기
        //임시저장함 조회 : tempGiven / member_id : 나 / 임시저장만
        //전체수신함 조회 : receivedAll : approver_id : 나 / 내 approver_order가 1이상 / 해당 approval_no의 approver의 상태 중 '대기' 상태의 마지막이 자신의 approver_order 이후일 경우에만
        //결제대기내역 조회 : received / approver_id : 나 / 내 approver_order가 1이상 / approval 상태 = 처리중만, 해당 approval_no의 approver의 상태 중 '대기' 상태의 처음이 자신의 approver_order일 경우에만
        //수신참조내역 조회 : receivedRef / referencer_id : 나 / 임시저장, 회수 제외

        List<ApprovalDTO> approvalDTOList = new ArrayList<>();

        String flag = condition.get("flag").toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        switch(flag){
            case "given" : {
                //결재 상신함 (내가 기안자 / 임시저장 제외)

                List<Approval> approvalList = approvalRepository.findByMemberId(memberId);


                //양식이름, 최종 승인 날짜, 현재 결재 진행자의 이름 및 직급
                if(!approvalList.isEmpty() || approvalList.size() > 0){
                    //목록이 있다면
                    for(int i = 0; i < approvalList.size(); i++){

                        Approval approval = approvalList.get(i);

                        ApprovalDTO approvalDTO = selectApproval(approval.getApprovalNo());
                        log.info("*****SERVICE(given) : 전자결재 한 건 DTO : " + approvalDTO);

                        approvalDTOList.add(approvalDTO);
                    }
                }
                break;
            }
            case "tempGiven" : {
                // 임시저장함 (내가 기안자 / 임시저장만)
                List<Approval> tempApprovalList = approvalRepository.findTempByMemberId(memberId);

                if(!tempApprovalList.isEmpty() || tempApprovalList.size() > 0){
                    for(int i = 0; i < tempApprovalList.size(); i++){
                        Approval approval = tempApprovalList.get(i);
                        log.info("\n*****SERVIE(tempGiven) : 임시저장 한 건 : " +  tempApprovalList.size() + " \n*****SERVICE(tempGiven) : 임시저장 한 건 제목 : " + approval.getApprovalTitle());

                        ApprovalDTO approvalDTO = selectApproval(approval.getApprovalNo());
//                        log.info("*****SERVICE(tempGiven) : 임시저장 한 건 DTO : " + approvalDTO);

                        log.info("\n여기도 했단다.... 저 정체모를 approvalDTO는 어디서 나왔을까 ");
                        approvalDTOList.add(approvalDTO);
                    }
                }

                break;
            }
            case "receivedAll" : {
                // 전체 수신함 (내가 결재자 / )

                break;
            }
            case "received" : {
                // 결재 대기함 (내가 결재자 / approvalStatus="처리 중", approverStatus = "대기" 인 approver 중 가장 첫번째의 member_id가 나의 member_id와 같은 결재번호의 전자결재)
                //1. 내차례 (approverOrder > 0)
                //- approvalNo 가 "approvalNo"인 Approver 중에 ApproverStatus 가 "대기" 인 것 중 가장 처음이 나의 Approver_order(2)일때
                //- 단, approvalStatus = 처리중 일때

                //approvalStatus = "처리 중", approverStatus = "대기" 인 approver 중 가장 낮은 approver_order의 approver 목록 가져오기
                List<Approver> approverList = approverRepository.findByStandById();

                if(approverList.size() > 0 || !approverList.isEmpty()){
                    for(int i = 0; i < approverList.size(); i++){
                        Approver approver = approverList.get(i);

                        //가장 낮은 approver_order의 approver의 memberId 가 나와 같다면
                        if((approver.getMemberId() == memberId)){
                            //해당 전자결재 번호의 전자결재 정보를 가져오기
                            Approval approval = approvalRepository.findById(approver.getApprovalNo());

                            ApprovalDTO approvalDTO = selectApproval(approval.getApprovalNo());
                            log.info("*****SERVICE(received) : 결재대기 DTO : " + approvalDTO);

                            approvalDTOList.add(approvalDTO);
                        }
                    }
                }
                log.info("\nSERVICE (received) 결재대기 DTO 갯수 : " + approvalDTOList.size());
                break;
            }
            case "receivedRef" : {
                // 수신 참조내역 (내가 참조자 / 임시저장, 회수 제외)
                List<Referencer> referencerList = referencerRepository.findByMemberId(memberId);

                //참조내역이 있다면
                if(referencerList.size() > 0 || !referencerList.isEmpty()){


                    for(int i = 0; i < referencerList.size(); i++){
                        Referencer referencer = referencerList.get(i);
                        //해당 전자결재 정보 가져오기
                        Approval approval = approvalRepository.findById(referencer.getApprovalNo());

                        ApprovalDTO approvalDTO = selectApproval(approval.getApprovalNo());
                        log.info("*****SERVICE(referenced) : 참조 DTO : " + approvalDTO);

                        approvalDTOList.add(approvalDTO);
                    }
                }

                break;
            }
            default : break;

        }

        return approvalDTOList;
    }
}
