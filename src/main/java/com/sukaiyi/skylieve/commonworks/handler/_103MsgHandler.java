package com.sukaiyi.skylieve.commonworks.handler;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import net.minidev.json.JSONArray;

import java.util.Map;

/**
 * 经验，金币变化
 *
 * @author sukaiyi
 */
class _103MsgHandler implements MessageHandler {

	@Override
	public void handle(DocumentContext panorama, Object message) {
		ReadContext msgContext = JsonPath.parse(message);
		Map<String, Object> map = msgContext.json();
		String expInc = map.getOrDefault("exp_inc", "0").toString();
		int expIncInt = Integer.parseInt(expInc);
		if (expIncInt != 0) {
			JSONArray levelExpArr = panorama.read("$.messages[?(@.msg_type==2)].level_exp");
			int levelExp = Integer.parseInt(levelExpArr.get(0).toString());
			int total = expIncInt + levelExp;
			panorama.set("$.messages[?(@.msg_type==2)].level_exp", total);
		}

		String goldCost = map.getOrDefault("gold_cost", "0").toString();
		int goldCostInt = Integer.parseInt(goldCost);
		if (goldCostInt != 0) {
			JSONArray goldCoinArr = panorama.read("$.messages[?(@.msg_type==2)].gold_coin");
			int goldCoin = Integer.parseInt(goldCoinArr.get(0).toString());
			int total = goldCoin - goldCostInt;
			panorama.set("$.messages[?(@.msg_type==2)].gold_coin", total);
		}

		String goldInc = map.getOrDefault("gold_inc", "0").toString();
		int goldIncInt = Integer.parseInt(goldInc);
		if (goldIncInt != 0) {
			JSONArray goldCoinArr = panorama.read("$.messages[?(@.msg_type==2)].gold_coin");
			int goldCoin = Integer.parseInt(goldCoinArr.get(0).toString());
			int total = goldCoin + goldIncInt;
			panorama.set("$.messages[?(@.msg_type==2)].gold_coin", total);
		}
	}
}
