package com.sukaiyi.skylieve.factory.service.impl;

import com.sukaiyi.skylieve.factory.entity.FactoryEntity;
import com.sukaiyi.skylieve.factory.repository.FactoryRepository;
import com.sukaiyi.skylieve.factory.service.FactoryService;
import com.sukaiyi.skylieve.factory.vo.FactoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sukaiyi
 */
@Service
public class FactoryServiceImpl implements FactoryService {

	private final FactoryRepository repository;

	@Autowired
	public FactoryServiceImpl(FactoryRepository repository) {
		this.repository = repository;
	}

	@Override
	public void clear() {
		repository.deleteAll();
	}

	@Override
	public Page<FactoryVO> findAll(Pageable pageable) {
		Page<FactoryEntity> page = repository.findAll(pageable);
		List<FactoryEntity> entities = page.getContent();
		List<FactoryVO> vos = new LinkedList<>();
		entities.forEach(e -> {
			FactoryVO vo = new FactoryVO();
			BeanUtils.copyProperties(e, vo);
			vos.add(vo);
		});
		Page<FactoryVO> result = new PageImpl<>(vos, pageable, page.getTotalElements());
		return result;
	}

	@Override
	public FactoryVO insert(FactoryVO vo) {
		FactoryEntity entity = new FactoryEntity();
		BeanUtils.copyProperties(vo, entity);

		FactoryEntity savedEntity = repository.save(entity);

		FactoryVO savedVO = new FactoryVO();
		BeanUtils.copyProperties(savedEntity, savedVO);
		return savedVO;
	}

	@Override
	public List<FactoryVO> insert(List<FactoryVO> vos) {
		List<FactoryEntity> entities = new LinkedList<>();
		vos.forEach(v -> {
			FactoryEntity e = new FactoryEntity();
			BeanUtils.copyProperties(v, e);
			entities.add(e);
		});
		List<FactoryEntity> savedEntities = repository.save(entities);
		List<FactoryVO> savedVOs = new LinkedList<>();
		savedEntities.forEach(e -> {
			FactoryVO v = new FactoryVO();
			BeanUtils.copyProperties(e, v);
			savedVOs.add(v);
		});
		return savedVOs;
	}

	@Override
	public List<FactoryEntity> findByItemIds(List<String> itemIds) {
		return repository.findByItemIds(itemIds);
	}

}
