package com.practice.core.member;

import com.practice.core.member.Member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
