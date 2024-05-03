package com.insider.login.auth.config;

import com.insider.login.auth.filter.CustomAuthenticationFilter;
import com.insider.login.auth.filter.JwtAuthorizationFilter;
import com.insider.login.auth.handler.CustomAuthFailureHandler;
import com.insider.login.auth.handler.CustomAuthSuccessHandler;
import com.insider.login.auth.handler.CustomAuthenticationProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/*
* @EnableGlobalMethodSecurity(prePostEnabled = true)
* -> enable method-level security with pre- and post- invocation authorization.
* ::: prePostEnabled = true: allows us to use @PreAuthorize and @PostAuthorize to secure methods based on conditions defined in these annotations
* "" we're telling Spring Security to enable method-level security based on annotations like @PreAuthorize and @PostAuthorize across our apps
* this allows us to control access to our methods based on specific criteria in our apps
*
* 해삭: @EnableGlobalMethodSecurity(prePostEnabled = true) -> annotation을 사용해서 class에 접근을 할 수 있는 권한을 줄 수 있다~
* */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // annotation을 통해서 권환 설정을 가능하겠금 해주는 것이 바로 이 annotation이다
public class WebSecurityConfig {

    // static resources -> security filter를 거치지 않는 logic / 설정이다
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }



    // 가장 중점
    @Bean // bean annotation을 해주면 자동으로 Security Filter에 알맞게 customization을 해줄 수 있다
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 내부에서는 특정한 요청이 들어왔을 때 authorization (권한)에 맞는 user (사용자에게) resource를 보여주는 logic 작성할 것
        http.csrf(AbstractHttpConfigurer::disable)  // disables CSRF(Cross-Site Request Forgery) protection. In JWT... unnecessary because JWTs are not vulnerable to CSRF attacks (해석 : React에서는 front와 back이 구분이 (server가 다름) 되어있기 대문에 session이 아니기 때문에 disabled 한 것이다
                .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class) // responsible for authenticating requests using JWT tokens and determining whether the user has the necessary permissions to access protected resources.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // means that the app doesn't create or use HTTP sessions for storing user authentication info... this is typical in JWT as the token itself contains all the necessary authentication details
                .formLogin(form -> form.disable()) // security를 사용을 하면 기본 로그인창을 disable한 것이다
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // responsible for authenticating users using custom authentication mechanisms such as username/ password
                .httpBasic(basic -> basic.disable()); // 기본 인증을 요청하는 것. HTTP basic authentication is not needed in a stateless app using JWT for authentication (해석: 불필요하다)

        /*
        * -- addFilterBefore() parameters의 정의 --
        * 1. jwtAuthorizationFilter()
        * -> token 인증을 한 다음에 사용자 인증 정보를 setting 해주는 역할
        * 2. customAuthenticationFilter()
        * -> login에 대한 url설정과 각각 로그인 인증 성공, 실패 시 처리해주는 handler method 등록
        *
        * BasicAuthenticationFilter & UsernamePasswordAuthenticationFilter -> 상속을 받아서 customize할 것
        * */

        /*
        * -- addFilterBefore ... STATELESS (browser session은 우리가 관리를 하지 않기 때문이다)
        * */

        return http.build(); // 최종적으로 반환을 해줘야한다
    }

    /*
    * <정리>
    * csrf disabled -> 보완성이 낮아짐. final에서는 front, back server가 구분되어 있기 때문에 disable해도 상관이 없다.
    * 하지만 session 사용하는 server는 able를 해야 보완성이 높아진다
    * this is commonly done to avoid unnecessary complexity and overhead, especially in stateless authentication scenarios like those using JWT for authentication
    *
    * */

    /* 사용자가 요청 (request)시 수행되는 method */
    /*
    * BasicAuthenticationFilter를 상속 받아서 우리가 원하는대로 override 하겠금 만들 것이다 (SecurityFilter 참조할 것!)
    * BasicAuthenticationFilter는 기본적으로 AuthenticationManager가 필요하고, 이것은 또 AuthenticationProvider가 필요하다
    * */
    private JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(authenticationManager());
    }

    /* Authentization의 인증 메소드를 제공하는 manager (Provider)를 반환하는 method */
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthenticationProvider());
    }

    /* 사용자의 id와 password를 DB와 비교하여 검증하는 handler method */
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(); // 실질적으로 인증해주는 역할을 한다 (임시 토큰을 받아서 DB에랑 맞는지 확인해주는 logic)
    }

    /* 비밀번호 암호화하는 encorder를 반환하는 method */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 사용자의 인증 요청을 가로채서 로그인 logic을 수행하는 filter를 반환하는 method
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {
        // authenticationManager가 필요함
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/login"); // login에 접근 url 정보
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthLoginSuccessHandler()); // 각각 성공할 때 접근할 handler
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthLoginFailureHandler()); // 각각 실패할 때 접근할 handler
        customAuthenticationFilter.afterPropertiesSet(); // properties를 다 setting 해주는 것 -> filter를 구성해주는 것
        return customAuthenticationFilter;
        // set: extend하면 해결
    }

    /* 사용자 정보가 맞을 경우 (= 로그인 성공 시) 수행하는 handler를 반환하는 method */
    private CustomAuthSuccessHandler customAuthLoginSuccessHandler() {
        return new CustomAuthSuccessHandler();
    }

    private CustomAuthFailureHandler customAuthLoginFailureHandler() {
        return new CustomAuthFailureHandler();
    }
}
