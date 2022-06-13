package com.ex.datajpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ex.datajpa.dto.MemberDto;
import com.ex.datajpa.entity.Member;

// spring data jpa 인터페이스가 기본적인 CRUD는 제공(구현체까지)
public interface MemberRepository extends JpaRepository<Member,Long>{
	
	List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
	
	List<Member> findTop3By();
	
	// TODO: *@Query 이용; 쿼리를 직접 정의하는 기능 @Param 으로 파라미터 넘길 수 있음
	@Query("select m from Member m where m.username = :username and m.age = :age")
	List<Member> findUser(@Param("username") String username, @Param("age") int age);

	@Query("select m.username from Member m")
	List<String> findUsernameList();
	
	// dto 로 반환하기 위해서는  new operation 써야한다 -> queryDsl 로 더 편하게 작성 가능
	@Query("select new com.ex.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
	List<MemberDto> findMemberDto();
	
	// *컬렉션 바인딩 : in절에 바로 파라미터 넣을 수 있음
	@Query("select m from Member m where m.username in :names")
	List<Member> findByNames(@Param("names") List<String> names);
	
	// 반환타입 유연하게 가능
	List<Member> findListByUsername(String username); // 컬렉션
	Member findMemberByUsername(String username); // 단건
	Optional<Member> findOptionalByUsername(String username); // Optional 로 감싼 단건

	
}
