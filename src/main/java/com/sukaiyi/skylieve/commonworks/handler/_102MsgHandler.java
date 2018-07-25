package com.sukaiyi.skylieve.commonworks.handler;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import net.minidev.json.JSONArray;

import java.util.List;
import java.util.Map;

/**
 * 库存增加或减少的message
 *
 * @author sukaiyi
 */
class _102MsgHandler implements MessageHandler {

	@Override
	public void handle(DocumentContext panorama, Object message) {
		ReadContext msgContext = JsonPath.parse(message);
		int msgType = msgContext.read("$.msg_type");
		if (msgType != 102) {
			return;
		}
		List<Map<String, Integer>> itemsIncs = msgContext.read("$.warehouses[*].items_inc[*]");
		itemsIncs.forEach(e -> {
			String itemId = e.get("item_id").toString();
			int count = e.get("count");
			addWarehouses(panorama, itemId, count);
		});
		List<Map<String, Integer>> itemsCosts = msgContext.read("$.warehouses[*].items_cost[*]");
		itemsCosts.forEach(e -> {
			String itemId = e.get("item_id").toString();
			int count = e.get("count");
			addWarehouses(panorama, itemId, -count);
		});
	}

	private void addWarehouses(DocumentContext context, String itemId, int increase) {
		//读取原库存
		String jsonPath = String.format("$.messages[?(@.msg_type==3)].warehouses[*].items[?(@.item_id==%s)].count", itemId);
		JSONArray arr = context.read(jsonPath);
		if (arr == null || arr.size() != 1) {
			return;
		}
		int count = Integer.parseInt(arr.get(0).toString());
		//设置新的库存
		context.set(jsonPath, count + increase);
	}
}
