package com.sukaiyi.skylieve.items.repository;

import com.sukaiyi.skylieve.items.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author sukaiyi
 */

public interface ItemRepository extends JpaRepository<ItemEntity, String> {

	@Query("select t from ItemEntity t where t.id = ?1 and t.dr = 0")
	ItemEntity findById(String id);

	@Query("select t from ItemEntity t where t.dr = 0")
	Page<ItemEntity> findAll(Pageable pageable);

	@Query("select t from ItemEntity t where t.itemId = ?1 and t.dr = 0")
	ItemEntity findByItemId(String itemId);

	@Query("select t from ItemEntity t where t.itemId in (?1) and t.dr = 0")
	List<ItemEntity> findByItemIds(List<String> itemIds);

	@Modifying
	@Query("update ItemEntity t set t.dr = 1 where t.id in (?1)")
	int deleteByIds(List<String> ids);

	@Modifying
	@Query("update ItemEntity t set t.dr = 1 where t.itemId in (?1)")
	int deleteByItemIds(List<String> itemIds);

	@Modifying
	@Query("update ItemEntity t set t.dr = 1 where t.dr <> 0")
	void deleteAll();
}
