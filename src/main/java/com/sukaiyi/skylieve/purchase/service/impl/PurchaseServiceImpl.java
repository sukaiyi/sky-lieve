package com.sukaiyi.skylieve.purchase.service.impl;

import com.sukaiyi.skylieve.purchase.entity.PurchaseEntity;
import com.sukaiyi.skylieve.purchase.repository.PurchaseRepository;
import com.sukaiyi.skylieve.purchase.service.PurchaseService;
import com.sukaiyi.skylieve.purchase.vo.PurchaseVO;
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
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseRepository repository;

	@Autowired
	public PurchaseServiceImpl(PurchaseRepository repository) {
		this.repository = repository;
	}

	@Override
	public void clear() {
		repository.deleteAll();
	}

	@Override
	public Page<PurchaseVO> findAll(Pageable pageable) {
		Page<PurchaseEntity> page = repository.findAll(pageable);
		List<PurchaseEntity> entities = page.getContent();
		List<PurchaseVO> vos = new LinkedList<>();
		entities.forEach(e -> {
			PurchaseVO vo = new PurchaseVO();
			BeanUtils.copyProperties(e, vo);
			vos.add(vo);
		});
		Page<PurchaseVO> result = new PageImpl<>(vos, pageable, page.getTotalElements());
		return result;
	}

	@Override
	public PurchaseVO insert(PurchaseVO vo) {
		PurchaseEntity entity = new PurchaseEntity();
		BeanUtils.copyProperties(vo, entity);

		PurchaseEntity savedEntity = repository.save(entity);

		PurchaseVO savedVO = new PurchaseVO();
		BeanUtils.copyProperties(savedEntity, savedVO);
		return savedVO;
	}

	@Override
	public List<PurchaseVO> insert(List<PurchaseVO> vos) {
		List<PurchaseEntity> entities = new LinkedList<>();
		vos.forEach(v -> {
			PurchaseEntity e = new PurchaseEntity();
			BeanUtils.copyProperties(v, e);
			entities.add(e);
		});
		List<PurchaseEntity> savedEntities = repository.save(entities);
		List<PurchaseVO> savedVOs = new LinkedList<>();
		savedEntities.forEach(e -> {
			PurchaseVO v = new PurchaseVO();
			BeanUtils.copyProperties(e, v);
			savedVOs.add(v);
		});
		return savedVOs;
	}

	@Override
	public List<PurchaseEntity> findAllEntities() {
		return repository.findAll();
	}

}
