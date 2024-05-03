package com.insider.login.auth;

import com.insider.login.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DetailsMember implements UserDetails {

    private Member member;

    public DetailsMember() {}

    public DetailsMember(Optional<Member> member) {
        this.member = member.get();
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    /* 권한 정보 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        member.getRoleList().forEach(role -> authorities.add(() -> role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(member.getMemberId()); // 아이디는 int이기 때문에 String값으로 변경을 해줘야 한다
    }

//    @Override
//    public long getUsername() {
//        return member.getMemberId(); // 아이디는 int이기 때문에 String값으로 변경을 해줘야 한다
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
