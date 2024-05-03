package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Referencer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ReferencerRepository {

    @PersistenceContext
    private EntityManager manager;

    public void save (Referencer referencer){
        manager.persist(referencer);
    }
}
