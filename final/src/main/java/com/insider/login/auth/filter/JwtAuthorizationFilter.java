package com.insider.login.auth.filter;

import com.insider.login.auth.DetailsMember;
import com.insider.login.common.AuthConstants;
import com.insider.login.common.utils.TokenUtils;
import com.insider.login.common.utils.MemberRole;
import com.insider.login.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    /* 토큰 인증이 성공적으로 처리 됐으면, 사용자 정보를 그 토큰에 setting 해주는 내용를 처리하게 된다 */

    /* 토큰 관련 Exception 발생 시 예외 내용을 담은 객체 반환하는 method */
    // 인증된 사용자만 접근 가능하겠금 해야함
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /* 원하는 방식으로 customization을 하기 위해서 overriding을 */

    /* 토큰 검증을 한 다음에 filter가 내부적으로 동작을 하는 method */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // we are able to intercept every requests and responses and provide new data within the response. (해석: 어떤 요청이 들어왔을 때 우선 인증을 할 것이고, 그 인증이 완료되면 보통 인증정보들이 SecurityContextHolder에 가지고있다. 그렇기 때문에 거기로 들어가기 위한 coding을 할것이다)

        System.out.println("===== JwtAuthorizationFilter 도착 =====");
        List<String> roleLessList = Arrays.asList("/regist");

        // 인증은 했지만 권한이 필요 없는 resource들은 그냥 다음 동작으로 넘어간다... 하지만 권한이 필요한 resource면 -> SecurityContextHolder에 권한 정보를 같이 줘서, 거기에 접근을 할 수 있게 해줘야 한다
        if (roleLessList.contains((request.getRequestURI()))) {
            chain.doFilter(request, response);
            return;
        }
        // ContextHolder에 setting을 하기 위해서는 token이 유효 하는지 확인을 해야한다
        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        /* 확인용 */
        if (header != null) {
            System.out.println("header의 정보: " + header);
        } else {
            System.out.println("header가 존재하지 않습니다");
        }

        try {
            if (header != null && !header.equalsIgnoreCase("")) {
                String token = TokenUtils.splitHeader(header);
                System.out.println("token의 정보: " + token);

                // token이 유효하는지
                if (TokenUtils.isValidToken(token)) {
                    System.out.println("token은 유효하다.. token의 정보: " + token);
                    Claims claims = TokenUtils.getClaimsFromToken(token); // claims : token에 있는 정보들

                    System.out.println("Claims에 대한 정보: " + claims);
                    // ContextHolder에 setting해줄 DetailsUser
                    DetailsMember authentication = new DetailsMember();
                    Member member = new Member();
                    System.out.println("😭😭😭😭😭😭😭");
//                  int memberId = claims.get("memberId", Integer.class); // 로그인할 때 입력한 Id를 가져온다. memberId는 int이기 떼문에 Integer.class 선정
//                  user.setMemberId(Integer.parseInt(claims.get(memberId).toString())); // ????????
                    member.setName(claims.get("userName").toString());
                    member.setRole(MemberRole.valueOf(claims.get("Role").toString()));     // ???????? 02-jwt-security-03.mp4 40분쯤...!
                    authentication.setMember(member);
                    System.out.println("member정보: " + member);

                    /*
                     * ContextHolder에 setting
                     * authenticated : 인증 성공난 후 (SecurityContextHolder가 관리하는 SecurityContext에 저장될 authentication, token, getAuthorities 객체를 생성해서 넘겨준다
                     * type -> AbstractAuthenticationToken이다
                     * */
                    AbstractAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(authentication, token, authentication.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));     // 요청 정보를 가지고 details을 만들어서 token에다가 setting해주는 내용

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken); // SecurityContextHolder에다가 인증된 내용을 setting 해준다
                    chain.doFilter(request, response);
                } else {
                    throw new RuntimeException("token이 유효하지 않습니다.");
                }
            } else {
                throw new RuntimeException("token이 존재하지 않습니다");
            }
        } catch (Exception e) {
            // 예외 발생 시
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            // 반환은 JSONObject
            JSONObject jsonObject = jsonResponseWrapper(e);
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }

    /* 토큰 관련 Exception 발생 시 예외 내용을 담은 객체 반환하는 메소드 */
    // 각각 error에 어떤 error인지 판별을 하고 메시지를 넣기 위해 따로 쓴 것이다
    private JSONObject jsonResponseWrapper(Exception e) {
        String resultMsg = "";

        if(e instanceof ExpiredJwtException) {
            resultMsg = "Token Expired";
        } else if (e instanceof SignatureException) {
            resultMsg = "Token SignatureException";
        } else if (e instanceof JwtException) {
            resultMsg = "Token Parsing JwtException";
        } else {
            resultMsg = "other Token error";
        }

        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status",401);
        jsonMap.put("message",resultMsg);
        jsonMap.put("reason",e.getMessage());

        return new JSONObject(jsonMap);
    }
}
