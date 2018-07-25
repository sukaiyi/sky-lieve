package com.sukaiyi.skylieve.config.service;

import com.sukaiyi.skylieve.config.entity.ConfigEntity;

/**
 * @author sukaiyi
 */
public interface ConfigService {
	ConfigEntity turnOffAll();
	ConfigEntity turnOnAll();
	ConfigEntity getConfig();
	ConfigEntity insert(ConfigEntity entity);
}
