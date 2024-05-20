package com.insider.login.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    /* 사용자의 잘못된 로그인 시도를 커스텀 하기 위한 handler method */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("CustomAuthFailureHandler 도착");
        JSONObject jsonObject;
        String failMsg;

        if (exception instanceof AuthenticationServiceException) {
            // 사용자의 로그인 또는 인정 처리 과정에서 문제가 발생한다.
            failMsg = "존재하지 않는 사용자입니다";
        } else if (exception instanceof BadCredentialsException) {
            // BadCredentialsException error -> 사용자의 아이디, 또는 비밀번호가 DB에 맞지 않는 경우 발생한다
            failMsg = "아이디 또는 비밀번호가 틀립니다";
        } else if (exception instanceof LockedException) {
            // 계정이 잠긴 경우 발생한다
            failMsg = "잠긴 계정입니다. 관리자에게 문의하세요";
        } else if (exception instanceof DisabledException) {
            // 비활성화 된 계정에서 발생한다
            failMsg = "비활성화된 계정입니다. 관리자에게 문의하세요";
        } else if (exception instanceof AccountExpiredException) {
            // 계정 만료시 발생한다
            failMsg = "만료된 계정입니다. 관리자에게 문의하세요";
        } else if (exception instanceof CredentialsExpiredException) {
            // 자격 증명이 만료된 경우 발생한다
            failMsg = "비밀번호가 만료되었습니다";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            // 보안 컴텍스트에 인증 객첵가 존재하지 않거나 인증 저보가 없는 상태에서 보안처리 된 리소스에 접근하면 발생한다.
            failMsg = "인증 요청이 거부되었습니다";
        } else if (exception instanceof UsernameNotFoundException) {
            // DB에서 사용자의 정보가 없는 경우 발생한다.
            failMsg = "존재하지 않는 이메일입니다";
        } else {
            failMsg = "정의되지 않은 케이스의 오류입니다. 관리자에게 문의해 주세요";
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("failType",failMsg);                      // 인증 실패시 map type으로 정의를 해준다

        jsonObject = new JSONObject(resultMap);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();                 // 응답을 주도록 setting

        out.println(jsonObject);
        out.flush();
        out.close();

    }
}