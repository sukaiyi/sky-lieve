package com.sukaiyi.skylieve.global.service;

import com.sukaiyi.skylieve.global.entity.APIKeyEntity;

/**
 * @author sukaiyi
 */
public interface APIKeyService {
	APIKeyEntity create();

	boolean validate(String key);

	boolean destroy(String key);
}
