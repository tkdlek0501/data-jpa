package com.ex.datajpa.repository;

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
		Member member = new Member("memberA");
		Member savedMember = memberJpaRepository.save(member);
		
		Member findMember = memberJpaRepository.find(savedMember.getId());
		
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
	}
	
}
