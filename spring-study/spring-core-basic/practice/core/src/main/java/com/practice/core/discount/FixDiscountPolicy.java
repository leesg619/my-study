package com.practice.core.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.practice.core.annotation.MainDiscountPolicy;
import com.practice.core.member.Grade;
import com.practice.core.member.Member;

@Component
@MainDiscountPolicy
public class FixDiscountPolicy implements DiscountPolicy {

	private int discountFixAmount = 1000; // ex. 천원 고정 할인

	@Override
	public int discount(Member member, int price) {
		if (member.getGrade() == Grade.VIP) {
			return discountFixAmount;
		} else {
			return 0;
		}
	}
}
