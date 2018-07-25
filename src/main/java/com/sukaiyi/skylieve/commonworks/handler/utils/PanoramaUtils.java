package com.sukaiyi.skylieve.commonworks.handler.utils;

import com.jayway.jsonpath.DocumentContext;
import net.minidev.json.JSONArray;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sukaiyi
 */
public class PanoramaUtils {

	/**
	 * 获取库存
	 *
	 * @param context panorama
	 * @return 库存
	 */
	public static Map<String, Integer> getWarehouses(DocumentContext context) {
		Map<String, Integer> warehouses = new LinkedHashMap<>();
		Integer result = context.read("$.result");
		if (result != 1) {
			String errorMsg = context.read("$.error_msg");
			return warehouses;
		}
		JSONArray w = context.read("$.messages[?(@.msg_type==3)].warehouses[*].items[*]");
		w.forEach(e -> {
			Map map = (Map) e;
			warehouses.put(map.get("item_id").toString(), Integer.parseInt(map.get("count").toString()));
		});
		return warehouses;
	}

	/**
	 * 增加某个物品的库存
	 *
	 * @param context  panorama
	 * @param itemId   物品的item_id
	 * @param increase 增加的数量
	 */
	public static void addWarehouses(DocumentContext context, String itemId, int increase) {
		Integer result = context.read("$.result");
		if (result != 1) {
			return;
		}
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
