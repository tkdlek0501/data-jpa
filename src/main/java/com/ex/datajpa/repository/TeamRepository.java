package com.ex.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.datajpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
