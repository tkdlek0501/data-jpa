package com.ex.datajpa.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.ex.datajpa.entity.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {
	
	private final EntityManager em;
	
	public Member save(Member member) {
		em.persist(member);
		return member;
	}
	
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
}
