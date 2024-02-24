package com.practice.core.autowired;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import com.practice.core.member.Member;

class AutowiredTest {

	@Test
	@DisplayName("테스트")
	void AutowiredOption() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
	}

	static class TestBean {

		// Member는 스프링 빈이 아니다
		// setNoBean1() 은 @Autowired(required=false) 이므로 호출 자체가 안된다.
		@Autowired(required = false)
		public void setNoBean1(Member noBean1) {
			System.out.println("noBean1 = " + noBean1);
		}

		//null 호출
		@Autowired
		public void setNoBean2(@Nullable Member member) {
			System.out.println("setNoBean2 = " + member);
		}

		//Optional.empty 호출
		@Autowired(required = false)
		public void setNoBean3(Optional<Member> member) {
			System.out.println("setNoBean3 = " + member);
		}
	}

}
