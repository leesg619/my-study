package com.practice.intro.config;

import com.practice.intro.repository.JdbcMemberRepository;
import com.practice.intro.repository.JdbcTemplateMemberRepository;
import com.practice.intro.repository.MemberRepository;
import com.practice.intro.repository.MemoryMemberRepository;
import com.practice.intro.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 자바 코드로 직접 스프링 빈 등록 - 컴포넌트 스캔 (@Service / @Repository) 없이 가능하게함.
 * @Controller 는 그대로쓴다.
 */
@Configuration
public class SpringConfig {

    // DataSource는 데이터베이스 커넥션을 획득할 때 사용하는 객체다. 스프링 부트는 데이터베이스 커넥션 정
    // 보를 바탕으로 DataSource를 생성하고 스프링 빈으로 만들어둔다. 그래서 DI를 받을 수 있다
    private DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
