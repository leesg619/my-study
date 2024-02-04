package com.practice.core.old;

import com.practice.core.discount.DiscountPolicy;
import com.practice.core.discount.FixDiscountPolicy;
import com.practice.core.member.Member;
import com.practice.core.member.MemberRepository;
import com.practice.core.member.MemoryMemberRepository;
import com.practice.core.order.Order;
import com.practice.core.order.OrderService;

public class OrderServiceImpl implements OrderService {
    // 스프링 없이 자바로 1차 구현 - OCP / DIP 위반 중

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);

    }
}
