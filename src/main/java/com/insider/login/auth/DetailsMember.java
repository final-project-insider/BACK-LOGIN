package com.insider.login.auth;


import com.insider.login.member.dto.MemberDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DetailsMember implements UserDetails {

    private MemberDTO memberDTO;

    public DetailsMember() {}

    public DetailsMember(Optional<MemberDTO> memberDTO) {
        this.memberDTO = memberDTO.get();
    }

    public MemberDTO getMember() {
        return memberDTO;
    }

    public void setMember(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

    /* 권한 정보 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        memberDTO.getRoleList().forEach(role -> authorities.add(() ->role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return memberDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(memberDTO.getMemberId());
    }

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