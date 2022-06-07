package com.ex.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.datajpa.entity.Member;

// spring data jpa �������̽��� �⺻���� CRUD�� ����(����ü����)
public interface MemberRepository extends JpaRepository<Member,Long>{

}
