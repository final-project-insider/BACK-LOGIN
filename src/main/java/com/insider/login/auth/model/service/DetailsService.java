package com.insider.login.auth.model.service;

import com.insider.login.auth.DetailsMember;
import com.insider.login.member.service.MemberService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class  DetailsService implements UserDetailsService { // 이 class를 사용을 하기 위해서는

    private final MemberService memberService;

    public DetailsService (MemberService memberService) {
        this.memberService = memberService;
    }

    /*
     * 로그인 요청 시 사용자의 id를 받아 DB에서 사용자 정보를 가져오는 method
     * loadUserbyUsername: represents the details of the user
     *
     * UserDetails: provides methods to access user-related info such as username, password, authorities (roles), account status and etc...
     * */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        // Check if id is null
        if (id.equals("0")) {
            throw new AuthenticationServiceException("ID is null!");
        }

        try {
            int intTypeId = Integer.parseInt(id);
            System.out.println("ID is an integer: " + intTypeId);

            // DB에 가입을 한 구성원의 정보가 있는지 확인
            return memberService.findMember(intTypeId)
                    .map(data -> new DetailsMember(Optional.of(data)))
                    .orElseThrow(() -> new AuthenticationServiceException("Member not found for ID: " + intTypeId));
        } catch (NumberFormatException e) {
            System.out.println("ID is not an integer: " + id);
            throw new AuthenticationServiceException("ID is not a valid integer: " + id);
        }
    }

}