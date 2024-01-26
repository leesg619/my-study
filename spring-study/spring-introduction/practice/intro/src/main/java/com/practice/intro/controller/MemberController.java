package com.practice.intro.controller;

import com.practice.intro.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    //스프링 빈이 올라갈 때 생성자에서 컨테이너가 컨테이너 속에서 MemberService 를 찾아서 자동 주입
    //따라서 MemberService 도 스프링 빈이 등록돼있어야함. @Service
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
