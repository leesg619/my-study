package com.practice.core.beanfind;

import com.practice.core.config.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBeans() {
    	// given
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        // when
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + " ||| object = " + bean);
        }
/**
name = org.springframework.context.annotation.internalConfigurationAnnotationProcessor ||| object = org.springframework.context.annotation.ConfigurationClassPostProcessor@32c726ee
name = org.springframework.context.annotation.internalAutowiredAnnotationProcessor ||| object = org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor@22f31dec
name = org.springframework.context.annotation.internalCommonAnnotationProcessor ||| object = org.springframework.context.annotation.CommonAnnotationBeanPostProcessor@34c01041
name = org.springframework.context.event.internalEventListenerProcessor ||| object = org.springframework.context.event.EventListenerMethodProcessor@76f4b65
name = org.springframework.context.event.internalEventListenerFactory ||| object = org.springframework.context.event.DefaultEventListenerFactory@c94fd30
name = appConfig ||| object = com.practice.core.config.AppConfig$$SpringCGLIB$$0@36328d33
name = memberService ||| object = com.practice.core.member.MemberServiceImpl@2c4d1ac
name = orderService ||| object = com.practice.core.order.OrderServiceImpl@7f0d96f2
name = memberRepository ||| object = com.practice.core.member.MemoryMemberRepository@545b995e
name = discountPolicy ||| object = com.practice.core.discount.FixDiscountPolicy@76a2ddf3
 */
    }

    @Test
    @DisplayName("애플리케이션 빈만 출력하기")
    void findApplicastionBean() {
        // given
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        // when
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            //Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
            //Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + " ||| object = " + bean);
            }
        }
/**
* name = appConfig ||| object = com.practice.core.config.AppConfig$$SpringCGLIB$$0@32c726ee
name = memberService ||| object = com.practice.core.member.MemberServiceImpl@22f31dec
name = orderService ||| object = com.practice.core.order.OrderServiceImpl@34c01041
name = memberRepository ||| object = com.practice.core.member.MemoryMemberRepository@76f4b65
name = discountPolicy ||| object = com.practice.core.discount.FixDiscountPolicy@c94fd30*
 */
    }
}
