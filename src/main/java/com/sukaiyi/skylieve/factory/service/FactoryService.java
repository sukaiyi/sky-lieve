package com.sukaiyi.skylieve.factory.service;

import com.sukaiyi.skylieve.factory.entity.FactoryEntity;
import com.sukaiyi.skylieve.factory.vo.FactoryVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author sukaiyi
 */
public interface FactoryService {

	void clear();

	Page<FactoryVO> findAll(Pageable pageable);

	FactoryVO insert(FactoryVO entity);

	List<FactoryVO> insert(List<FactoryVO> vos);

	List<FactoryEntity> findByItemIds(List<String> itemIds);
}
