package com.ex.datajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.datajpa.entity.Member;

// spring data jpa �������̽��� �⺻���� CRUD�� ����(����ü����)
public interface MemberRepository extends JpaRepository<Member,Long>{
	
	List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
	
	List<Member> findTop3By();
}
