package com.ex.datajpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.ex.datajpa.entity.Member;

import lombok.RequiredArgsConstructor;

// TODO: ���ٽ� ���� �������丮
// �ٽ� ����Ͻ� ������ �ƴ� ��쿡�� Ŭ������ ���� ����� �� ����
// DTO �� ��ȸ�ϴ� ���(ȭ�鿡 ���� ���)� ���
// ������ ������ ����Ŭ�� �ٸ��� ������ �и�

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
	
	private final EntityManager em;
	
	List<Member> findAllMember() {
		return em.createNamedQuery("select m from Member m", Member.class)
				.getResultList();
	}
	
}
