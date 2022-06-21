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

// �� Ȯ�� ���

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
	// �������� �ڵ����� member�� ã�ƿ�; ������ Ŭ���� ������ ���
	// but �����ϴ� ����� �ƴ�
	// Ʈ����� ���� ������ ��ȸ�ϴ� ���� �ƴ϶� DB�� �������� �ʰ� ��ȸ�ϴ� �ܼ� ��ȸ������ ��� ����
	
	@GetMapping("/members")
	public Page<MemberDto> list(@PageableDefault(size=5, sort="username") Pageable pageable){
		Page<Member> page = memberRepository.findAll(pageable);
		return page.map(MemberDto::new); // Page<Member> ��ü�� �����ڸ� �̿��ؼ� MemberDto�� ��ȯ
	}
	// page, size, sort ������Ʈ�� ����
	// sort ���� : id,desc  / username,desc
	// sort�� ������ ���� �� ����
	// .yml ���� default �� ������ �� ���� or @PageableDefault �� ����
	
//	@PostConstruct
	public void init() {
		for(int i = 0; i < 100; i++) {
			memberRepository.save(new Member("user" + i, i, null));
		}
	}
}
