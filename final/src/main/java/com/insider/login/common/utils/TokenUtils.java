package com.insider.login.common.utils;

import com.insider.login.member.entity.Member;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    public void setJwtSecretKey (String jwtSecretKey) { // 정적 변수이기 때문에 class명.field명
        TokenUtils.jwtSecretKey = jwtSecretKey;
    }

    @Value("${jwt.time}")
    public void setTokenValidateTime (Long tokenValidateTime) { // 정적 변수이기 때문에 class명.field명
        TokenUtils.tokenValidateTime = tokenValidateTime;
    }

    // 다른 곳에서 사용을 할 것이기 대문에 public static
    public static String splitHeader(String header) { // BEARER를 떼는 method이다
        // 만약에 header에 값이 없으면 -> nullPointerException error가 발생할 것이다. 그렇기 대문에 그것에 대한 조건문을 작성을 해야한다..!

        // if(!header.equals("")) {
        if (header != null) { // 보통 header : "Bearer " + token 이기 때문에
            return header.split(" ")[1];                // "Bearer "를 split해서 1st index인 token만 반환을 해주면 된다
            } else {
                return null;
        }
    }

    public static boolean isValidToken(String token) {
        // token을 받아서 boolean 값을 반환할 method... 그러기 위해서는 token을 받아서 복호화를 시켜야한다. 왜냐? 복호화를 한다는 것은 token이 존재를 하기 때문이다
        System.out.println("is valid token: " + token); // 여기까지는 okay..
        try {
            Claims claims = getClaimsFromToken(token); // 정상적으로 작동이 되면
            System.out.println("Things that are stored in Claims: " + claims);
            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) { // token이 null인 경우
            e.printStackTrace();
            return false;
        }
    }

    public static Claims getClaimsFromToken (String token) {
        // Jwts parsing을 해서, 복호화를 하고 data를 가지고 온다
        // setSigningKey -> binary로 encoding하는 방식이다
        // parseClaims -> token을 비교한다
//        return Jwts.parser()
//                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey)) // secret key를 넣어서 복호화 setting해주고
//                .parseClaimsJws(token) // 값을 제대로 받았으면 -> header, payload, signature로 분리를 한다
//                .getBody(); // payload의 값을 Claims로 반환을 한다 (return)
//        System.out.println("😭😭😭😭😭😭 claims의 문제..");

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey)) // secret key를 넣어서 복호화 setting해주고
                .parseClaimsJws(token) // 값을 제대로 받았으면 -> header, payload, signature로 분리를 한다
                .getBody();
//        System.out.println("😭😭😭" + claims);
        return claims;
    }

    /* token을 생성하는 method */
    public static String generateJwtToken(Member member) {
        // 만료시간도 추가해야한다
        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);
        System.out.println("expire time: " + expireTime);
        System.out.println("generateJwtToken의 member정보: " + member);

        // token에 대한 setting
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())                              // token은 header, payload, 그리고 signature로 구성이 되어있기 때문에 각 setting을 해줘야 한다
                .setClaims(createClaims(member))                        // payload에다가 user data를 넣어준다
                .setSubject("insider's token: " + member.getMemberId()) // token의 제목을 넣어준다
                .signWith(SignatureAlgorithm.HS256, createSignature())  // HS256 형식으로 암호화를 해준다
                .setExpiration(expireTime);                             // 만료시간
        System.out.println("builder의 정보: " + builder);

        return builder.compact(); // token을 String형식으로 바꿔서 반환을 해준다
    }

    /* token의 header를 설정하는 method */
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        // token에 대한 정보들이 들어간다
        header.put("type", "jwt");                      // json type
        header.put("alg", "HS256");                     // algorithm 방식
        header.put("date", System.currentTimeMillis()); // 만들어준 시간

        System.out.println("header 정보: " + header);

        return header;
    }

    /* 사용자 정보를 기반으로 claim을 생성하는 method */
    private static Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        System.out.println("member 정보: " + member);

        claims.put("departName",member.getDepartment().getDepartName());    // 부서 이름
        claims.put("positionName", member.getPosition().getPositionName()); // 직급
        claims.put("image", member.getImage().getMemberImagePath());        // 이미지 경로
        claims.put("userName", member.getName());   // claims에다가 정보를 입력하는 것들...
        claims.put("Role", member.getRole());
        claims.put("memberId", member.getMemberId());

        System.out.println("🧥🧥🧥🧥🧥🧥 claims에 담은 memberId 정보: " + claims.get("memberId")); // 확인용
//        System.out.println("claims의 정보: " + claims);
//        System.out.println(claims.get("token"));
//        claims.put("Time", LocalTime.now());
        /* 꺼내오는 정보들 예시...*/
//        System.out.println("Claims의 정보: " + claims);
//        System.out.println("구성원의 사진: " + claims.get("image"));
//        System.out.println("구성원의 직급명: " + claims.get("positionName"));

        return claims;
    }

    /* JWT 서명을 발급하는 method */
    private static Key createSignature() {                                              // key는 signature를 가지고 확인을 한다
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);         // secret key를 암호화 시켜서
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());   // secret key spec을 반환을 해준다
    }

    /*
    * 이렇게 method를 분리 시킨 이유:
    * 1. private으로 접근을 제한을 하기 위해서
    * 2. debugging 하기 편하다
    * */
}
