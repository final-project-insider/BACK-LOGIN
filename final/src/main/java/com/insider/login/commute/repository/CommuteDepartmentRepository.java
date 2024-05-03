package com.insider.login.commute.repository;

import com.insider.login.commute.entity.CommuteDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommuteDepartmentRepository extends JpaRepository<CommuteDepartment, Integer> {
    CommuteDepartment findByDepartNo(int departNo);
}
