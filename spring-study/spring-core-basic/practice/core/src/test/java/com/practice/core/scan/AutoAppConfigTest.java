package com.practice.core.scan;
import com.practice.core.AutoAppConfig;
import com.practice.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.assertj.core.api.Assertions.*;
public class AutoAppConfigTest {

    @Test
    void basicScan() {
        /**
         *  AutoAppConfig 를 com/practice/core/config package에 위치시키면 타 @Component 제대로 못찾음
         *  basePackages 를 설정하면 해결 (설정하지 않을 경우 컴포넌트 스캔 동작시 하위 패키지만 탐색해서 그런 것.)
         */

        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
