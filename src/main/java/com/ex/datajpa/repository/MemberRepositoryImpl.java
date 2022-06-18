package com.ex.datajpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.ex.datajpa.entity.Member;

import lombok.RequiredArgsConstructor;

// TODO: 사용자 정의 레포지토리
// 실무에서 많이 씀
// 말그대로 jpa 레포지토리에 포함 안된 쿼리들을 커스텀한 뒤 레포지토리에서 상속받아 
// 메서드쿼리 형식으로 쓸 수 있게 하는 것
// 레포지토리에서 createQuery 등을 직접 이용하지 않게 여기서 구현체를 만들어서
// 가시성 좋게 할 수 있다

// 이것을 쓰는 case : 
// jpaRepository(MemberRepository)에서 구현되지 않은 복잡한 쿼리들을 따로 만들어 사용할 때
// (즉, 인터페이스만으로는 @Query를 이용해도 부족할 때 만들면 된다)
// + 여기서 queryDsl 쿼리를 사용해서 구현

// 주의점: 이 구현체의 클래스 이름은 '상속받는 레포지토리 이름 + Impl' 로 만들어야 한다 
// 또한 custom에 아무거나 다 구현하면 안 좋고 핵심 로직만 구현하는 게 좋다. 
// 만약 핵심 로직이 아니면 MemberQueryRepository 등을 만들어 거기서 관리해야 한다

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final EntityManager em;
	
	@Override
	public List<Member> findMemberCustom() {
		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}
	
}
