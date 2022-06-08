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
}
