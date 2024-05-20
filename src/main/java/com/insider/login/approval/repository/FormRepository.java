package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, String> {

    public Optional<Form> findByFormNo(String formNo) ;

    public List<Form> findAll();
}

