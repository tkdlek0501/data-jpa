package com.ex.datajpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
@Getter @Setter
public class Member {
	
	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String username;
	private int age;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;
	
	// jpa는 (@Entity) 프록시 객체 만들어내야 하기 때문에 private으로 막아버리면 안되고
	// 기본 생성자 필요 -> @NoArgsConstructor 로 대체
//	protected Member() {
//	}
	
	public Member(String username, int age, Team team) {
		this.username = username;
		this.age = age;
		if(team != null) { // 원래는 team이 없으면 생성되지 않는 방향으로 작업해야 한다.; 예외를 터뜨려야 함 
			changeTeam(team);
		}
	}
	
	
// === 연관관계 편의 메서드	
	public void changeTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}
	
}
