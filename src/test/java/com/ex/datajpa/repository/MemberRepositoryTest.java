package com.ex.datajpa.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
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
	@PersistenceContext
	EntityManager em;
	
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
		List<Member> list = memberRepository.findTop3By(); // �ܼ��� findTop() �̷��� ����� ��������
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
		// optional ��ȯ�� null �̸� orElse() �� ó�����ָ� �ȴ�
		// optional ���� ������ ���� exception �߻�
		System.out.println("List aaa : " + aaa);
	}
	
	@Test
	public void paging(){
		//given
		memberRepository.save(new Member("member1", 10, null));
		memberRepository.save(new Member("member2", 10, null));
		memberRepository.save(new Member("member3", 10, null));
		memberRepository.save(new Member("member4", 10, null));
		memberRepository.save(new Member("member5", 10, null));
		
		int age = 10;
		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
		// ����� �̸����� desc ����
		
		//when
		Page<Member> page = memberRepository.findByAge(age, pageRequest);
		
		// Entity -> DTO for API response
		Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));
		
		//Slice<Member> slice = memberRepository.findByAge(age, pageRequest);
		
		// then
		List<Member> content = page.getContent();
		long totalElements = page.getTotalElements(); // totalCount
		
		//List<Member> content2 = slice.getContent();
		
		for (Member member : content) {
			System.out.println("member = " + member);
		}
		System.out.println("totalElement : " + totalElements);
		
		Assertions.assertThat(content.size()).isEqualTo(3); // ������ content
		Assertions.assertThat(page.getTotalElements()).isEqualTo(5); // �� ����
		Assertions.assertThat(page.getNumber()).isEqualTo(0); // ������ ��ȣ; 0 ���� ����
		Assertions.assertThat(page.getTotalPages()).isEqualTo(2); // �� ������ ����
		Assertions.assertThat(page.isFirst()).isTrue(); // ù��° ����������
		Assertions.assertThat(page.hasNext()).isTrue(); // ���� �������� �ִ���
		
//		Assertions.assertThat(content2.size()).isEqualTo(3); 
//		Assertions.assertThat(slice.getNumber()).isEqualTo(0); 
//		Assertions.assertThat(slice.isFirst()).isTrue(); 
//		Assertions.assertThat(slice.hasNext()).isTrue(); 
		
	}
	
	// bulk�� ������ ���Ӽ� ���ؽ�Ʈ���� �����ϴ� ���� �����ϰ� ������ �ٷ� ���������� ��
	@Test
	public void bulkUpdate() {
		memberRepository.save(new Member("member1", 10, null));
		memberRepository.save(new Member("member2", 19, null));
		memberRepository.save(new Member("member3", 20, null));
		memberRepository.save(new Member("member4", 21, null));
		memberRepository.save(new Member("member5", 40, null));
		
		int resultCount = memberRepository.bulkAgePlus(20);
		
//		em.flush(); // transaction�ؼ� DB�� �ݿ�
//		em.clear(); // ���Ӽ� ���ؽ�Ʈ ���� (�� ���� ���ʹ� ĳ�ø� �̿����� �ʰ� DB���� ���� ��ȸ�ǰ�)
		
		Member member5 = memberRepository.findMemberByUsername("member5");
		System.out.println("member5 : " + member5); 
		// update�� �ص� ���Ӽ� ���ؽ�Ʈ�� �ݿ��� ���� �ʾ� ���̰� 40��� �����Ǿ� ����
		// ��ũ ���� �Ŀ��� ���� ó�� ���� em.flush(), em.clear() �������
		// repository���� @Modifying(clearAutomatically = true) // true ���ָ�  ��ó�� ������
		
		Assertions.assertThat(resultCount).isEqualTo(3);
	}
	
	@Test
	public void findMemberLazy() {
		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		teamRepository.save(teamA);
		teamRepository.save(teamB);
		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 10, teamB);
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		em.flush();
		em.clear();
		
		List<Member> members = memberRepository.findAll();
		//List<Member> members = memberRepository.findMemberFetchJoin();
		
		for (Member member : members) {
			System.out.println("member = " + member.getUsername());
			System.out.println("member.teamClass = " + member.getTeam().getClass()); // �̰� ���Ͻ� ��ü�� ���ְ�
			System.out.println("member.team = " + member.getTeam().getName()); // Team�� Lazy�� ���־ �� �� ������ ������ ���Ͻ� ��ü�� �ʱ�ȭ
			// �� �κ��� N+1 ������ ����� ��
			// �ΰ������� ���� �;� �� �� �̷� ������ ���� �� ���� -> fetch join�� �Ἥ �ѹ��� join�ؼ� ������� ������ �ذ� �ؾ� ��
			// 1. findmemberFetchJoin(); @Query �� fetch join ����ؼ� �ѹ��� ������� ��
			// 2. @EntityGraph�� Ȱ��; ���� findAll() �� �������̵� �ؼ� ������
		}
	}
	
	@Test
	public void queryHint() {
		// given
		Member member1 = new Member("member1", 10, null);
		memberRepository.save(member1);
		em.flush();
		em.clear();
		
		// when
		Member findMember = memberRepository.findReadOnlyByUsername("member1");
		findMember.setUsername("member2"); // ���氨�� ����
		
		em.flush();
	}
	
	@Test
	public void lock() {
		// given
		Member member1 = new Member("member1", 10, null);
		memberRepository.save(member1);
		em.flush();
		em.clear();
		
		// when
		List<Member> findMembers = memberRepository.findLockByUsername("member1");
	}
	
	@Test
	public void callCustom() {
		List<Member> result = memberRepository.findMemberCustom();
	}
}
