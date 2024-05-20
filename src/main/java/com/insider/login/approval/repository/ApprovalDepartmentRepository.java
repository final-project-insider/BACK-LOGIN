package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalDepartmentRepository extends JpaRepository<Department, Integer> {

    public Department findById(int departNo);


    @Query("SELECT d FROM ApprovalDepart d ORDER BY d.departNo ASC")
    public List<Department> findAllOrderedByDepartNo();
}
