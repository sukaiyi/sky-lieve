package com.sukaiyi.skylieve.global.service.impl;

import com.sukaiyi.skylieve.common.utils.MD5Util;
import com.sukaiyi.skylieve.global.entity.APIKeyEntity;
import com.sukaiyi.skylieve.global.repository.APIKeyRepository;
import com.sukaiyi.skylieve.global.service.APIKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

/**
 * @author sukaiyi
 */
@Service
public class APIKeyServiceImpl implements APIKeyService {

	@Autowired
	APIKeyRepository repository;

	@Override
	public APIKeyEntity create() {
		long time = System.currentTimeMillis();
		String key = MD5Util.encode(time + "");
		APIKeyEntity apiKey = new APIKeyEntity(null, key);
		return repository.save(apiKey);
	}

	@Override
	public boolean validate(String key) {
		APIKeyEntity apiKey = repository.findByKey(key);
		return apiKey != null;
	}

	@Override
	public boolean destroy(String key) {
		return repository.deleteKeys(Collections.singletonList(key)) == 1;
	}
}
