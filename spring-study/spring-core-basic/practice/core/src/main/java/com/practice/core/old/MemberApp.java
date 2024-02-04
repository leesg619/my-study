package com.practice.core.old;

import com.practice.core.member.Grade;
import com.practice.core.member.Member;
import com.practice.core.member.MemberService;

public class MemberApp {
    public static void main(String[] args) {
        // 스프링 없이 자바로 1차 구현 - OCP / DIP 위반 중
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
