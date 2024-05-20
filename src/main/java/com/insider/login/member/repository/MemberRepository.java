package com.insider.login.member.repository;

import com.insider.login.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    boolean existsByMemberId(int memberId);

    List<Member> findByDepartment_DepartNo(int departNo);



}
