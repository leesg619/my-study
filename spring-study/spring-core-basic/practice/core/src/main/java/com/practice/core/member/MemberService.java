package com.practice.core.member;

import com.practice.core.member.Member;

public interface MemberService {
    void join(Member member);
    Member findMember(Long memberId);
}