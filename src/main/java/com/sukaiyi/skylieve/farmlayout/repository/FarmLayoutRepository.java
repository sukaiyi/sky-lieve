package com.sukaiyi.skylieve.farmlayout.repository;

import com.sukaiyi.skylieve.factory.entity.FactoryEntity;
import com.sukaiyi.skylieve.farmlayout.entity.FarmLayout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author sukaiyi
 */

public interface FarmLayoutRepository extends JpaRepository<FarmLayout, String> {

	@Query("select t from FarmLayout t where t.id = ?1 and t.dr = 0")
	FarmLayout findById(String id);

	@Query("select t from FarmLayout t where t.dr = 0")
	Page<FarmLayout> findAll(Pageable pageable);

	@Modifying
	@Query("update FarmLayout t set t.dr = 1 where t.id in (?1)")
	int deleteByIds(List<String> ids);

	@Modifying
	@Query("update FarmLayout t set t.dr = 1 where t.dr <> 0")
	void deleteAll();
}
