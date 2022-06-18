package com.ex.datajpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.ex.datajpa.entity.Member;

import lombok.RequiredArgsConstructor;

// TODO: 비핵심 쿼리 레포지토리
// 핵심 비즈니스 로직이 아닌 경우에는 클래스는 따로 만드는 게 좋다
// DTO 로 조회하는 경우(화면에 맞춘 경우)등에 사용
// 수정의 라이프 사이클이 다르기 때문에 분리

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
	
	private final EntityManager em;
	
	List<Member> findAllMember() {
		return em.createNamedQuery("select m from Member m", Member.class)
				.getResultList();
	}
	
}
