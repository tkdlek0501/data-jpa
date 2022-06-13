package com.ex.datajpa.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.ex.datajpa.dto.MemberDto;
import com.ex.datajpa.entity.Member;
import com.ex.datajpa.entity.Team;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
	
	@Autowired MemberRepository memberRepository;
	@Autowired TeamRepository teamRepository;
	
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
	
	@Test
	public void testQuery() {
		Member m1 = new Member("AAA", 10, null);
		Member m2 = new Member("BBB", 20, null);
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		List<Member> result = memberRepository.findUser("AAA", 10);
		Assertions.assertThat(result.get(0)).isEqualTo(m1);
	}
	
	@Test
	public void findusernameList() {
		Member m1 = new Member("AAA", 10, null);
		Member m2 = new Member("BBB", 20, null);
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		List<String> result = memberRepository.findUsernameList();
		for (String s : result) {
			System.out.println("s = " + s);
		}
	}
	
	@Test
	public void findMemberDto() {
		Team team = new Team("teamA");
		teamRepository.save(team);
		
		Member m1 = new Member("AAA", 10, null);
		m1.setTeam(team);
		memberRepository.save(m1);
		
		List<MemberDto> result = memberRepository.findMemberDto();
		for (MemberDto s : result) {
			System.out.println("s = " + s);
		}
	}
	
	@Test
	public void findByNames() {
		Member m1 = new Member("AAA", 10, null);
		Member m2 = new Member("BBB", 20, null);
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		ArrayList<String> datas = new ArrayList<>();
		datas.add("AAA");
		datas.add("BBB");
		
		List<Member> result = memberRepository.findByNames(datas);
		for (Member member : result) {
			System.out.println("s = " + member);
		}
	}
	
	@Test
	public void returnType() {
		Member m1 = new Member("AAA", 10, null);
		Member m2 = new Member("BBB", 20, null);
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		List<Member> aaa = memberRepository.findListByUsername("AAA");
		//Member bbb = memberRepository.findMemberByUsername("AAA");
		//Optional<Member> ccc = memberRepository.findOptionalByUsername("AAA");
		// optional 반환시 null 이면 orElse() 로 처리해주면 된다
		// optional 에도 여러개 담기면 exception 발생
		System.out.println("List aaa : " + aaa);
	}
}
