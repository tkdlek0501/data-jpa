package com.ex.datajpa.repository;

// projections

public class UsernameOnlyDto {
	
	private final String username;
	
	private UsernameOnlyDto(String username) { // 파라미터 명을 분석
		this.username = username;;
	}
	
	public String getUsername() {
		return username;
	}
}
