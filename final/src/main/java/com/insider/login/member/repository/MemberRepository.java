package com.insider.login.member.repository;

import com.insider.login.commute.entity.CommuteMember;
import com.insider.login.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> { // Integer -> memberId 때문에

    Optional<Member> findMemberByMemberId(int id);

    /*
    * 반환할 entity type: User
    * key값 type: Integer (User의 memberId)
    *
    * */

    // better to use "Integer" than primitive type "int" -> this allows us to handle "null" values more efficiently
//    Optional<Member> findMemberByMemberId(memberId); // findUserById : Optional객체를 반환을 하기 때문에 nullPointException을 방지를 할 수 있게 도움을 준다
//    Optional<Member> findById(Long memberId);
}
