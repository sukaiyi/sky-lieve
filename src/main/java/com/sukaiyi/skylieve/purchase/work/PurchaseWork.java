package com.sukaiyi.skylieve.purchase.work;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.sukaiyi.skylieve.api.lieve.service.FarmService;
import com.sukaiyi.skylieve.commonworks.handler.MessageDispenser;
import com.sukaiyi.skylieve.commonworks.handler.utils.PanoramaUtils;
import com.sukaiyi.skylieve.purchase.entity.PurchaseEntity;
import com.sukaiyi.skylieve.purchase.entity.PurchasedItemEntity;
import com.sukaiyi.skylieve.purchase.repository.PurchasedItemRepository;
import com.sukaiyi.skylieve.purchase.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sukaiyi
 */
@Slf4j
@Component
public class PurchaseWork {

	@Autowired
	FarmService farmService;
	@Autowired
	PurchaseService purchaseService;
	@Autowired
	PurchasedItemRepository purchasedItemRepository;
	@Autowired
	MessageDispenser messageDispenser;

	public void purchase(DocumentContext panoramaContext) throws IOException {
		List<PurchaseEntity> purchaseEntities = purchaseService.findAllEntities();
		if (purchaseEntities.size() <= 0) {
			log.info("没有需要购买的物品");
			return;
		}

		Map<String, Integer> toPurchaseEntities = new LinkedHashMap<>();
		Map<String, Integer> warehouses = PanoramaUtils.getWarehouses(panoramaContext);
		purchaseEntities.forEach(e -> {
			if (e.getNeed() > warehouses.getOrDefault(e.getItemId(), 0)) {
				toPurchaseEntities.put(e.getItemId(), e.getNeed() - warehouses.getOrDefault(e.getItemId(), 0));
			}
		});
		if (toPurchaseEntities.size() <= 0) {
			log.info("没有需要购买的物品");
			return;
		}
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
			ReadContext context = JsonPath.parse(farmService.panorama(sellerId, "999").execute().body());
			result = context.read("$.result");
			if (result != 1) {
				String errorMsg = adContext.read("$.error_msg");
				log.info(String.format("获取农场信息失败[%s][%s]:%s", result, sellerId, errorMsg));
				continue;
			}
			JSONArray stallItems = context.read("$.messages[?(@.msg_type==20)].stall_items[?(@.status != 2)]");
			for (Object obj : stallItems) {
				Map map = (Map) obj;
				String itemId = map.get("item_id").toString();
				if (!toPurchaseEntities.containsKey(itemId)) {
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
				messageDispenser.dispense(panoramaContext, stallBuyContext);
				PurchasedItemEntity purchasedItemEntity = new PurchasedItemEntity(null, itemId, count, coin);
				purchasedItemRepository.save(purchasedItemEntity);
				int remain = toPurchaseEntities.getOrDefault(itemId, 0) - count;
				if (remain <= 0) {
					toPurchaseEntities.remove(itemId);
				} else {
					toPurchaseEntities.put(itemId, remain);
				}
			}
		}
	}
}

