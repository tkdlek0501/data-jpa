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

// spring data jpa �������̽��� �⺻���� CRUD�� ����(����ü����)
public interface MemberRepository extends JpaRepository<Member,Long>{
	
	List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
	
	List<Member> findTop3By();
	
	// TODO: *@Query �̿�; ������ ���� �����ϴ� ��� @Param ���� �Ķ���� �ѱ� �� ���� 
	// ���� �����ϸ�(�Ķ���� 2�� ����) �޼ҵ� ������ ���� ������� jpql�� ����
	@Query("select m from Member m where m.username = :username and m.age = :age")
	List<Member> findUser(@Param("username") String username, @Param("age") int age);

	@Query("select m.username from Member m")
	List<String> findUsernameList();
	
	// dto �� ��ȯ�ϱ� ���ؼ���  new operation ����Ѵ� -> queryDsl �� �� ���ϰ� �ۼ� ����
	@Query("select new com.ex.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
	List<MemberDto> findMemberDto();
	
	// *�÷��� ���ε� : in���� �ٷ� �Ķ���� ���� �� ����
	@Query("select m from Member m where m.username in :names")
	List<Member> findByNames(@Param("names") List<String> names);
	
	// ��ȯŸ�� �����ϰ� ����
	List<Member> findListByUsername(String username); // �÷���
	Member findMemberByUsername(String username); // �ܰ�
	Optional<Member> findOptionalByUsername(String username); // Optional �� ���� �ܰ�
	
	// TODO: pageable �� ����¡ 
	// @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m) from Member m")
	// count ������ �и��ϴ� �͵� ���� (count���� ���ɿ� �̽��� �ִٸ� or sort ������ ��ٷӴٸ� �� ������� �ذ�)
	Page<Member> findByAge(int age, Pageable pageable);	
	// count �������� ���ư�
	// Page �� �ƴ϶� List �� �޾Ƽ� �ܼ��� �߶� �������� �͵� ����
	
	//Slice<Member> findByAge(int age, Pageable pagealbe);
	// count ������ �� ����, ������ ������ ����¡
	
	// TODO: bulk ���� ���¹�
	@Modifying(clearAutomatically = true) // update�� ���� + em.flush(), em.clear() ���� ����
	@Query("update Member m set m.age = m.age + 1 where m.age >= :age")
	int bulkAgePlus(@Param("age") int age);
	
	@Query("select m from Member m left join fetch m.team")
	List<Member> findMemberFetchJoin();
	
	// TODO: @EntityGraph ���; fetch join�� ���� 
	// �⺻ �޼ҵ� ������ override�ؼ�  ������ �� �ִ�
	@Override
	@EntityGraph(attributePaths = {"team"})
	List<Member> findAll();
	// @Query�� ����ϸ鼭�� ������ �� �ִ�
	@Query("select m from Member m")
	@EntityGraph(attributePaths = {"team"})
	List<Member> findMemberEntityGraph();
	// ���� ���� �޼ҵ� ������ ���뵵 �����ϴ� ('EntityGraph' �κ� �ٸ� �̸����� ���� �����ϴ�, 'JoinTeam' �̷�������?)
	@EntityGraph(attributePaths = {"team"})
	List<Member> findEntityGraphByUsername(@Param("username") String username);
	
	// Entity���� Ŭ���� ������ @NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team")) ���ε� ����
	// repository �� @EntityGraph("Member.all") �߰��ϸ� ����� ; ���⼭�� ���� name �� ���Ƿ� ���� ���� 
	
	// �����ϰ� join �ϰ� �ʹٸ� EntityGraph�� ����ϸ� ������ fetch join�� ���� �ʿ��ϴ��ϸ� jpql�� ����
	
	// + �ᱹ�� N+1�� �ذ��ϱ� ���� ������� �ѹ��� join�ϱ� ���� ������ε�,
	// 1:N �÷��� ��ȸ�� ��쿡�� fetch join�� �ƴ� Lazy + fetch size �������� �ذ��ؾ� �Ѵٴ� ���� ��������
	
	// ���̹�����Ʈ�� �̿��ؼ� ���� ����ȭ (�������� ������ �ʾ� ���氨���� ����, readonly �ϴ� ��)
	// ���� ����ȭ�� ������ �׽�Ʈ�� �غ��� �Ѵ�
	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
	Member findReadOnlyByUsername(String username);
	
	// db�� select for update ��� 
	// ������ �����Ϸ��� SELECT �ϴ� ���̶�� ���� �˷� �ٸ� ������ select �ϴ� ���� ����( ���ü� ����)
	//  Ư�� ������(ROW)�� ���� ��Ÿ�� LOCK�� �Ŵ� ���
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Member> findLockByUsername(String username);
	
	
	
}
