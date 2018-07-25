package com.sukaiyi.skylieve.produce.work;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.sukaiyi.skylieve.api.lieve.service.FarmService;
import com.sukaiyi.skylieve.commonworks.handler.MessageDispenser;
import com.sukaiyi.skylieve.commonworks.handler.utils.PanoramaUtils;
import com.sukaiyi.skylieve.factory.entity.FactoryEntity;
import com.sukaiyi.skylieve.factory.service.FactoryService;
import com.sukaiyi.skylieve.items.entity.ItemEntity;
import com.sukaiyi.skylieve.items.entity.ItemProduceEntity;
import com.sukaiyi.skylieve.items.service.ItemProduceService;
import com.sukaiyi.skylieve.items.service.ItemService;
import com.sukaiyi.skylieve.produce.entity.ProduceEntity;
import com.sukaiyi.skylieve.produce.service.ProduceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author sukaiyi
 */
@Slf4j
@Component
public class ProduceWork {

	@Autowired
	FarmService farmService;
	@Autowired
	ProduceService produceService;
	@Autowired
	ItemService itemService;
	@Autowired
	ItemProduceService itemProduceService;
	@Autowired
	FactoryService factoryService;
	@Autowired
	MessageDispenser messageDispenser;

	public void produce(DocumentContext panoramaContext) throws IOException {
		List<ProduceEntity> produceEntities = produceService.findAllEntities();
		if (produceEntities.size() <= 0) {
			log.info("没有需要生产的物品");
			return;
		}

		/**
		 * 收获工厂产品，并返回工厂空位
		 */
		Map<String, Integer> factoryIdBlankPosition = harvestFactories(panoramaContext);

		Map<String, Integer> toProduceItemMap = new LinkedHashMap<>();
		Map<String, Integer> warehouses = PanoramaUtils.getWarehouses(panoramaContext);
		produceEntities.forEach(e -> {
			if (e.getNeed() > warehouses.getOrDefault(e.getItemId(), 0)) {
				toProduceItemMap.put(e.getItemId(), e.getNeed());
			}
		});
		if (toProduceItemMap.size() <= 0) {
			log.info("没有需要生产的物品");
			return;
		}

		List<String> toProduceItemId = new ArrayList<>(toProduceItemMap.keySet());
		List<ItemProduceEntity> produceItemNeed = itemProduceService.findByProduceItemIds(toProduceItemId);
		Map<String, Map<String, Integer>> produceItemNeedMap = new HashMap<>();
		produceItemNeed.forEach(e -> {
			String produceItemId = e.getProduceItemId();
			String needItemId = e.getNeedItemId();
			int count = e.getCount();
			Map<String, Integer> map = produceItemNeedMap.getOrDefault(produceItemId, new HashMap<>());
			map.put(needItemId, count);
			produceItemNeedMap.put(produceItemId, map);
		});

		List<ItemEntity> toProduceItems = itemService.findByItemIds(toProduceItemId);
		List<String> factoryItemIds = new LinkedList<>();
		toProduceItems.forEach(e -> {
			String factoryItemId = e.getFactory();
			if (factoryItemId != null) {
				factoryItemIds.add(factoryItemId);
			}
		});
		if (factoryItemIds.size() <= 0) {
			return;
		}

		List<FactoryEntity> factoryEntities = factoryService.findByItemIds(factoryItemIds);
		/**
		 * factoryMap -> (factory_item_id, factory)
		 */
		Map<String, List<FactoryEntity>> factoryMap = new LinkedHashMap<>();
		factoryEntities.forEach(e -> {
			List<FactoryEntity> es = factoryMap.getOrDefault(e.getItemId(), new LinkedList<>());
			es.add(e);
			factoryMap.put(e.getItemId(), es);
		});

		/**
		 * 开始生产
		 */
		for (ItemEntity item : toProduceItems) {
			// 测试材料是否足够
			boolean itemEnough = true;
			Map<String, Integer> needItems = produceItemNeedMap.get(item.getItemId());
			if (needItems != null && needItems.size() > 0) {
				for (String needItemId : needItems.keySet()) {
					if (needItems.get(needItemId) > warehouses.getOrDefault(needItemId, 0)) {
						itemEnough = false;
						break;
					}
				}
			}
			if (!itemEnough) {
				log.info(String.format("未生产%s:材料不足", item.getName()));
				continue;
			}

			// 寻找有空位的工厂
			List<FactoryEntity> factories = factoryMap.get(item.getFactory());
			if (factories == null || factories.size() == 0) {
				continue;
			}
			FactoryEntity toUseFactory = null;
			for (FactoryEntity factory : factories) {
				int remain = factoryIdBlankPosition.getOrDefault(factory.getId(), 0);
				if (remain > 0) {
					toUseFactory = factory;
					break;
				}
			}
			if (toUseFactory != null) {
				if (produce(panoramaContext, toUseFactory, item) == 1) {
					int remain = factoryIdBlankPosition.getOrDefault(toUseFactory.getId(), 0);
					factoryIdBlankPosition.put(toUseFactory.getId(), remain - 1);
				}
			}
		}
	}

	private Map<String, Integer> harvestFactories(DocumentContext context) throws IOException {
		Map<String, Integer> factoryIdBlankPosition = new LinkedHashMap<>();
		List<Map<String, Object>> factories = context.read("$.messages[?(@.msg_type==2)].factories[*]");
		for (Map<String, Object> f : factories) {
			String factoryId = f.get("id").toString();
			int blankPosition = Integer.parseInt(f.get("capacity").toString()) - ((List) f.get("products")).size();
			List<Map<String, Object>> matureProducts = JsonPath.read(f.get("products"), "$[?(@.left_time <= 0)]");
			for (Map<String, Object> p : matureProducts) {
				String productId = p.get("product_id").toString();
				DocumentContext harvestResult = JsonPath.parse(farmService.factoryHarvest(factoryId, productId, "0").execute().body());
				Integer result = harvestResult.read("$.result");
				if (result == 1) {
					messageDispenser.dispense(context, harvestResult);
					blankPosition++;
				}
			}
			factoryIdBlankPosition.put(factoryId, blankPosition);
		}
		return factoryIdBlankPosition;
	}

	private int produce(DocumentContext context, FactoryEntity factory, ItemEntity item) throws IOException {
		String itemId = item.getItemId();
		String factoryId = factory.getId();
		ReadContext produceContext = JsonPath.parse(farmService.produce(factoryId, itemId).execute().body());
		Integer r = produceContext.read("$.result");
		messageDispenser.dispense(context, produceContext);
		return r;
	}
}

