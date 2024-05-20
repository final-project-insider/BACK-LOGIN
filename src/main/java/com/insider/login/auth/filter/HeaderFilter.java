package com.insider.login.auth.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HeaderFilter implements Filter { // CORS 설정을 위한 filter 설정 class (위조를 막기 위한 filter settings이다)

    @Override // 필수, called by the servlet container each time a request / response pair is passed through the filter chain. it intercepts each requests before they are sent to the servlet or JSPm and outgoing response before they are sent back to the client
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("== HeaderFilter ==");
        HttpServletResponse res = (HttpServletResponse) servletResponse; // downcasting
        res.setHeader("Access-Control-Allow-Origin", "*");  // 다른 외부 요청의 응답을 허용할 것인가? * -> 전체 허용 (분리된 front-server에서 요청을 받아올 것이기 때문에)
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE"); // 외부 요청에 허용할 method
        res.setHeader("Access-Control-Max-Age", "3600"); // caching 허용 시간 (specifies how long the results of preflight request (OPTIONS) can be cached (3600 : 1hour))
        res.setHeader("Access-Control-Allow-Headers",
                "X-Requested-With, Content-Type, Authorization, X-XSRF-token"); // header에 담길 수 있는 type들
        res.setHeader("Access-Control-Allow-Credentials", "false"); // 자격 증명을 허용할 것인지? (specifies whether the response to the request can be exposed on the page. false -> not allowed to be sent with the requests)
        filterChain.doFilter(servletRequest, res); // 내가 받은 request와 response를 전달을 하면서 다음에 진행할 filter가 있으면 filter, 없으면 mapping이 되는 handlerMethod를 호출 해주는 역할

//        System.out.println("res에 대한 정보 출력: " + res);
    }
}
