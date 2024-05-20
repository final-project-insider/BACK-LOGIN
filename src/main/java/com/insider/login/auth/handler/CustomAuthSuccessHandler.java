package com.insider.login.auth.handler;

import com.insider.login.auth.DetailsMember;
import com.insider.login.common.AuthConstants;
import com.insider.login.common.utils.ConvertUtil;
import com.insider.login.common.utils.TokenUtils;
import com.insider.login.member.dto.MemberDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/* 로그인이 성공적으로 되면 토큰은 여기서 발행을 한다 */
@CrossOrigin(origins = "http://localhost:3000")
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        // entity안에 userStatus가 있다... 로그인을 할 때 재직, or 퇴직 여부를 확인을 할려고 사용하는 method을 작성할 것이다
        MemberDTO memberDTO = ((DetailsMember) authentication.getPrincipal()).getMember();                             // getPrincipal()을 사용을 하면 그게 대한 정보를 다 가져오는데, 그것은 DetailsUser에 해당한다. Object이기 때문에 DetailsUser로 downcasting을 해줄 것이고 user 정보를 가져온다
        System.out.println("member에 대한 정보: " + memberDTO);

        // 응답을 하는 것을 모두 다 JSON Object로 만들어서 String JSON으로 반환을 해줘야한다
        JSONObject jsonValue = (JSONObject) ConvertUtil.convertObjectToJsonObject(memberDTO);
        HashMap<String, Object> responseMap = new HashMap<>();                                                   // 반환값 설정
        JSONObject jsonObject;
        if(memberDTO.getMemberStatus().equals("재직")) {                                                             // 구성원이 재직 중이면 -> 로그인 성공
            responseMap.put("userInfo", jsonValue);
            responseMap.put("message","로그인 성공입니다.");
            // 로그인이 성공을 했을 때 token 정보도 같이 줘야한다
            String token = TokenUtils.generateJwtToken(memberDTO);                                                  // token setting하는 logic
            System.out.println("token 정보: " + token); // eg) token 정보: eyJkYXRlIjoxNzE0NDU1NTU2OTM3LCJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJwb3NpdGlvbk5hbWUiOiLslYzrsJQiLCJSb2xlIjoiQURNSU4iLCJpbWFnZSI6IuydtOuvuOyngOqwgCDrk6TslrTqsIgg6rK966GcIiwic3ViIjoiMjQwNDAxNTY4IiwibWVtYmVyU3RhdHVzIjoi7J6s7KeBIiwidXNlck5hbWUiOiLquYDsp4DtmZgiLCJleHAiOjE3MTQ1NDE5NTYsImRlcGFydE5hbWUiOiLsnbjsgqztjIAiLCJtZW1iZXJJZCI6MjQwNDAxNTY4fQ.fsgaqYu7OQGzlsqDd0lINkBIIPWUFpJVwKXBvYaPeeI
            responseMap.put("token", token);
            response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);         // token의 구조가 BEARER token... 이기 때문에 이런 형식으로 추가를 해준다
        } else {                                             // 그렇지 않으면 -> 휴면 상태라고 뜨게 하고 로그인을 못하겠금 막아준다
            responseMap.put("userInfo", jsonValue);
            responseMap.put("message", "휴면 상태의 계정입니다.");
        }

        jsonObject = new JSONObject(responseMap);
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);
        printWriter.flush();
        printWriter.close();
    }
    /*
        <<< front-end >>>
        fetch('http://example.com/login', {
            method: 'POST',
                    body: JSON.stringify({ username: 'example', password: 'password' }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
                .then(response => {
        const token = response.headers.get('Authorization');
            console.log('Token:', token);
            // Save the token in localStorage or sessionStorage for future requests
        });
    */

    /*
      <<< backend >>>
    * String token = response.getHeader(AuthConstants.AUTH_HEADER);
    * */

}