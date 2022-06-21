package com.ex.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ex.datajpa.entity.Item;

@SpringBootTest
public class ItemRepositoryTest {
	
	@Autowired ItemRepository itemRepository;
	
	@Test
	public void save() {
		Item item = new Item();
		itemRepository.save(item);
	}
	
	// save 호출시 persist or merge 하는데
	// 식별자가 있나 없나로 구분을 함
	// merge의 경우 select를 하고 insert를 하기 때문에
	// update 쿼리를 위해서는 save의 merge를 지양하고 dirty checking을 이용해야 함
}
