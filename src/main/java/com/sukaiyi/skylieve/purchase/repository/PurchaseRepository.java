package com.sukaiyi.skylieve.purchase.repository;

import com.sukaiyi.skylieve.global.entity.APIKeyEntity;
import com.sukaiyi.skylieve.purchase.entity.PurchaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author sukaiyi
 */

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, String> {

	@Query("select t from PurchaseEntity t where t.id = ?1 and t.dr = 0")
	PurchaseEntity findById(String id);

	@Query("select t from PurchaseEntity t where t.dr = 0")
	Page<PurchaseEntity> findAll(Pageable pageable);

	@Query("select t from PurchaseEntity t where t.dr = 0")
	List<PurchaseEntity> findAll();

	@Query("select t from PurchaseEntity t where t.itemId = ?1 and t.dr = 0")
	PurchaseEntity findByItemId(String key);

	@Modifying
	@Query("update PurchaseEntity t set t.dr = 1 where t.id in (?1)")
	int deleteByIds(List<String> ids);

	@Modifying
	@Query("update PurchaseEntity t set t.dr = 1 where t.itemId in (?1)")
	int deleteByItemIds(List<String> itemIds);

	@Modifying
	@Query("update PurchaseEntity t set t.dr = 1 where t.dr <> 0")
	void deleteAll();
}
