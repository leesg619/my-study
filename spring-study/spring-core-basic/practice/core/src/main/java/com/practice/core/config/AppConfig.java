package com.practice.core.config;

import com.practice.core.discount.DiscountPolicy;
import com.practice.core.discount.FixDiscountPolicy;
import com.practice.core.member.MemberRepository;
import com.practice.core.member.MemberService;
import com.practice.core.member.MemberServiceImpl;
import com.practice.core.member.MemoryMemberRepository;
import com.practice.core.order.OrderService;
import com.practice.core.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }


    public OrderService orderService() {
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy()
        );
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }

}
