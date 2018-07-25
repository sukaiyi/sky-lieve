package com.sukaiyi.skylieve.items.service.impl;

import com.sukaiyi.skylieve.items.entity.ItemEntity;
import com.sukaiyi.skylieve.items.repository.ItemRepository;
import com.sukaiyi.skylieve.items.service.ItemService;
import com.sukaiyi.skylieve.items.vo.ItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sukaiyi
 */
@Service
public class ItemServiceImpl implements ItemService {

	private final ItemRepository repository;

	@Autowired
	public ItemServiceImpl(ItemRepository repository) {
		this.repository = repository;
	}

	@Override
	public void clear() {
		repository.deleteAll();
	}

	@Override
	public Page<ItemVO> findAll(Pageable pageable) {
		Page<ItemEntity> page = repository.findAll(pageable);
		List<ItemEntity> entities = page.getContent();
		List<ItemVO> vos = new LinkedList<>();
		entities.forEach(e -> {
			ItemVO vo = new ItemVO();
			BeanUtils.copyProperties(e, vo);
			vos.add(vo);
		});
		Page<ItemVO> result = new PageImpl<>(vos, pageable, page.getTotalElements());
		return result;
	}

	@Override
	public ItemVO insert(ItemVO vo) {
		ItemEntity entity = new ItemEntity();
		BeanUtils.copyProperties(vo, entity);

		ItemEntity savedEntity = repository.save(entity);

		ItemVO savedVO = new ItemVO();
		BeanUtils.copyProperties(savedEntity, savedVO);
		return savedVO;
	}

	@Override
	public List<ItemVO> insert(List<ItemVO> vos) {
		List<ItemEntity> entities = new LinkedList<>();
		vos.forEach(v -> {
			ItemEntity e = new ItemEntity();
			BeanUtils.copyProperties(v, e);
			entities.add(e);
		});
		List<ItemEntity> savedEntities = repository.save(entities);
		List<ItemVO> savedVOs = new LinkedList<>();
		savedEntities.forEach(e -> {
			ItemVO v = new ItemVO();
			BeanUtils.copyProperties(e, v);
			savedVOs.add(v);
		});
		return savedVOs;
	}

	@Override
	public List<ItemEntity> findByItemIds(List<String> itemIds) {
		return repository.findByItemIds(itemIds);
	}

}
