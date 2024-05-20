package com.insider.login.common.utils;

import com.insider.login.auth.DetailsMember;
import com.insider.login.member.dto.MemberDTO;
import com.insider.login.member.dto.UpdatePasswordRequestDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    private static String jwtSecretKey;     // secret key
    private static Long tokenValidateTime;  // token 만료시간

    @Value("${jwt.key}") // Application file에서 설정 정보를 불러와서 setting할 것
    public void setJwtSecretKey (String jwtSecretKey) {
        TokenUtils.jwtSecretKey = jwtSecretKey;                            // jwt.key에서 값을 받아와서 setting한다
    }

    @Value("${jwt.time}")
    public void setTokenValidateTime (Long tokenValidateTime) { // 정적 변수이기 때문에 class명.field명
        TokenUtils.tokenValidateTime = tokenValidateTime;
    }

    /* header는 "Bearer " + token이기 때문에 token만 빼오기 위해서 작성하는 logic */
    public static String splitHeader(String header) {
        if (header != null) {
            return header.split(" ")[1];
        } else {
            return null;
        }
    }

    public static boolean isValidToken(String token) {
//        System.out.println("token만 잘 가져왔는지: " + token); // 확인용
        try {
            Claims claims = getClaimsFromToken(token);     // 정상적으로 작동이 되면
//            System.out.println("Claims에 담은 정보들: " + claims);
            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {                 // token이 null인 경우
            e.printStackTrace();
            return false;
        }
    }

    public static Claims getClaimsFromToken (String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey)) // secret key를 넣어서 복호화 setting해주고
                .parseClaimsJws(token)                                            // 값을 제대로 받았으면 -> header, payload, signature로 분리를 한다
                .getBody();
//        System.out.println("Claims: " + claims);
        return claims;
    }

    /* token을 생성하는 method */
    public static String generateJwtToken(MemberDTO member) {
        // 만료시간도 추가해야한다
        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);
        System.out.println("expire time: " + expireTime);
        System.out.println("로그인한 구성원의 정보 (in Token Utils): " + member);

        /*
         * token에서는 header, payload, signature가 존재를 하기 때문에 각각 값들을 입력을 해줘야 한다
         * - header: setHeader
         * - payload: setSubject
         * - signature: signWith
         * */
        JwtBuilder builder = Jwts.builder()                             // used to construct JWTs
                .setHeader(createHeader())
                .setClaims(createClaims(member))                        // payload에다가 user data를 넣어준다
                .setSubject("" + member.getMemberId())
                .signWith(SignatureAlgorithm.HS256, createSignature())  // 반환 받은 key값으로 sign in
                .setExpiration(expireTime);                             // 만료시간

        return builder.compact();                                       // token을 String형식으로 바꿔서 반환을 해준다
    }

    /* token의 header를 설정하는 method */
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("type", "jwt");                      // json type
        header.put("alg", "HS256");                     // algorithm 방식
        header.put("date", System.currentTimeMillis()); // 만들어준 시간
        System.out.println("header 정보: " + header);

        return header;
    }

    private static Map<String, Object> createClaims(MemberDTO memberDTO) {
        Map<String, Object> claims = new HashMap<>();
        System.out.println("member 정보: " + memberDTO);

        /* 토큰을 통해서 쉽게 정보들을 꺼낼 수 있는 logic */
        claims.put("departNo", memberDTO.getDepartmentDTO().getDepartNo());         // 부서 번호
        claims.put("departName", memberDTO.getDepartmentDTO().getDepartName());     // 부서명
        claims.put("positionName", memberDTO.getPositionDTO().getPositionName());   // 직급명
        claims.put("imageUrl",memberDTO.getImageUrl());                             // 이미지 경로
        claims.put("name", memberDTO.getName());                                    // 구성원 이름
        claims.put("role", memberDTO.getRole());                                    // 구성원 직책
        claims.put("memberId", memberDTO.getMemberId());                            // 구성원 사번
        claims.put("memberStatus", memberDTO.getMemberStatus());                    // 현상태 (재직, ...)
//        claims.put("employedDate", memberDTO.getEmployedDate());                    // 입사 일자

        System.out.println("Claims에 담은 정보들: " + claims);                         // 확인용
        return claims;
    }

    /* JWT 서명을 발급하는 method */
    private static Key createSignature() {                                              // key는 signature를 가지고 확인을 한다
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);         // secret key -> parsed into byte array, which is used for signing JWTs
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;               // set to HMAC-SHA256, widely used for generating secure cryptographic signatures
        return new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());         // constructs specification for the secret key, which can be used for cryptographic operations

        /* in summary...
         * - takes Base64-encoded secret key, convert into byte array, select HS256 signature algorithm, and construct a secret key specification using the provided secret key bytes and selected algorithm.
         * - The resulting 'Key' is then used for signing JTWs.
         * */
    }

    public static MemberDTO getTokenInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DetailsMember detailsMember = (DetailsMember) authentication.getPrincipal();
        MemberDTO member = detailsMember.getMember();
        return member;
    }


    /*
     * 이렇게 method를 분리 시킨 이유:
     * 1. private으로 접근을 제한을 하기 위해서
     * 2. debugging 하기 편하다
     * */
}