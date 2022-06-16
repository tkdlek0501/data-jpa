package com.ex.datajpa.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ex.datajpa.entity.Member;

@SpringBootTest
@Transactional
public class MemberJpaRepositoryTest {
	
	@Autowired MemberJpaRepository memberJpaRepository;
	
	@Test
	public void testMember() {
		Member member = new Member("memberA", 0, null);
		Member savedMember = memberJpaRepository.save(member);
		
		Member findMember = memberJpaRepository.find(savedMember.getId());
		
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
	}
	
	@Test
	public void basicCRUD() {
		Member member1 = new Member("member1", 10, null);
		Member member2 = new Member("member2", 20, null);
		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);
		
		Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
		Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
		Assertions.assertThat(findMember1).isEqualTo(member1);
		Assertions.assertThat(findMember2).isEqualTo(member2);
		
		List<Member> all = memberJpaRepository.findAll();
		Assertions.assertThat(all.size()).isEqualTo(2);
		
		long count = memberJpaRepository.count();
		Assertions.assertThat(count).isEqualTo(2);
		
		memberJpaRepository.delete(member1);
		memberJpaRepository.delete(member2);
		
		long deletedCount = memberJpaRepository.count();
		Assertions.assertThat(deletedCount).isEqualTo(0);
	}
	
	@Test
	public void paging(){
		//given
		memberJpaRepository.save(new Member("member1", 10, null));
		memberJpaRepository.save(new Member("member2", 10, null));
		memberJpaRepository.save(new Member("member3", 10, null));
		memberJpaRepository.save(new Member("member4", 10, null));
		memberJpaRepository.save(new Member("member5", 10, null));
		
		int age = 10;
		int offset = 0;
		int limit = 3;
		
		//when
		List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
		long totalCount = memberJpaRepository.totalCount(age);
		
		// then
		// 페이지 계산 공식 적용
		// ex 마지막 페이지, 최초 페이지 등등 
		
		Assertions.assertThat(members.size()).isEqualTo(3);
		Assertions.assertThat(totalCount).isEqualTo(5);
		
	}
	
	@Test
	public void bulkUpdate() {
		memberJpaRepository.save(new Member("member1", 10, null));
		memberJpaRepository.save(new Member("member2", 19, null));
		memberJpaRepository.save(new Member("member3", 20, null));
		memberJpaRepository.save(new Member("member4", 21, null));
		memberJpaRepository.save(new Member("member5", 40, null));
		
		int resultCount = memberJpaRepository.bulkAgePlus(20);
		
		Assertions.assertThat(resultCount).isEqualTo(3);
	}
	
}
