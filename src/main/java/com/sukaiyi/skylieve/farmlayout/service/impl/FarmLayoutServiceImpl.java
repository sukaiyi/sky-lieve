package com.sukaiyi.skylieve.farmlayout.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.sukaiyi.skylieve.api.lieve.service.FarmService;
import com.sukaiyi.skylieve.farmlayout.entity.FarmLayout;
import com.sukaiyi.skylieve.farmlayout.entity.ItemPosition;
import com.sukaiyi.skylieve.farmlayout.repository.FarmLayoutRepository;
import com.sukaiyi.skylieve.farmlayout.service.FarmLayoutService;
import com.sukaiyi.skylieve.items.repository.ItemProduceRepository;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author sukaiyi
 */
@Slf4j
@Service
public class FarmLayoutServiceImpl implements FarmLayoutService {

	@Autowired
	FarmService farmService;
	@Autowired
	FarmLayoutRepository repository;

	@Override
	public void backup() throws IOException {
		ReadContext context = JsonPath.parse(farmService.panorama("", "143").execute().body());
		int r = context.read("$.result");
		if (r != 1) {
			String errorMsg = context.read("$.error_msg");
			log.error(String.format("备份失败[%s]：%s", r, errorMsg));
			return;
		}
		List<Map> factories = context.read("$.messages[?(@.msg_type==2)].factories[*]");
		List<Map> decorations = context.read("$.messages[?(@.msg_type==2)].decorations[*]");
		List<Map> beehives = context.read("$.messages[?(@.msg_type==2)].beehives[*]");
		List<Map> baseBuildings = context.read("$.messages[?(@.msg_type==2)].base_buildings[*]");
		List<Map> petHouses = context.read("$.messages[?(@.msg_type==2)].pet_houses[*]");
		List<Map> yards = context.read("$.messages[?(@.msg_type==2)].yards[*]");
		List<Map> fields = context.read("$.messages[?(@.msg_type==2)].fields[*]");
		List<Map> fruitTrees = context.read("$.messages[?(@.msg_type==2)].fruit_trees[*]");
		List<Map> flowerBushes = context.read("$.messages[?(@.msg_type==2)].flower_bushes[*]");
		Map<String, List<Map>> allItems = new LinkedHashMap<>();
		allItems.put(ItemPosition.ItemType.Factory.name(), factories);
		allItems.put(ItemPosition.ItemType.Decoration.name(), decorations);
		allItems.put(ItemPosition.ItemType.Beehive.name(), beehives);
		allItems.put(ItemPosition.ItemType.BaseBuilding.name(), baseBuildings);
		allItems.put(ItemPosition.ItemType.PetHouse.name(), petHouses);
		allItems.put(ItemPosition.ItemType.Yard.name(), yards);
		allItems.put(ItemPosition.ItemType.Field.name(), fields);
		allItems.put(ItemPosition.ItemType.FruitTree.name(), fruitTrees);
		allItems.put(ItemPosition.ItemType.FlowerBushes.name(), flowerBushes);

		List<ItemPosition> itemPositions = new LinkedList<>();
		allItems.forEach((k, v) -> {
			v.forEach(m -> {
				String id = m.get("id").toString();
				int x = Integer.parseInt(m.get("x").toString());
				int y = Integer.parseInt(m.get("y").toString());
				int rotate = Integer.parseInt(m.get("rotate").toString());
				ItemPosition position = new ItemPosition(id, x, y, rotate, k);
				itemPositions.add(position);
			});
		});
		String itemPositionsJson = JsonPath.parse(itemPositions).jsonString();
		FarmLayout farmLayout = new FarmLayout(null, itemPositionsJson, "");
		repository.save(farmLayout);
	}

	@Override
	public void restore(String id) {

	}

	@Override
	public void delete(String id) {

	}
}
