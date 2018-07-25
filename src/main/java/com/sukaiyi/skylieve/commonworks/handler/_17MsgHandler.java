package com.sukaiyi.skylieve.commonworks.handler;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import net.minidev.json.JSONArray;

/**
 * 更新 npc 订单
 *
 * @author sukaiyi
 */
class _17MsgHandler implements MessageHandler {

	@Override
	public void handle(DocumentContext panorama, Object message) {
		ReadContext msgContext = JsonPath.parse(message);
		String replaceOrderId = msgContext.read("$.order_id").toString();
		JSONArray newOrder = msgContext.read("$.orders[*]");
		// todo 待测
		panorama.set(String.format("$.messages[?(@.msg_type==16)].orders[?(@.order_id==%s)]", replaceOrderId), newOrder);
	}
}
