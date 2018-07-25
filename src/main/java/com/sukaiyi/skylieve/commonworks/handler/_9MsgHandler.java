package com.sukaiyi.skylieve.commonworks.handler;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import net.minidev.json.JSONArray;

/**
 * 生产产品时，工厂占位
 *
 * @author sukaiyi
 */
class _9MsgHandler implements MessageHandler {

	@Override
	public void handle(DocumentContext panorama, Object message) {
		ReadContext msgContext = JsonPath.parse(message);
		String factoryId = msgContext.read("$.factory_id").toString();
		JSONArray products = msgContext.read("$.products[*]");
		panorama.set(String.format("$.messages[?(@.msg_type==2)].factories[?(@.id==%s)].products", factoryId), products);
	}
}
