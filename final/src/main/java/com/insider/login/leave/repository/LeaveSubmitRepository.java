package com.insider.login.leave.repository;

import com.insider.login.leave.entity.LeaveSubmit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LeaveSubmitRepository extends JpaRepository<LeaveSubmit, Integer> {

    @Query("SELECT l FROM LeaveSubmit l WHERE l.leaveSubApplicant = :applicantId")
    Page<LeaveSubmit> findByMemberId(@Param("applicantId") int applicantId, Pageable pageable);

    @Query("SELECT l FROM LeaveSubmit l WHERE l.leaveSubApplicant = :applicantId")
    List<LeaveSubmit> findByMemberId(@Param("applicantId") int applicantId);
}
