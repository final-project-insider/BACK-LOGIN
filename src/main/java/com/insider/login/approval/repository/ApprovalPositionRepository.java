package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalPositionRepository extends JpaRepository<Position, String> {

}
