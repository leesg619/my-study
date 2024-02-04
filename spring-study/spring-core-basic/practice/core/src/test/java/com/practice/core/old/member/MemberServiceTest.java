package com.practice.core.old.member;

import com.practice.core.member.Grade;
import com.practice.core.member.Member;
import com.practice.core.member.MemberService;
import com.practice.core.old.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService = new MemberServiceImpl();
    @Test
    void join() {
    	// given
        Member member = new Member(1L, "memberA", Grade.VIP);

    	// when
        memberService.join(member);

    	// then
        Member findMember = memberService.findMember(1L);
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}
