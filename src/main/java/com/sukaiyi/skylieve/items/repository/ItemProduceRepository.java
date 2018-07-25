package com.sukaiyi.skylieve.items.repository;

import com.sukaiyi.skylieve.items.entity.ItemProduceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author sukaiyi
 */

public interface ItemProduceRepository extends JpaRepository<ItemProduceEntity, String> {

	@Query("select t from ItemProduceEntity t where t.id = ?1 and t.dr = 0")
	ItemProduceEntity findById(String id);

	@Query("select t from ItemProduceEntity t where t.dr = 0")
	Page<ItemProduceEntity> findAll(Pageable pageable);

	@Query("select t from ItemProduceEntity t where t.produceItemId = ?1 and t.dr = 0")
	ItemProduceEntity findByProduceItemId(String itemId);

	@Query("select t from ItemProduceEntity t where t.produceItemId in (?1) and t.dr = 0")
	List<ItemProduceEntity> findByProduceItemIds(List<String> itemIds);

	@Modifying
	@Query("update ItemProduceEntity t set t.dr = 1 where t.id in (?1)")
	int deleteByIds(List<String> ids);

	@Modifying
	@Query("update ItemProduceEntity t set t.dr = 1 where t.produceItemId in (?1)")
	int deleteProduceByItemIds(List<String> itemIds);

	@Modifying
	@Query("update ItemProduceEntity t set t.dr = 1 where t.dr <> 0")
	void deleteAll();
}
