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
	
	// save ȣ��� persist or merge �ϴµ�
	// �ĺ��ڰ� �ֳ� ������ ������ ��
	// merge�� ��� select�� �ϰ� insert�� �ϱ� ������
	// update ������ ���ؼ��� save�� merge�� �����ϰ� dirty checking�� �̿��ؾ� ��
}
