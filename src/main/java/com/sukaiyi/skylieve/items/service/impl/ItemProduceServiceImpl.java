package com.sukaiyi.skylieve.items.service.impl;

import com.sukaiyi.skylieve.items.entity.ItemProduceEntity;
import com.sukaiyi.skylieve.items.repository.ItemProduceRepository;
import com.sukaiyi.skylieve.items.repository.ItemRepository;
import com.sukaiyi.skylieve.items.service.ItemProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sukaiyi
 */
@Service
public class ItemProduceServiceImpl implements ItemProduceService {

	@Autowired
	ItemProduceRepository repository;

	@Override
	public ItemProduceEntity findById(String id) {
		return repository.findById(id);
	}

	@Override
	public List<ItemProduceEntity> findByProduceItemIds(List<String> itemIds) {
		return repository.findByProduceItemIds(itemIds);
	}

	@Override
	public ItemProduceEntity findByProduceItemId(String itemId) {
		return repository.findByProduceItemId(itemId);
	}

	@Override
	public ItemProduceEntity save(ItemProduceEntity entity) {
		return repository.save(entity);
	}
}
