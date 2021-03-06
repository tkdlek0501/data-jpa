package com.ex.datajpa.repository;

import java.util.List;
import java.util.Optional;

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
	
	public void delete(Member member) {
		em.remove(member);
	}
	
	public List<Member> findAll(){
		return em.createQuery(
				"select m from Member m", Member.class
				).getResultList();
	}
	
	public Optional<Member> findById(Long id){
		Member member = em.find(Member.class, id);
		return Optional.ofNullable(member); // null 가능
	}
	
	public long count() {
		return em.createQuery(
				"select count(m) from Member m", Long.class
				).getSingleResult();
	}
	
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
	
	// 순수 JPA 페이징 / 정렬
	public List<Member> findByPage(int age, int offset, int limit){
		return em.createQuery("select m from Member m where m.age = :age order by m.username desc", Member.class)
		.setParameter("age", age)
		.setFirstResult(offset)
		.setMaxResults(limit)
		.getResultList();
	}
	public long totalCount(int age) {
		return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
				.setParameter("age", age)
				.getSingleResult();
	}
	
	public int bulkAgePlus(int age) {
		int resultCount = em.createQuery("update Member m set m.age = m.age + 1 where m.age >= :age")
		.setParameter("age", age)
		.executeUpdate(); // bulk update 
		
		return resultCount;
	}
}
