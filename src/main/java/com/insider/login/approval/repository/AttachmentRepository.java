package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Attachment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttachmentRepository {

    @PersistenceContext
    private EntityManager  manager;


    public void save(Attachment attachment){
        manager.persist(attachment);
    }

    public List<Attachment> findByApprovalId(String approvalNo) {
        List<Attachment> attachmentList = manager.createQuery("SELECT a FROM Apr_attachment a WHERE a.approvalNo = :approvalNo", Attachment.class)
                .setParameter("approvalNo", approvalNo)
                .getResultList();

        return attachmentList;
    }

    public void deleteById(String approvalNo) {
        List<Attachment> attachmentList = findByApprovalId(approvalNo);

        if(!attachmentList.isEmpty()){
            for(Attachment attachment : attachmentList){
                Attachment managedAttachment = manager.merge(attachment);
                manager.remove(managedAttachment);
            }

        }else{

        }

    }
}
