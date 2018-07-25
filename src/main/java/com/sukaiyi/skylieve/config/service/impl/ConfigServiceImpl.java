package com.sukaiyi.skylieve.config.service.impl;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.sukaiyi.skylieve.config.entity.ConfigEntity;
import com.sukaiyi.skylieve.config.repository.ConfigRepository;
import com.sukaiyi.skylieve.config.service.ConfigService;
import com.sukaiyi.skylieve.config.utils.ConfigParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sukaiyi
 */
@Service
public class ConfigServiceImpl implements ConfigService {

	private final ConfigRepository repository;

	@Autowired
	public ConfigServiceImpl(ConfigRepository repository) {
		this.repository = repository;
	}

	@Override
	public ConfigEntity turnOffAll() {
		ConfigEntity configEntity = repository.findFirst();

		String config = configEntity.getConfig();
		DocumentContext context = JsonPath.parse(config);
		context.set("$.enableStallWork", false);
		context.set("$.enableBeeWork", false);
		context.set("$.enableProduceWork", false);
		context.set("$.enablePurchaseWork", false);
		configEntity.setConfig(context.jsonString());

		return repository.save(configEntity);
	}

	@Override
	public ConfigEntity turnOnAll() {
		ConfigEntity configEntity = repository.findFirst();

		String config = configEntity.getConfig();
		DocumentContext context = JsonPath.parse(config);
		context.set("$.enableStallWork", true);
		context.set("$.enableBeeWork", true);
		context.set("$.enableProduceWork", true);
		context.set("$.enablePurchaseWork", true);
		configEntity.setConfig(context.jsonString());

		return repository.save(configEntity);
	}

	@Override
	public ConfigEntity getConfig() {
		return repository.findFirst();
	}

	@Override
	public ConfigEntity insert(ConfigEntity entity) {
		return repository.save(entity);
	}
}
