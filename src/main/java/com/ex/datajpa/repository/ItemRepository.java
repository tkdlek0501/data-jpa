package com.ex.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.datajpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
