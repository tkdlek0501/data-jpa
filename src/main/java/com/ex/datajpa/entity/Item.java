package com.ex.datajpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class Item {
	
	@Id @GeneratedValue
	private Long id;
	
}
