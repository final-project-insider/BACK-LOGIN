package com.insider.login.commute.repository;

import com.insider.login.commute.entity.CommuteMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommuteMemberRepository extends JpaRepository<CommuteMember, Integer> {

    List<CommuteMember> findByDepartNo(int departNo);

    List<CommuteMember> findByMemberRole(String admin);

    CommuteMember findByMemberId(int memberId);
}
