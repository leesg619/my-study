package com.practice.intro.config;

import com.practice.intro.repository.MemberRepository;
import com.practice.intro.repository.MemoryMemberRepository;
import com.practice.intro.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 자바 코드로 직접 스프링 빈 등록 - 컴포넌트 스캔 (@Service / @Repository) 없이 가능하게함.
 * @Controller 는 그대로쓴다.
 */
@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
