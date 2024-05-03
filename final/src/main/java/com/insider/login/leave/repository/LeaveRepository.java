package com.insider.login.leave.repository;


import com.insider.login.leave.entity.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leaves, Integer> {
    @Query("SELECT l FROM Leaves l WHERE l.memberId = :memberId")
    List<Leaves> findByMemberId(@Param("memberId") int memberId);
}
