package com.insider.login.approval.service;

import com.insider.login.approval.dto.ApprovalDTO;
import com.insider.login.approval.dto.AttachmentDTO;
import com.insider.login.approval.entity.Approval;
import com.insider.login.approval.entity.Approver;
import com.insider.login.approval.entity.Attachment;
import com.insider.login.approval.entity.Form;
import com.insider.login.approval.repository.ApprovalRepository;
import com.insider.login.approval.repository.ApproverRepository;
import com.insider.login.approval.repository.AttachmentRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ApprovalService {

    private ApprovalRepository approvalRepository;
    private ApproverRepository approverRepository;
    private AttachmentRepository attachmentRepository;
    private final ModelMapper modelMapper;

    public ApprovalService(ApprovalRepository approvalRepository,
                           ApproverRepository approverRepository,
                           AttachmentRepository attachmentRepository,
                           ModelMapper modelMapper){
        this.approvalRepository = approvalRepository;
        this.approverRepository = approverRepository;
        this.attachmentRepository = attachmentRepository;
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


        /*if(approvalDTO.getAttachment().size() > 0){
            for(int i = 0; i < approvalDTO.getAttachment().size(); i++){
                Attachment attachment = new Attachment(
                        approvalDTO.getAttachment().get(i).getFileNo(),
                        approvalDTO.getAttachment().get(i).getFileOriname(),
                        approvalDTO.getAttachment().get(i).getFileSavepath(),
                        approvalDTO.getAttachment().get(i).getFileSavename(),
                        approvalDTO.getAttachment().get(i).getApprovalNo()
                );

                attachmentRepository.save(attachment);
            }

        }*/

        try {

            if (approvalDTO.getAttachment().size() > 0 || approvalDTO.getAttachment().isEmpty()) {
                for (AttachmentDTO attachmentDTO : approvalDTO.getAttachment()) {

                    Attachment attachment = modelMapper.map(attachmentDTO, Attachment.class);

                    // 첨부파일 엔티티 저장
                    attachmentRepository.save(attachment);
                }
            }
        }catch(NullPointerException e){

        }

    }
}
