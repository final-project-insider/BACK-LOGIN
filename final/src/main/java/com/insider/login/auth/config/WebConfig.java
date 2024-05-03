package com.insider.login.auth.config;

import com.insider.login.auth.filter.HeaderFilter;
import com.insider.login.auth.interceptor.JwtTokenInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer { // 요청 처리에 대한 설정을 해줄거기 때문에 implement 작성

    /* 정적 자원들의 위치를 알려주는 method (contains the locations within the project where static resources are stored. */
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/static/", "classpath:/public/", "classpath:/", "classpath:/resources/",
            "classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/"
    };

    // 위에 있는 정적 자원들의 접근을 허용을 할 수 있겠금 addResourceHandler를 추가할 것이다
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS); // 정적 자원에 해당하는 요청이나 접근 허용을 하겠금 resourceHandler에 설정한 것
    }

    @Bean
    public FilterRegistrationBean<HeaderFilter> getFilterRegistrationBean() {
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<HeaderFilter>(createHeaderFilter()); // filter 정보를 줄 수 있겠금 해주는 method
        // indicates that the filter should have the lowest possible order, meaning it will be one of the first filters to be executed.
        // setting the order ensures that HeaderFilter is executed early in the filter chain, allowing it to intercept requests and modify responses before other filters or servlets are invoked
        registrationBean.setOrder(Integer.MIN_VALUE);
        // specifies the URL patterns for which the filter should be applied... "/*" -> indicates that the filter should be applied to all URL patterns, meaning that it will intercept all incoming requests
        registrationBean.addUrlPatterns("/*");
        return registrationBean; // register as a bean -> meaning that the servlet container will use this bean to instantiate and configure the "HeaderFilter" according to the specified settings.
    }

    @Bean
    public HeaderFilter createHeaderFilter() {
        return new HeaderFilter();
    }

    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor() {
        return new JwtTokenInterceptor();
    }
}
