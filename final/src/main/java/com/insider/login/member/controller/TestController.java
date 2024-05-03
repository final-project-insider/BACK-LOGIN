package com.insider.login.member.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('ADMIN')") // ADMIN인 사용자만 접근 가능하다
public class TestController {
    @GetMapping("/test")
    public String test(){
        System.out.println("===== test controller ===== 도착");
        return "test GET";
    }

    @PostMapping("/test")
    public String test2(){
        return "test POST";
    }
}