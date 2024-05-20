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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;

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

    private ApprovalPositionRepository approvalPositionRepository;

    private FormRepository formRepository;

    private final ModelMapper modelMapper;

    public ApprovalService(ApprovalRepository approvalRepository,
                           ApproverRepository approverRepository,
                           AttachmentRepository attachmentRepository,
                           ReferencerRepository referencerRepository,

                           ApprovalMemberRepository approvalMemberRepository,
                           ApprovalDepartmentRepository approvalDepartmentRepository,
                           ApprovalPositionRepository approvalPositionRepository,

                           FormRepository formRepository,
                           ModelMapper modelMapper){
        this.approvalRepository = approvalRepository;
        this.approverRepository = approverRepository;
        this.attachmentRepository = attachmentRepository;
        this.referencerRepository = referencerRepository;
        this.approvalMemberRepository = approvalMemberRepository;
        this.approvalDepartmentRepository = approvalDepartmentRepository;
        this.approvalPositionRepository = approvalPositionRepository;
        this.formRepository = formRepository;
        this.modelMapper = modelMapper;
    }

    //양식 목록 조회
    public List<FormDTO> selectFormList(){

        List<Form> formList = formRepository.findAll();

        List<FormDTO> formDTOList = formList.stream()
                .map(form -> new FormDTO(form.getFormNo(), form.getFormName(), form.getFormShape()))
                .sorted((form1, form2) -> {
                    //non을 가장 위에 두고 그 외의 경우는 한글순으로 정렬
                    if("non".equals(form1.getFormNo()) && !"non".equals(form2.getFormNo())){
                        //form1이 "non"이고 form2가 "non"이 아니면 form1을 더 위로
                        return -1;
                    }else if(!"non".equals(form1.getFormNo())&&"non".equals(form2.getFormNo())){
                        //form1이 "non"이 아니고 form2가 "non"이면 form2를 더 위로
                        return 1;
                    }
                    else{
                        return form1.getFormName().compareTo(form2.getFormName());
                    }
                })
                .collect(Collectors.toList());

        return formDTOList;
    }

    //양식번호로 양식 조회
    public FormDTO selectForm(String formNo){

        FormDTO formDTO = new FormDTO();
        Form form = formRepository.findByFormNo(formNo)
                .orElseThrow(() -> new RuntimeException("양식을 찾을 수 없습니다. : " + formNo));

        formDTO = modelMapper.map(form, FormDTO.class);


        return formDTO;
    }

    //전자결재 기안(등록)
    @Transactional
    public Object insertApproval(ApprovalDTO approvalDTO, List<MultipartFile> files) {

        int result = 0;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            Approval approval = new Approval(
                    approvalDTO.getApprovalNo(),
                    approvalDTO.getMemberId(),
                    approvalDTO.getApprovalTitle(),
                    approvalDTO.getApprovalContent(),
                    now(),
                    approvalDTO.getApprovalStatus(),    //처리중, 임시저장
                    approvalDTO.getRejectReason(),
                    approvalDTO.getFormNo()
            );

            // Approval 엔티티 저장
            approvalRepository.save(approval);

            //추가한 결재자 이외 기안자도 결재자에 넣기 => 첫 결재자(기안자)는 결재처리 상태를 '승인' 으로 바꾸기
            //임시저장시엔?
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

//        String savePath = UPLOAD_DIR + FILE_DIR;

            List<Map<String, String>> fileList = new ArrayList<>();

            if(files != null && !files.isEmpty()){
                try {
                    String savedName = "";
                    String ext = "";
                    String savePath = UPLOAD_DIR + FILE_DIR;

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
                        log.info("savePath : " + savePath);

                        if(!Files.exists(uploadPath)){
                            Files.createDirectories(uploadPath);
                        }
                        //파일저장경로 없으면 만들어주기

                        Path filePath = uploadPath.resolve(savedName);

                        //**파일 경로에 저장
                        try{
                            Files.copy(file.getInputStream(), filePath);
                            log.info("파일 저장 됐어 : " + filePath);
                        }catch(Exception e){
                            log.info("파일 저장 안됐어");
                        }


                        fileList.add(fileMap);


                        Attachment attachment = modelMapper.map(attachmentDTO, Attachment.class);

                        //** 첨부파일정보 DB에 저장
                        attachmentRepository.save(attachment);

                    }

                    result = 1;

                } catch (IOException e) {
                    //무슨 에러가 발생해도 파일 지워주기
                    e.printStackTrace();

                    try {

                        deleteFile(fileList);
                        log.info("파일 지워졌대요 ");

                    } catch (IOException ex) {
                        e.printStackTrace();
                    }
                }
            }

        return ( result > 0 ) ? "성공" : "실패";
    }


    //부서목록조회
    public List<DepartmentDTO> selectDepartList(){

        List<DepartmentDTO> departmentDTOList = new ArrayList<>();

        List<Department> departmentList = approvalDepartmentRepository.findAllOrderedByDepartNo();

        for(int i = 0; i < departmentList.size(); i++){
            Department department = departmentList.get(i);
            DepartmentDTO departmentDTO = new DepartmentDTO(department.getDepartNo(), department.getDepartName());

            departmentDTOList.add(i, departmentDTO);
        }

        return departmentDTOList;
    }

    //사원목록조회
    public List<MemberDTO> selectMemberList(int departNo){

        List<MemberDTO> memberDTOList = new ArrayList<>();
        // 부서정보 : 부서명으로 조회할 경우
//        Department department = approvalDepartmentRepository.findByName(departName);
        // 부서정보 : 부서번호로 조회할 경우
        Department department = approvalDepartmentRepository.findById(departNo);


        //부서별 사원 목록
        List<Member> memberList = approvalMemberRepository.findByDepart(departNo);
        //한 부서의 멤버 목록 조회
        for(Member member : memberList){
            Position position = approvalPositionRepository.findById(member.getPositionLevel()).orElse(null);

            MemberDTO memberDTO = new MemberDTO(member.getName(),
                                            member.getMemberId(),
                                            member.getPassword(),
                                            member.getDepartNo(),
                                            member.getPositionLevel(),
                                            member.getEmployedDate(),
                                            member.getAddress(),
                                            member.getPhoneNo(),
                                            member.getMemberStatus(),
                                            member.getEmail(),
                                            member.getMemberRole(),
                                            member.getImage_url(),
                                            //departName,
                                            department.getDepartName(),
                                            position.getPositionName()
                    );

            memberDTOList.add(memberDTO);
        }

        return memberDTOList;
    }


    //한 전자결재 조회
    public ApprovalDTO selectApproval(String approvalNo){

        Approval approval = approvalRepository.findById(approvalNo);

        //기안자 정보 가져오기
        Member senderMember = approvalMemberRepository.findById(approval.getMemberId());
        //기안자의 부서 정보 가져오기
        Department senderDepart = approvalDepartmentRepository.findById(senderMember.getDepartNo());
        //기안자의 직급 정보 가져오기
        Position senderPosition = approvalPositionRepository.findById(senderMember.getPositionLevel()).orElse(null);


        //양식 정보 가져오기
        Form form = formRepository.findByFormNo(approval.getFormNo())
                .orElseThrow(() -> new RuntimeException("양식을 찾을 수 없습니다. : " + approval.getFormNo()));


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

            Position receiverPosition = approvalPositionRepository.findById(receiverMember.getPositionLevel()).orElse(null);

            String approverFormattedDateTime = "";

            if(approverList.get(i).getApproverDate() != null){
                //날짜가 null이 아닐때
                approverFormattedDateTime = approverList.get(i).getApproverDate().format(formatter);
            }
            //날짜 포맷

            ApproverDTO approverDTO = new ApproverDTO(approverList.get(i).getApproverNo(), approverList.get(i).getApprovalNo(), approverList.get(i).getApproverOrder(), approverList.get(i).getApproverStatus(), approverFormattedDateTime, approverList.get(i).getMemberId(), receiverMember.getName(), receiverPosition.getPositionName(), receiverDepart.getDepartName(), approval.getRejectReason());

            approver.add(approverDTO);


        }
