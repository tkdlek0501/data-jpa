package com.ex.datajpa.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberTest {
	
	@PersistenceContext
	EntityManager em;
	
	@Test
	public void testEntity() {
		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		em.persist(teamA);
		em.persist(teamB);
		
		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 20, teamA);
		Member member3 = new Member("member3", 30, teamB);
		Member member4 = new Member("member4", 40, teamB);
		
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
		
		em.flush(); // db에 강제로 insert 쿼리
		em.clear(); // 영속성 컨텍스트 캐시 clear
		
		// confirm
		List<Member> members = em.createQuery(
				"select m from Member m ", Member.class
				).getResultList();
		
		//members.stream().forEach(); // steam.forEach() 의 사용의 지양하는게 좋다; 성능상 for문이 더 빠르다
		for(Member member : members) {
			System.out.println("member = " + member);
			System.out.println("-> member.team = " + member.getTeam());
		}
	}
	
}
