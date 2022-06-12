package com.ex.datajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ex.datajpa.dto.MemberDto;
import com.ex.datajpa.entity.Member;

// spring data jpa �������̽��� �⺻���� CRUD�� ����(����ü����)
public interface MemberRepository extends JpaRepository<Member,Long>{
	
	List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
	
	List<Member> findTop3By();
	
	// TODO: *@Query �̿�; ������ ���� �����ϴ� ��� @Param ���� �Ķ���� �ѱ� �� ����
	@Query("select m from Member m where m.username = :username and m.age = :age")
	List<Member> findUser(@Param("username") String username, @Param("age") int age);

	@Query("select m.username from Member m")
	List<String> findUsernameList();
	
	// dto �� ��ȯ�ϱ� ���ؼ���  new operation ����Ѵ� -> queryDsl �� �� ���ϰ� �ۼ� ����
	@Query("select new com.ex.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
	List<MemberDto> findMemberDto();
}