//        log.info("*****selectApproval -- Approver List " + approver );

        List<Referencer> referencerList = referencerRepository.findByApprovalId(approvalNo);
        for(int i = 0; i < referencerList.size(); i++){

            //참조자 정보 가져오기
            Member referencerMember = approvalMemberRepository.findById(referencerList.get(i).getMemberId());
            //참조자 부서 정보 가져오기
            Department referencerDepart = approvalDepartmentRepository.findById(referencerMember.getDepartNo());

            Position referencerPosition = approvalPositionRepository.findById(referencerMember.getPositionLevel()).orElse(null);

            ReferencerDTO referencerDTO = new ReferencerDTO(referencerList.get(i).getRefNo(), referencerList.get(i).getApprovalNo(), referencerList.get(i).getMemberId(), referencerList.get(i).getRefOrder(), referencerMember.getName(), referencerPosition.getPositionName(), referencerDepart.getDepartName());
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

        ApprovalDTO approvalDTO = new ApprovalDTO(approval.getApprovalNo(), approval.getMemberId(), approval.getApprovalTitle(), approval.getApprovalContent(), approvalFormattedDateTime, approval.getApprovalStatus(), approval.getRejectReason(), approval.getFormNo(), form.getFormName(), senderDepart.getDepartName(), senderMember.getName(), senderPosition.getPositionName(), attachment, approver, referencer, finalApproverDate, standByMemberName);


        log.info("service : " + approvalDTO);

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

        log.info("Service : updateApprover 들어왔다 : " + approverNo);

        //전자결재 번호 가져오기
        String approvalNo = approverNo.substring(0, approverNo.indexOf("_"));
        log.info("Service : updateApprover - approvalNo 추출 : " + approvalNo);

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


    //결재 목록 페이지 조회
    public Page<ApprovalDTO> selectApprovalList(int memberId, Map<String, Object> condition, int pageNo) {
        //상신함 조회 : given / member_id : 나 / 임시저장 제외 / 현재 처리중(ApprovalNo인 Approver중 '대기' 중 가장 첫번째)인 결재자(이름, 직급) 보여주기
        //임시저장함 조회 : tempGiven / member_id : 나 / 임시저장만
        //전체수신함 조회 : receivedAll : approver_id : 나 / 내 approver_order가 1이상 / 해당 approval_no의 approver의 상태 중 '대기' 상태의 마지막이 자신의 approver_order 이후일 경우에만
        //결제대기내역 조회 : received / approver_id : 나 / 내 approver_order가 1이상 / approval 상태 = 처리중만, 해당 approval_no의 approver의 상태 중 '대기' 상태의 처음이 자신의 approver_order일 경우에만
        //수신참조내역 조회 : receivedRef / referencer_id : 나 / 임시저장, 회수 제외

        log.info("service 들어왔다 : selectApprovalList");
        System.out.println("service 들어왔다 : selectApprovalList");

        List<ApprovalDTO> approvalDTOList = new ArrayList<>();

        String flag = condition.get("flag").toString();
        String title = isNull(condition.get("title")) ? "" : condition.get("title").toString();


        int limit = (Integer) condition.get("limit");

        String direction = isNull(condition.get("direction")) ? "" : condition.get("direction").toString();


        log.info("*****서비스 들어옴 : memberId : " + memberId + ", flag : " + flag + ", title : " + title + ", pageNo : " + pageNo);
        System.out.println("*****서비스 들어옴 : memberId : " + memberId + ", flag : " + flag + ", title : " + title + ", pageNo : " + pageNo);


        Pageable pageable = PageRequest.of(pageNo, limit);

        Page<Approval> approvalPage = null;

        switch(flag) {
            case "given": {
                //결재 상신함 (내가 기안자 / 임시저장 제외)

                approvalPage = approvalRepository.findByMemberId(memberId, pageable, direction, title);
                System.out.println("totalPage : " + approvalPage.getTotalPages());

                approvalDTOList = ListToDTO(approvalPage);

                break;
            }
            case "tempGiven" : {
                // 임시저장함 (내가 기안자 / 임시저장만)

                approvalPage = approvalRepository.findTempByMemberId(memberId, pageable, direction, title);

                approvalDTOList = ListToDTO(approvalPage);


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
                List<Approver> approverList = approverRepository.findByStandById(title);

                if(approverList.size() > 0 || !approverList.isEmpty()){
                    for(int i = 0; i < approverList.size(); i++){
                        Approver approver = approverList.get(i);

                        //가장 낮은 approver_order의 approver의 memberId 가 나와 같다면
                        if((approver.getMemberId() == memberId)){
                            //해당 전자결재 번호의 전자결재 정보를 가져오기
                            Approval approval = approvalRepository.findById(approver.getApprovalNo());

                            ApprovalDTO approvalDTO = selectApproval(approval.getApprovalNo());

                            approvalDTOList.add(approvalDTO);
                        }
                    }
                }

                if(direction == "ASC" || direction.equals("ASC")){
                    approvalDTOList.sort(Comparator.comparing(ApprovalDTO::getApprovalDate).reversed());
                }
                else{
                    approvalDTOList.sort(Comparator.comparing(ApprovalDTO::getApprovalDate));
                }

                log.info("\nSERVICE (received) 결재대기 DTO 갯수 : " + approvalDTOList.size());

                int pageSize = pageable.getPageSize();
                int pageNumber = pageable.getPageNumber();
                int startOffset = pageNumber * pageSize;
                int endOffset = Math.min(startOffset + pageSize, approvalDTOList.size());

                List<ApprovalDTO> pageContent = approvalDTOList.subList(startOffset, endOffset);

                return new PageImpl<>(pageContent, pageable, approvalDTOList.size());

            }
            case "receivedRef" : {
                // 수신 참조내역 (내가 참조자 / 임시저장, 회수 제외)

                approvalPage = referencerRepository.findByMemberId(memberId, pageable, direction, title);

                approvalDTOList = ListToDTO(approvalPage);

                break;
            }

        }

        int totalPage =  approvalPage.getTotalPages();
        long total = approvalPage.getTotalElements();

        System.out.println("Service last totalPage : " + totalPage);

        System.out.println("Service total 갯수 : " + total);

        PageImpl<ApprovalDTO> appPage = new PageImpl<> (approvalDTOList, pageable, total);
        System.out.println("new PageImpl getPageable: " + appPage.getSize());

        System.out.println("new PageImpl totalPage : " + appPage.getTotalPages());

        return appPage;
    }

    //Page를 DTO로 변환
    public List<ApprovalDTO> ListToDTO(Page<Approval> approvalPage){

        List<ApprovalDTO> approvalDTOList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if(!approvalPage.getContent().isEmpty()){
            approvalDTOList = approvalPage.getContent().stream()
                    .map(approval -> {
                        ApprovalDTO approvalDTO = modelMapper.map(approval, ApprovalDTO.class);
                        String approvalNo = approval.getApprovalNo();

                        approvalDTO = selectApproval(approvalNo);
                        approvalDTO.setApprovalDate(approval.getApprovalDate().format(formatter));


                        return approvalDTO;
                    })
                    .collect(Collectors.toList());
        }else{
            approvalDTOList = Collections.emptyList();
        }

        return approvalDTOList;

    }

    //전자결재 삭제
    @Transactional
    public boolean approvalDelete(String approvalNo) {

        try{

            List<Attachment> attachmentList = attachmentRepository.findByApprovalId(approvalNo);
            List<Map<String, String>> fileList = new ArrayList<>();
            Map<String, String> attachMap = new HashMap<>();

            for(Attachment attachment : attachmentList){
                attachMap.put("savedFileName", attachment.getFileSavename());
                fileList.add(attachMap);
            }

            deleteFile(fileList);                           //첨부파일 삭제

            attachmentRepository.deleteById(approvalNo);    //첨부파일 DB 삭제

            referencerRepository.deleteById(approvalNo);    //참조선 삭제
            approverRepository.deleteById(approvalNo);      //결제선 삭제
            approvalRepository.deleteById(approvalNo);      //전자결재 삭제

            return true;
        }catch(IOException e){
            e.printStackTrace();
            log.info("파일 삭제 실패");
            return false;

        }catch(Exception e){
            return false;
        }

    }

    //파일 삭제
    public void deleteFile(List<Map<String, String>> fileList) throws IOException  {
        String savePath = UPLOAD_DIR + FILE_DIR;

        List<String> failedFiles = new ArrayList<>();

        for(int i = 0; i < fileList.size(); i++){
            Map<String, String> file = fileList.get(i);
            String savedFileName  = file.get("savedFileName");

            File deleteFile = new File(savePath + "/" + savedFileName);


            boolean isDeleted = deleteFile.delete();

            if(!isDeleted){
                failedFiles.add(savedFileName);
            }
        }

       /* if(!failedFiles.isEmpty()){

            throw new IOException("Failed to delete files : " + failedFiles.toString());
        }*/
    }


    //가장 마지막 전자결재 번호 조회
    public String selectApprovalNo(String yearFormNo) {

        String lastApprovalNo = approvalRepository.findByApprovalNo(yearFormNo);
        log.info("Service lastApprovalNo: " + lastApprovalNo);

        return lastApprovalNo;
    }
}
