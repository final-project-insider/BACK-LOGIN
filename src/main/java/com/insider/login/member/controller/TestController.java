package com.insider.login.member.controller;
import com.insider.login.auth.DetailsMember;
import com.insider.login.common.AuthConstants;
import com.insider.login.common.utils.TokenUtils;
import com.insider.login.member.dto.MemberDTO;
import io.jsonwebtoken.Claims;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('ADMIN')") // ADMIN인 사용자만 접근 가능하다
public class TestController {
    @GetMapping("/test")
    public String test(){
        System.out.println("===== test controller ===== 도착");
        return "test GET";
    }

    @PostMapping("/test")
    public String test2(@RequestHeader("Authorization") String token){
        System.out.println("test controller postMapping 도착 ");
        System.out.println("token: " + token);
        String jwtToken = token.substring(7);

        Claims claims = TokenUtils.getClaimsFromToken(jwtToken);
        Object memberIdObject = claims.get("memberId");

        if (memberIdObject instanceof Integer) {
            int memberId = (Integer) memberIdObject;
            System.out.println("memberId: " + memberId);
            return "memberID: " + memberId;
        } else {
            return "MemberId not found in token";
        }
    }

    /* 1번째 방법 */
    @GetMapping("/getMemberInfo")
    public String getMemberInfo(@RequestHeader(AuthConstants.AUTH_HEADER) String token) {
        System.out.println("token: " + token); // 확인용
        String jwtToken = token.substring(7);
        Claims claims = TokenUtils.getClaimsFromToken(jwtToken);
        Object memberIdObject = claims.get("memberId");

        // memberId가 존재하고 integer일 경우에는
        if (memberIdObject != null && memberIdObject instanceof Integer) {
            int memberId = (Integer) memberIdObject;
            System.out.println("memberId: " + memberId);
            return "memberID: " + memberId;
        } else {
            return "MemberId not found in token";
        }
    }



    /* 2번째 방법 */
    @GetMapping("/getToken")
    public String tokenInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DetailsMember detailsMember = (DetailsMember) authentication.getPrincipal();
        MemberDTO member = detailsMember.getMember();
        System.out.println("member 정보들: " + member);
        return ("member의 정보들: " + member);
//        return ("token 정보: " + token);
    }
}