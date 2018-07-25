package com.sukaiyi.skylieve.items.service;

import com.sukaiyi.skylieve.items.entity.ItemEntity;
import com.sukaiyi.skylieve.items.vo.ItemVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author sukaiyi
 */
public interface ItemService {

	void clear();

	Page<ItemVO> findAll(Pageable pageable);

	ItemVO insert(ItemVO entity);

	List<ItemVO> insert(List<ItemVO> vos);

	List<ItemEntity> findByItemIds(List<String> itemIds);
}
