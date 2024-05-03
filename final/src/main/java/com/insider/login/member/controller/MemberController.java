package com.insider.login.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.insider.login.auth.model.dto.LoginDTO;



import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import com.insider.login.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Objects;

@RestController
public class MemberController {

    @Autowired
    private MemberRepository userRepository; // 원래 service에 작성을 하는 것인데 test를 하기 위해서

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping("/regist")
    public String signup(@RequestBody Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호
        member.setMemberStatus("재직"); // 재직 or 다른 것들... setting 해줘야 한다!!! NOTE 할것!!

        // JSON형식으로 LocalDate을 저장을 하기 위한 block of code
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LocalDate localDate = LocalDate.now();

//        try {
//            String jsonDate = objectMapper.writeValueAsString(localDate);
//            System.out.println("JSON Date: " + jsonDate);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        member.setEmployedDate(localDate); // 등록한 날짜 가입


        Member savedMember = memberService.saveMember(member);
        System.out.println("회원 가입한 구성원 정보: " + savedMember);

        if(Objects.isNull(savedMember)) { // 비어있으면 실패
            System.out.println("회원가입 실패 😭");
            return "회원가입 실패";
        } else {                    // 다 작성을 했으면 구성원 가입 성공
            System.out.println("회원가입 성공 🙂");
            return "회원 가입 성공!";
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        System.out.println("login controller 도착");
        int memberIdInfo = loginDTO.getId();
        String memberPasswordInfo = loginDTO.getPass();

        System.out.println("아이디 정보: " + memberIdInfo);
        System.out.println("비밀번호 정보: " + memberPasswordInfo);

        return null;
    }
}
