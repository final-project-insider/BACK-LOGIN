package com.insider.login.auth.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.auth.model.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter { // used for username-password authentication process

    /*
    * CustomAuthenticationFilter intercepts the request and extracts the ID and password from the RequestedBody
    * - it creates an "Authentication"
    * */
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    /*
     * 지정된 url 요청 시 대항 요청을 가로채서 검증 로직을 수행하는 메소드
     * extracts the authentication details from the request, constructs an "Authentication" object and delegates the authentication process to authentication manager
     */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        System.out.println("🥈 reach Authentication ✅ in CustomAuthenticationFilter");

        try {
            authRequest = getAuthRequest(request);
            setDetails(request,authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /*
     * 사용자의 로그인 요청 시 요청 정보를 임시 토큰에 저장하는 메소드
     * extracts id and password from the Request Body (JSON data) and creates token with these credentials
     * */

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws IOException { // IOExceptionL: handle errors
        ObjectMapper objectMapper = new ObjectMapper(); // objectMapper is used to deserialize the JSON data into 'LoginDTO' object

        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        LoginDTO member = objectMapper.readValue(request.getInputStream(), LoginDTO.class);

        return new UsernamePasswordAuthenticationToken(member.getId(), member.getPass());
    }
}
