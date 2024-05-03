package com.insider.login.member.service;

import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAdminMembers() {
        return memberRepository.findByMemberRole("ADMIN");
    }
}
