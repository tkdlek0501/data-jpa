package com.ex.datajpa.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

// TODO: BaseEntity 작성법 (auditing)

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

	@CreatedDate
	@Column(updatable = false) // 컬럼을 수정한 이후 update 시점에 막는 기능
	private LocalDateTime createDate;
	
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	@CreatedBy
	@Column(updatable = false)
	private String createdBy;
	
	@LastModifiedBy
	private String lastModifiedBy;
}
