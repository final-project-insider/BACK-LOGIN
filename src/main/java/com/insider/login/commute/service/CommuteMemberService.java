package com.insider.login.commute.service;

import com.insider.login.commute.entity.CommuteMember;
import com.insider.login.commute.repository.CommuteMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommuteMemberService {

    private final CommuteMemberRepository commuteMemberRepository;

    public CommuteMemberService(CommuteMemberRepository commuteMemberRepository) {
        this.commuteMemberRepository = commuteMemberRepository;
    }

    public List<CommuteMember> getAdminMembers() {
        return commuteMemberRepository.findByMemberRole("ADMIN");
    }
}
