package com.practice.core.old;

import com.practice.core.member.Member;
import com.practice.core.member.MemberRepository;
import com.practice.core.member.MemberService;
import com.practice.core.member.MemoryMemberRepository;

public class MemberServiceImpl implements MemberService {
    // 스프링 없이 자바로 1차 구현 - OCP / DIP 위반 중
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    public void join(Member member) {
        memberRepository.save(member);
    }
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}