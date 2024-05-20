package com.insider.login.auth.interceptor;

import com.insider.login.common.AuthConstants;
import com.insider.login.common.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.rmi.RemoteException;

public class JwtTokenInterceptor implements HandlerInterceptor { // 구현체로 만들어 줌
    // interceptors in Spring MVC are invoked before login controller is called

    @Override // 요청이 들어왔을 때 가로채서, token의 유효성을 검사 하는 method이다..!
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("JwtTokenInterceptor 도착");
        // token을 가지고 있는애
        String header = request.getHeader(AuthConstants.AUTH_HEADER); // Authorization이 token을 가지고 있다.
        String token = TokenUtils.splitHeader(header); // BEARER가 앞에 붙어있기 때문에 그것을 떼서 token값만 가져오는 logic

        System.out.println("header내용: " + header);
        System.out.println("token내용: " + token);

        // token을 반환 받으면
        if (token != null) {
            // token이 존재를 하면 유효성 검사를 할 것이다
            if(TokenUtils.isValidToken(token)) { // 위조 됐거나 시간이 만료 되지 않은 토큰을 검증할 것이다
                return true; // true를 반환을 해줘야 interceptor를 넘어서 다른 작업이 진행될 수 있다
            } else {
                throw new RemoteException("token이 만료 되었습니다.");
            }
        } else {
            throw new RemoteException("token 정보가 없습니다.");
        }
    }
}
