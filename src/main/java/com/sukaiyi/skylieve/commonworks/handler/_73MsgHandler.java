package com.sukaiyi.skylieve.commonworks.handler;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.sukaiyi.skylieve.commonworks.handler.utils.PanoramaUtils;
import net.minidev.json.JSONArray;

import java.util.List;
import java.util.Map;

/**
 * 辛勤劳作获得了某个物品
 *
 * @author sukaiyi
 */
class _73MsgHandler implements MessageHandler {

	@Override
	public void handle(DocumentContext panorama, Object message) {
		Map map = (Map) message;
		String itemId = map.getOrDefault("item_id", "0").toString();
		PanoramaUtils.addWarehouses(panorama, itemId, 1);
	}
}
