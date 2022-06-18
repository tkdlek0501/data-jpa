package com.ex.datajpa.repository;

import java.util.List;

import com.ex.datajpa.entity.Member;

// 사용자 정의 레포지토리 구현을 위한 인터페이스

public interface MemberRepositoryCustom {
	
	List<Member> findMemberCustom();
	
}
