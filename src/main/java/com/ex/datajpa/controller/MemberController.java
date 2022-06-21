package com.ex.datajpa.controller;

import javax.annotation.PostConstruct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ex.datajpa.dto.MemberDto;
import com.ex.datajpa.entity.Member;
import com.ex.datajpa.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

// 웹 확장 기능

@RestController
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberRepository memberRepository;
	
	@GetMapping("/members/{id}")
	public String findMember(@PathVariable("id") Long id) {
		Member member = memberRepository.findById(id).get();
		return member.getUsername();
	}
	
	@GetMapping("/members2/{id}")
	public String findMember2(@PathVariable("id") Member member) {
		return member.getUsername();
	}
	// 스프링이 자동으로 member를 찾아옴; 도메인 클래스 컨버터 기능
	// but 권장하는 기능이 아님
	// 트랜잭션 범위 내에서 조회하는 것이 아니라서 DB를 생각하지 않고 조회하는 단순 조회용으로 사용 가능
	
	@GetMapping("/members")
	public Page<MemberDto> list(@PageableDefault(size=5, sort="username") Pageable pageable){
		Page<Member> page = memberRepository.findAll(pageable);
		return page.map(MemberDto::new); // Page<Member> 객체를 생성자를 이용해서 MemberDto로 변환
	}
	// page, size, sort 쿼리스트링 제공
	// sort 예시 : id,desc  / username,desc
	// sort는 여러개 넣을 수 있음
	// .yml 에서 default 값 설정할 수 있음 or @PageableDefault 로 설정
	
//	@PostConstruct
	public void init() {
		for(int i = 0; i < 100; i++) {
			memberRepository.save(new Member("user" + i, i, null));
		}
	}
}
