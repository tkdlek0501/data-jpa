package com.ex.datajpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {
	
	@Id @GeneratedValue
	private Long id;
	
	private String username;
	
	// jpa는 (@Entity) 프록시 객체 만들어내야 하기 때문에 private으로 막아버리면 안되고
	// 기본 생성자 필요
	protected Member() {
	}
	
	public Member(String username) {
		this.username = username;
	}
	
}
