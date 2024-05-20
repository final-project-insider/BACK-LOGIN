package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalMemberRepository extends JpaRepository<Member, Integer> {


    public Member findById(int memberId);

    @Query("SELECT m FROM ApprovalMember m, Position p WHERE m.positionLevel = p.positionLevel AND m.departNo = :departNo ORDER BY p.positionLevel ASC")
    public List<Member> findByDepart(@Param("departNo") int departNo);
}
