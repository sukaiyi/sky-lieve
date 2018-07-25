package com.sukaiyi.skylieve.config.utils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.sukaiyi.skylieve.config.entity.ConfigEntity;
import org.springframework.util.StringUtils;

/**
 * @author sukaiyi
 */
public class ConfigParser {

	public static boolean getBoolean(ConfigEntity entity, String key) {
		return getBoolean(entity, key, true);
	}

	public static boolean getBoolean(ConfigEntity entity, String key, boolean def) {
		String config = entity.getConfig();
		if (StringUtils.isEmpty(config)) {
			return def;
		}
		try {
			return JsonPath.read(config, String.format("$.%s", key));
		} catch (PathNotFoundException exception) {
			return def;
		}
	}

	public int getInteger(ConfigEntity entity, String key) {
		return getInteger(entity, key, 0);
	}

	public static int getInteger(ConfigEntity entity, String key, int def) {
		String config = entity.getConfig();
		if (StringUtils.isEmpty(config)) {
			return def;
		}
		try {
			return JsonPath.read(config, String.format("$.%s", key));
		} catch (PathNotFoundException exception) {
			return def;
		}
	}

	public static String getString(ConfigEntity entity, String key) {
		return getString(entity, key, "");
	}

	public static String getString(ConfigEntity entity, String key, String def) {
		String config = entity.getConfig();
		if (StringUtils.isEmpty(config)) {
			return def;
		}
		try {
			return JsonPath.read(config, String.format("$.%s", key));
		} catch (PathNotFoundException exception) {
			return def;
		}
	}

	public static void set(ConfigEntity entity, String key, Object value) {
		String config = entity.getConfig();
		DocumentContext context = JsonPath.parse(config);
		context.set(String.format("$.%s", key), value);
		entity.setConfig(context.jsonString());
	}

}
