package com.insider.login.auth.handler;

import com.insider.login.auth.DetailsMember;
import com.insider.login.auth.model.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider { // Provider: 실제 인증을 진행을 해주는 역할이다

    /*
     * id와 pass가 맞으면 token을 반환을 해주는 역할이다
     * */

    @Autowired // 의존성 주업을 받게 해준다
    private DetailsService detailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 입력 받은 id,pass를 조회 하는것은 UserDetails에서 사용을 한다
        System.out.println("🥉 Authentication 도착 ✅ in CustomAuthenticationProvider");

        UsernamePasswordAuthenticationToken loginToken = (UsernamePasswordAuthenticationToken) authentication; // downcasting
        String id = loginToken.getName();                   // 인증대상자가 존재하는지 확인하는 logic (ID)
        String pass = (String) loginToken.getCredentials(); // 인증대상자가 존재하는지 확인하는 logic (PWD) ... object type을 반환을 하기 때문에 String으로 downcasting
        System.out.println("받은 loginToken의 정보: " + loginToken);
        System.out.println("로그인할 때 id 잘 기입을 했는지 확인용: " + id);
        System.out.println("로그인할 때 pass 잘 기입을 했는지 확인용: " + pass);

        // id는 long이기 때문에 long type으로 반환을 해준다

        DetailsMember detailsMember = (DetailsMember) detailsService.loadUserByUsername(id); // 구성원의 정보를 받고

        if(!passwordEncoder.matches(pass, detailsMember.getPassword())) {     // 입력한 비밀번호를 검증을 하는데, 틀릴 시
            throw new BadCredentialsException(pass + "는 틀린 비밀번회입니다");
        }
        return new UsernamePasswordAuthenticationToken(detailsMember, pass, detailsMember.getAuthorities()); // 비밀번호가 일치할 시 3가지의 인자를 반환을 해준다
    }

    // Token type에 따라서 언제 provider를 사용할지 조건을 지정해주는 method
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class); // token type이 usernamePasswordAuthenticationToken에 해당하면 반환을 한다 (true)
    }
}
