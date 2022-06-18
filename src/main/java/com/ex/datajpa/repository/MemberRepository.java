package com.ex.datajpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.ex.datajpa.dto.MemberDto;
import com.ex.datajpa.entity.Member;

// spring data jpa 인터페이스가 기본적인 CRUD는 제공(구현체까지)
public interface MemberRepository extends JpaRepository<Member,Long>{
	
	List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
	
	List<Member> findTop3By();
	
	// TODO: *@Query 이용; 쿼리를 직접 정의하는 기능 @Param 으로 파라미터 넘길 수 있음 
	// 비교적 간단하면(파라미터 2개 이하) 메소드 쿼리를 쓰고 길어지면 jpql을 쓰자
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
	
	// TODO: pageable 로 페이징 
	// @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m) from Member m")
	// count 쿼리를 분리하는 것도 가능 (count쿼리 성능에 이슈가 있다면 or sort 조건이 까다롭다면 이 방법으로 해결)
	Page<Member> findByAge(int age, Pageable pageable);	
	// count 쿼리까지 날아감
	// Page 가 아니라 List 로 받아서 단순히 잘라서 가져오는 것도 가능
	
	//Slice<Member> findByAge(int age, Pageable pagealbe);
	// count 쿼리는 안 날라감, 더보기 형태의 페이징
	
	// TODO: bulk 연산 쓰는법
	@Modifying(clearAutomatically = true) // update를 실행 + em.flush(), em.clear() 실행 설정
	@Query("update Member m set m.age = m.age + 1 where m.age >= :age")
	int bulkAgePlus(@Param("age") int age);
	
	@Query("select m from Member m left join fetch m.team")
	List<Member> findMemberFetchJoin();
	
	// TODO: @EntityGraph 사용; fetch join을 적용 
	// 기본 메소드 쿼리를 override해서  적용할 수 있다
	@Override
	@EntityGraph(attributePaths = {"team"})
	List<Member> findAll();
	// @Query를 사용하면서도 적용할 수 있다
	@Query("select m from Member m")
	@EntityGraph(attributePaths = {"team"})
	List<Member> findMemberEntityGraph();
	// 직접 만든 메소드 쿼리에 적용도 가능하다 ('EntityGraph' 부분 다른 이름으로 만들어도 무관하다, 'JoinTeam' 이런식으로?)
	@EntityGraph(attributePaths = {"team"})
	List<Member> findEntityGraphByUsername(@Param("username") String username);
	
	// Entity에서 클래스 레벨에 @NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team")) 으로도 가능
	// repository 에 @EntityGraph("Member.all") 추가하면 적용됨 ; 여기서도 물론 name 은 임의로 설정 가능 
	
	// 간단하게 join 하고 싶다면 EntityGraph를 사용하면 되지만 fetch join이 많이 필요하다하면 jpql로 쓰자
	
	// + 결국은 N+1을 해결하기 위한 방법으로 한번에 join하기 위한 방법들인데,
	// 1:N 컬렉션 조회의 경우에는 fetch join이 아닌 Lazy + fetch size 설정으로 해결해야 한다는 것을 주의하자
	
	// 하이버네이트를 이용해서 성능 최적화 (스냅샷을 만들지 않아 변경감지를 막음, readonly 하는 것)
	// 실제 최적화에 쓰려면 테스트를 해봐야 한다
	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
	Member findReadOnlyByUsername(String username);
	
	// db의 select for update 기능 
	// 데이터 수정하려고 SELECT 하는 중이라는 것을 알려 다른 곳에서 select 하는 것을 방지( 동시성 제어)
	//  특정 데이터(ROW)에 대해 베타적 LOCK을 거는 기능
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Member> findLockByUsername(String username);
	
	
	
}
