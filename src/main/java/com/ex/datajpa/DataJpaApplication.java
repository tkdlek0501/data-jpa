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
@EnableJpaAuditing //auddit ��� ��� 
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}
	
	// TODO: @CreatedBy, @LastModifiedBy �� ���� �� ����
	@Bean
	public AuditorAware<String> auditorProvider(){
		// �����δ� session ������ ������ userId �� �����;� �Ѵ�
		return () -> Optional.of(UUID.randomUUID().toString());
	}
}
