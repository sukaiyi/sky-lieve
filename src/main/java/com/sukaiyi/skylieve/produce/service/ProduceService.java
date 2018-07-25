package com.sukaiyi.skylieve.produce.service;

import com.sukaiyi.skylieve.produce.entity.ProduceEntity;
import com.sukaiyi.skylieve.produce.vo.ProduceVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author sukaiyi
 */
public interface ProduceService {

	void clear();

	Page<ProduceVO> findAll(Pageable pageable);

	ProduceVO insert(ProduceVO vo);

	List<ProduceVO> insert(List<ProduceVO> vos);

	List<ProduceEntity> findAllEntities();
}
