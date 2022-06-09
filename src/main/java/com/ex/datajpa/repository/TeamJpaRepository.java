package com.ex.datajpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.ex.datajpa.entity.Member;
import com.ex.datajpa.entity.Team;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeamJpaRepository {
	
	private final EntityManager em;
	
	public Team save(Team team) {
		em.persist(team);
		return team;
	}
	
	public void delete(Team team) {
		em.remove(team);
	}
	
	public List<Team> findAll(){
		return em.createQuery("select t from Team t", Team.class)
				.getResultList();
	}
	
	public Optional<Team> findById(Long id){
		Team team = em.find(Team.class, id);
		return Optional.ofNullable(team); // null ����
	}
	
	public Long count() {
		return em.createQuery("select count(t) from Team t", Long.class)
				.getSingleResult();
	}
}
