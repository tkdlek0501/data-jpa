package com.ex.datajpa.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.ex.datajpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
	
	@Autowired MemberRepository memberRepository;
	
	@Test
	public void testMember() {
		Member member = new Member("memberA", 0, null);
		Member savedMember = memberRepository.save(member);
		
		Member findMember = memberRepository.findById(savedMember.getId()).get();
		
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		Assertions.assertThat(findMember).isEqualTo(member);
	}
	
	@Test
	public void basicCRUD() {
		Member member1 = new Member("member1", 10, null);
		Member member2 = new Member("member2", 20, null);
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		Member findMember1 = memberRepository.findById(member1.getId()).get();
		Member findMember2 = memberRepository.findById(member2.getId()).get();
		Assertions.assertThat(findMember1).isEqualTo(member1);
		Assertions.assertThat(findMember2).isEqualTo(member2);
		
		List<Member> all = memberRepository.findAll();
		Assertions.assertThat(all.size()).isEqualTo(2);
		
		long count = memberRepository.count();
		Assertions.assertThat(count).isEqualTo(2);
		
		memberRepository.delete(member1);
		memberRepository.delete(member2);
		
		long deletedCount = memberRepository.count();
		Assertions.assertThat(deletedCount).isEqualTo(0);
	}
	
	@Test
	public void findByUsernameAndAgeGreaterThan() {
		Member m1 = new Member("aaa", 20, null);
		Member m2 = new Member("bbb", 10, null);
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("aaa", 15);
		System.out.println("result size : " + result.size());
		
		Assertions.assertThat(result.get(0).getUsername()).isEqualTo("aaa");
		Assertions.assertThat(result.get(0).getAge()).isEqualTo(20);
		Assertions.assertThat(result.size()).isEqualTo(1);
	}
	
	@Test
	public void findTop3() {
		List<Member> list = memberRepository.findTop3By(); // 단순히 findTop() 이렇게 만들면 에러난다
		System.out.println("list : " + list);
	}
}
