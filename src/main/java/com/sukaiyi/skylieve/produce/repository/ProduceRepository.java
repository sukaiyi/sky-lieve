package com.sukaiyi.skylieve.produce.repository;

import com.sukaiyi.skylieve.produce.entity.ProduceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author sukaiyi
 */

public interface ProduceRepository extends JpaRepository<ProduceEntity, String> {

	@Query("select t from ProduceEntity t where t.id = ?1 and t.dr = 0")
	ProduceEntity findById(String id);

	@Query("select t from ProduceEntity t where t.dr = 0")
	Page<ProduceEntity> findAll(Pageable pageable);

	@Query("select t from ProduceEntity t where t.dr = 0")
	List<ProduceEntity> findAll();

	@Query("select t from ProduceEntity t where t.itemId = ?1 and t.dr = 0")
	ProduceEntity findByItemId(String key);

	@Modifying
	@Query("update ProduceEntity t set t.dr = 1 where t.id in (?1)")
	int deleteByIds(List<String> ids);

	@Modifying
	@Query("update ProduceEntity t set t.dr = 1 where t.itemId in (?1)")
	int deleteByItemIds(List<String> itemIds);

	@Modifying
	@Query("update ProduceEntity t set t.dr = 1 where t.dr <> 0")
	void deleteAll();
}
