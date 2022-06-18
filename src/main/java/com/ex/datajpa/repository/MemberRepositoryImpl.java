package com.ex.datajpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.ex.datajpa.entity.Member;

import lombok.RequiredArgsConstructor;

// TODO: ����� ���� �������丮
// �ǹ����� ���� ��
// ���״�� jpa �������丮�� ���� �ȵ� �������� Ŀ������ �� �������丮���� ��ӹ޾� 
// �޼������� �������� �� �� �ְ� �ϴ� ��
// �������丮���� createQuery ���� ���� �̿����� �ʰ� ���⼭ ����ü�� ����
// ���ü� ���� �� �� �ִ�

// �̰��� ���� case : 
// jpaRepository(MemberRepository)���� �������� ���� ������ �������� ���� ����� ����� ��
// (��, �������̽������δ� @Query�� �̿��ص� ������ �� ����� �ȴ�)
// + ���⼭ queryDsl ������ ����ؼ� ����

// ������: �� ����ü�� Ŭ���� �̸��� '��ӹ޴� �������丮 �̸� + Impl' �� ������ �Ѵ� 
// ���� custom�� �ƹ��ų� �� �����ϸ� �� ���� �ٽ� ������ �����ϴ� �� ����. 
// ���� �ٽ� ������ �ƴϸ� MemberQueryRepository ���� ����� �ű⼭ �����ؾ� �Ѵ�

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final EntityManager em;
	
	@Override
	public List<Member> findMemberCustom() {
		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}
	
}
