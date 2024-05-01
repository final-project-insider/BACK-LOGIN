package com.insider.login.approval.service;

import com.insider.login.approval.dto.ApprovalDTO;
import com.insider.login.approval.dto.AttachmentDTO;
import com.insider.login.approval.entity.*;
import com.insider.login.approval.repository.ApprovalRepository;
import com.insider.login.approval.repository.ApproverRepository;
import com.insider.login.approval.repository.AttachmentRepository;
import com.insider.login.approval.repository.ReferencerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ApprovalService {

    private ApprovalRepository approvalRepository;
    private ApproverRepository approverRepository;
    private AttachmentRepository attachmentRepository;
    private ReferencerRepository referencerRepository;

    private final ModelMapper modelMapper;

    public ApprovalService(ApprovalRepository approvalRepository,
                           ApproverRepository approverRepository,
                           AttachmentRepository attachmentRepository,
                           ReferencerRepository referencerRepository,
                           ModelMapper modelMapper){
        this.approvalRepository = approvalRepository;
        this.approverRepository = approverRepository;
        this.attachmentRepository = attachmentRepository;
        this.referencerRepository = referencerRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void insertForm(Form newForm) {

        approvalRepository.insertForm(newForm);

    }

    @Transactional
    public void insertApproval(ApprovalDTO approvalDTO) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Approval approval = new Approval(
                approvalDTO.getApprovalNo(),
                approvalDTO.getMemberId(),
                approvalDTO.getApprovalTitle(),
                approvalDTO.getApprovalContent(),
                LocalDateTime.parse(approvalDTO.getApprovalDate(),formatter),
                approvalDTO.getApprovalStatus(),
                approvalDTO.getRejectReason(),
                approvalDTO.getFormNo()
        );

        // Approval 엔티티 저장
        approvalRepository.save(approval);

        //결재선 꺼내기
        for(int i = 0; i < approvalDTO.getApprover().size(); i++){

            Approver approver = new Approver(
                    approvalDTO.getApprover().get(i).getApproverNo(),
                    approvalDTO.getApprover().get(i).getApprovalNo(),
                    approvalDTO.getApprover().get(i).getApproverOrder(),
                    approvalDTO.getApprover().get(i).getApproverStatus(),
                    LocalDateTime.parse(approvalDTO.getApprover().get(i).getApproverDate(), formatter),
                    approvalDTO.getApprover().get(i).getMemberId()
            );

            //Approver 엔티티 저장
            approverRepository.save(approver);
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
        try {
            List<AttachmentDTO> attachmentList = approvalDTO.getAttachment();

            if (attachmentList != null || !attachmentList.isEmpty()) {
                for (AttachmentDTO attachmentDTO : approvalDTO.getAttachment()) {


                    Path fileSavePath = Paths.get(attachmentDTO.getFileSavepath(), attachmentDTO.getFileSavename());

                    //파일 저장 처리
                    //원본 파일의 경로를 가져옴
//                    Path originalFile

                    String ext = attachmentDTO.getFileOriname().substring(attachmentDTO.getFileOriname().lastIndexOf("."));
                    String savedFileName = UUID.randomUUID().toString().replace("-", "") + ext;

                    attachmentDTO.setFileSavename(savedFileName);


                    Attachment attachment = modelMapper.map(attachmentDTO, Attachment.class);

                    // 첨부파일 엔티티 저장
                    attachmentRepository.save(attachment);
                }
            }
        }catch(NullPointerException e){

        }

    }
}
