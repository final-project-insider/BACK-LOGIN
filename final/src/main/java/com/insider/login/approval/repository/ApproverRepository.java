package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Approver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ApproverRepository {

    @PersistenceContext
    private EntityManager manager;


    public void save(Approver approver){
        manager.persist(approver);
    }
}
