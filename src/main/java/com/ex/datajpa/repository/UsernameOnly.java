package com.ex.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

// projections

public interface UsernameOnly {
	
	@Value("#{target.username + ' ' + target.age}") // open projections; Entity�� �� �����ͼ� ó�� 
	String getUsername(); // username�� �������� ����
}
