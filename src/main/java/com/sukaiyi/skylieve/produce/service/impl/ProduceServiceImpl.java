package com.sukaiyi.skylieve.produce.service.impl;

import com.sukaiyi.skylieve.produce.entity.ProduceEntity;
import com.sukaiyi.skylieve.produce.repository.ProduceRepository;
import com.sukaiyi.skylieve.produce.service.ProduceService;
import com.sukaiyi.skylieve.produce.vo.ProduceVO;
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
public class ProduceServiceImpl implements ProduceService {

	private final ProduceRepository repository;

	@Autowired
	public ProduceServiceImpl(ProduceRepository repository) {
		this.repository = repository;
	}

	@Override
	public void clear() {
		repository.deleteAll();
	}

	@Override
	public Page<ProduceVO> findAll(Pageable pageable) {
		Page<ProduceEntity> page = repository.findAll(pageable);
		List<ProduceEntity> entities = page.getContent();
		List<ProduceVO> vos = new LinkedList<>();
		entities.forEach(e -> {
			ProduceVO vo = new ProduceVO();
			BeanUtils.copyProperties(e, vo);
			vos.add(vo);
		});
		Page<ProduceVO> result = new PageImpl<>(vos, pageable, page.getTotalElements());
		return result;
	}

	@Override
	public ProduceVO insert(ProduceVO vo) {
		ProduceEntity entity = new ProduceEntity();
		BeanUtils.copyProperties(vo, entity);

		ProduceEntity savedEntity = repository.save(entity);

		ProduceVO savedVO = new ProduceVO();
		BeanUtils.copyProperties(savedEntity, savedVO);
		return savedVO;
	}

	@Override
	public List<ProduceVO> insert(List<ProduceVO> vos) {
		List<ProduceEntity> entities = new LinkedList<>();
		vos.forEach(v -> {
			ProduceEntity e = new ProduceEntity();
			BeanUtils.copyProperties(v, e);
			entities.add(e);
		});
		List<ProduceEntity> savedEntities = repository.save(entities);
		List<ProduceVO> savedVOs = new LinkedList<>();
		savedEntities.forEach(e -> {
			ProduceVO v = new ProduceVO();
			BeanUtils.copyProperties(e, v);
			savedVOs.add(v);
		});
		return savedVOs;
	}

	@Override
	public List<ProduceEntity> findAllEntities() {
		return repository.findAll();
	}

}
