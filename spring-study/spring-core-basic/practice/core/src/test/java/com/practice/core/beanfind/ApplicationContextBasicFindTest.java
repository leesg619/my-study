package com.practice.core.beanfind;

import com.practice.core.config.AppConfig;
import com.practice.core.member.MemberService;
import com.practice.core.member.MemberServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new
            AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
    
    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
    
    @Test
    @DisplayName("구체 타입으로 조회 / 구체타입으로 조회해서 쓰는 방식은 하지 않는 게 좋다")
    void findBeanByName2() {
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
    @Test
    @DisplayName("빈 이름으로 조회 안됨")
    void findBeanByNameX() {
        // MemberService memberService = ac.getBean("xxx", MemberService.class);
        Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxx", MemberService.class));
/**
 * org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'xxx' available
 */
    }
}
