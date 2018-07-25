package com.sukaiyi.skylieve.commonworks.handler;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 收获产品时工厂腾位的message
 *
 * @author sukaiyi
 */
class _10MsgHandler implements MessageHandler {

	@Override
	public void handle(DocumentContext panorama, Object message) {
		ReadContext msgContext = JsonPath.parse(message);
		Map<String, Object> map = msgContext.json();
		String factoryId = map.getOrDefault("factory_id", "").toString();
		List productIds = (List) map.getOrDefault("product_ids", new ArrayList<>());
		if (StringUtils.isEmpty(factoryId) || productIds.size() == 0) {
			return;
		}
		productIds.forEach(productId -> panorama.delete(String.format("$.messages[?(@.msg_type==2)].factories[?(@.id==%s)].products[?(@.product_id==%s)]", factoryId, productId)));
	}
}
