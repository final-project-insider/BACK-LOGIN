package com.insider.login.department.repository;

import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByDepartName(String departName);
}