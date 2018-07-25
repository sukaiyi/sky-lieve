package com.sukaiyi.skylieve.api.lieve.service;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author sukaiyi
 */
public class SigUtil {

	private static final String MAC_NAME = "HmacSHA1";

	public static String calcSig(String method, String url, Map<String, String> param) throws Exception {
		String result = method + "&" + URLEncoder.encode(url, "utf-8");
		List<String> keys = new ArrayList<>(param.keySet());
		Collections.sort(keys);

		StringBuilder paramStr = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			paramStr.append(keys.get(i)).append('=').append(param.get(keys.get(i)));
			if (i < keys.size() - 1) {
				paramStr.append('&');
			}
		}
		result = result + "&" + URLEncoder.encode(paramStr.toString(), "utf-8");
		result = result.replaceAll("/", "%2F");
		return base64(hmacSHA1(result, "8b5b6eca8a9d1d1f")).replaceAll("\r", "").replaceAll("\n", "").trim();
	}

	private static byte[] hmacSHA1(String data, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), MAC_NAME);
		Mac mac = Mac.getInstance(MAC_NAME);
		mac.init(secretKey);
		byte[] text = data.getBytes();
		return mac.doFinal(text);
	}

	private static String base64(byte[] data) {
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(data);
	}
}

