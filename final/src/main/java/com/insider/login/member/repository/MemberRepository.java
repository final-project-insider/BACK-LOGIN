package com.insider.login.member.repository;

import com.insider.login.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> { // Integer -> memberId 때문에
    Optional<Member> findByMemberId(int id);

}
