package com.ex.datajpa.repository;

// projections

public class UsernameOnlyDto {
	
	private final String username;
	
	private UsernameOnlyDto(String username) { // �Ķ���� ���� �м�
		this.username = username;;
	}
	
	public String getUsername() {
		return username;
	}
}
