package com.sukaiyi.skylieve.commonworks;

import com.jayway.jsonpath.DocumentContext;
import com.sukaiyi.skylieve.api.lieve.service.FarmService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 自动为小卖部的商品打广告
 *
 * @author sukaiyi
 */
@Slf4j
@Component
public class StallWork {

	@Autowired
	FarmService farmService;

	public void autoAd(DocumentContext context) throws IOException {
		JSONArray stallItems = context.read("$.messages[?(@.msg_type==20 && @.last_free_ad_time==0 && @.stall_items.length() > 0)].stall_items[?(@.status==1)]");
		if (stallItems == null || stallItems.size() == 0) {
			return;
		}
		Map map = (Map) stallItems.get(0);
		String stallSaleId = map.get("id").toString();
		farmService.stallAd(stallSaleId).execute();
		return;
	}
}

