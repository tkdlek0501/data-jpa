package com.ex.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

// projections

public interface UsernameOnly {
	
	@Value("#{target.username + ' ' + target.age}") // open projections; Entity를 다 가져와서 처리 
	String getUsername(); // username만 가져오기 위해
}
