package com.sukaiyi.skylieve.items.service;

import com.sukaiyi.skylieve.items.entity.ItemEntity;
import com.sukaiyi.skylieve.items.entity.ItemProduceEntity;
import com.sukaiyi.skylieve.items.vo.ItemVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author sukaiyi
 */
public interface ItemProduceService {
	ItemProduceEntity findById(String id);

	List<ItemProduceEntity> findByProduceItemIds(List<String> itemIds);

	ItemProduceEntity findByProduceItemId(String itemId);

	ItemProduceEntity save(ItemProduceEntity entity);
}
