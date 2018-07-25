package com.sukaiyi.skylieve.config.repository;

import com.sukaiyi.skylieve.config.entity.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author sukaiyi
 */

public interface ConfigRepository extends JpaRepository<ConfigEntity, String> {

	@Query("select t from ConfigEntity t where t.dr = 0 order by t.createDate desc")
	ConfigEntity findFirst();
}
