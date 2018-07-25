package com.sukaiyi.skylieve.purchase.service;

import com.sukaiyi.skylieve.purchase.entity.PurchaseEntity;
import com.sukaiyi.skylieve.purchase.vo.PurchaseVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author sukaiyi
 */
public interface PurchaseService {

	void clear();

	Page<PurchaseVO> findAll(Pageable pageable);

	PurchaseVO insert(PurchaseVO vo);

	List<PurchaseVO> insert(List<PurchaseVO> vos);

	List<PurchaseEntity> findAllEntities();
}
