package com.ex.datajpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //auddit 기능 사용 
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}
	
	// TODO: @CreatedBy, @LastModifiedBy 에 들어가는 값 설정
	@Bean
	public AuditorAware<String> auditorProvider(){
		// 실제로는 session 정보를 꺼내서 userId 를 가져와야 한다
		return () -> Optional.of(UUID.randomUUID().toString());
	}
}
