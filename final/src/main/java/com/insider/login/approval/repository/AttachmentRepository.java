package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Attachment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class AttachmentRepository {

    @PersistenceContext
    private EntityManager  manager;


    public void save(Attachment attachment){
        manager.persist(attachment);
    }
}
