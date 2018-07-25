package com.sukaiyi.skylieve.commonworks;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.sukaiyi.skylieve.api.lieve.service.FarmService;
import com.sukaiyi.skylieve.commonworks.handler.MessageDispenser;
import com.sukaiyi.skylieve.commonworks.handler.utils.PanoramaUtils;
import com.sukaiyi.skylieve.config.service.ConfigService;
import com.sukaiyi.skylieve.items.service.ItemService;
import com.sukaiyi.skylieve.purchase.entity.PurchasedItemEntity;
import com.sukaiyi.skylieve.purchase.repository.PurchasedItemRepository;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author sukaiyi
 */
@Slf4j
@Component
public class PurchaseExtinctCropWork {


	@Autowired
	FarmService farmService;
	@Autowired
	ItemService itemService;
	@Autowired
	PurchasedItemRepository purchasedItemRepository;
	@Autowired
	MessageDispenser messageDispenser;

	public void work(DocumentContext context) throws IOException {
		Map<String, Integer> warehouses = PanoramaUtils.getWarehouses(context);
		List<Integer> fields = context.read("$.messages[?(@.msg_type==2)].fields[?(@.plant_item_id!=-1)].plant_item_id");
		fields.forEach(e -> {
			String itemId = e.toString();
			warehouses.put(itemId, warehouses.getOrDefault(itemId, 0) + 2);
		});
		String[] baseItemId = new String[]{
				"201001", "201002", "201003", "201004", "201005",
				"201006", "201007", "201008", "201009", "201010",
				"201011", "201012", "201013", "201014", "201015"
		};
		for (String itemId : baseItemId) {
			warehouses.put(itemId, warehouses.getOrDefault(itemId, 0));
		}
		Set<String> emptyItemId = new HashSet<>();
		warehouses.forEach((k, v) -> {
			if (v == 0) {
				emptyItemId.add(k);
			}
		});

		String ad = farmService.adQuery().execute().body();
		ReadContext adContext = JsonPath.parse(ad);
		Integer result = adContext.read("$.result");
		if (result != 1) {
			String errorMsg = adContext.read("$.error_msg");
			log.info(String.format("获取广告列表失败[%s]:%s", result, errorMsg));
			return;
		}
		List<String> sellerIds = adContext.read("$.messages[?(@.msg_type==28)].ad_items[*].seller_farm_id");
		for (String sellerId : sellerIds) {
			ReadContext sellerContext = JsonPath.parse(farmService.panorama(sellerId, "999").execute().body());
			result = sellerContext.read("$.result");
			if (result != 1) {
				String errorMsg = adContext.read("$.error_msg");
				log.info(String.format("获取农场信息失败[%s][%s]:%s", result, sellerId, errorMsg));
				continue;
			}
			JSONArray stallItems = sellerContext.read("$.messages[?(@.msg_type==20)].stall_items[?(@.status != 2)]");
			for (Object obj : stallItems) {
				Map map = (Map) obj;
				String itemId = map.get("item_id").toString();
				if (!emptyItemId.contains(itemId)) {
					continue;
				}
				String saleId = map.get("id").toString();
				int count = Integer.parseInt(map.get("count").toString());
				int coin = Integer.parseInt(map.get("coin").toString());

				ReadContext stallBuyContext = JsonPath.parse(farmService.stallBuy(sellerId, saleId).execute().body());
				result = stallBuyContext.read("$.result");
				if (result != 1) {
					String errorMsg = stallBuyContext.read("$.error_msg");
					log.info(String.format("购买失败[%s]:%s", result, errorMsg));
					continue;
				}
				messageDispenser.dispense(context, stallBuyContext);
				PurchasedItemEntity purchasedItemEntity = new PurchasedItemEntity(null, itemId, count, coin);
				purchasedItemRepository.save(purchasedItemEntity);
				emptyItemId.remove(itemId);
			}
		}
	}

}
