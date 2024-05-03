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
        System.out.println("id가 잘 넘어왔는지: " + id + " in DetailsService class"); // id가 잘 넘어왔는지 확인용
        // 정보를 조회를 해와서 사용 하는 logic을 작성을 해야한다
        // eg) ID를 가지고 사용자의 정보를 불러와야 한다 ((JPA를 사용 하기 때문에 DB에 접근을 하기 위해서 Repository만들 것

        // AuthenticationServiceException에는 String 타입이 필요하기 때문에 id를 String으로 반환을 할 것
        int intTypeId = Integer.parseInt(id);

        // id가 존재 하지 않으면
        if (intTypeId == 0) {
            throw new AuthenticationServiceException((id + " is empty!"));
        } else {
            System.out.println("DB에 가입을 한 구성원의 정보가 있는지 확인");
            return memberService.findMember(intTypeId) // userId                                                     // Optional: nullPointerException을 방지해준다.. 그렇기 때문에 여러가지의 예외방지를 위해서 여러가지 기능들을 제공 하는데
                    .map(data -> new DetailsMember(Optional.of(data)))                                  // map: 하나씩 본다 ... of(data) -> data가 가지고 있는 null이 아닌 값들을 반환을 해서 DetailsUser로 만들어준다
                    .orElseThrow(() -> new AuthenticationServiceException(id));
        }
        // return null; unreachable statement
    }
}
