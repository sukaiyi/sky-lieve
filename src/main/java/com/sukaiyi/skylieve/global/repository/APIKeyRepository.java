package com.sukaiyi.skylieve.global.repository;

import com.sukaiyi.skylieve.global.entity.APIKeyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author sukaiyi
 */

public interface APIKeyRepository extends JpaRepository<APIKeyEntity, String> {

	@Query("select t from APIKeyEntity t where t.id = ?1 and t.dr = 0")
	APIKeyEntity findKeyById(String id);

	@Query("select t from APIKeyEntity t where t.dr = 0")
	Page<APIKeyEntity> findAllKeys(Pageable pageable);

	@Query("select t from APIKeyEntity t where t.key = ?1 and t.dr = 0")
	APIKeyEntity findByKey(String key);

	@Modifying
	@Query("update APIKeyEntity t set t.dr = 1 where t.id in (?1)")
	int deleteKeyById(List<String> ids);

	@Modifying
	@Query("update APIKeyEntity t set t.dr = 1 where t.key in (?1)")
	int deleteKeys(List<String> keys);


}
