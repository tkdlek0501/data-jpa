package com.ex.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.datajpa.entity.Member;

// spring data jpa 인터페이스가 기본적인 CRUD는 제공(구현체까지)
public interface MemberRepository extends JpaRepository<Member,Long>{

}
