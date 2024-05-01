package com.insider.login.member.repository;

import com.insider.login.member.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
//    Department findDepartmentByDepartNo(int departNo);

    Department findByDepartNo(int departNo);
}
