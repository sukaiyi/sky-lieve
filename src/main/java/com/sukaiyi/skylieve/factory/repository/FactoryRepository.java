package com.sukaiyi.skylieve.factory.repository;

import com.sukaiyi.skylieve.factory.entity.FactoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author sukaiyi
 */

public interface FactoryRepository extends JpaRepository<FactoryEntity, String> {

	@Query("select t from FactoryEntity t where t.id = ?1 and t.dr = 0")
	FactoryEntity findById(String id);

	@Query("select t from FactoryEntity t where t.dr = 0")
	Page<FactoryEntity> findAll(Pageable pageable);

	@Query("select t from FactoryEntity t where t.itemId = ?1 and t.dr = 0")
	List<FactoryEntity> findByItemId(String itemId);

	@Query("select t from FactoryEntity t where t.itemId in (?1) and t.dr = 0")
	List<FactoryEntity> findByItemIds(List<String> itemIds);

	@Modifying
	@Query("update FactoryEntity t set t.dr = 1 where t.id in (?1)")
	int deleteByIds(List<String> ids);

	@Modifying
	@Query("update FactoryEntity t set t.dr = 1 where t.itemId in (?1)")
	int deleteByItemIds(List<String> itemIds);

	@Modifying
	@Query("update FactoryEntity t set t.dr = 1 where t.dr <> 0")
	void deleteAll();
}
