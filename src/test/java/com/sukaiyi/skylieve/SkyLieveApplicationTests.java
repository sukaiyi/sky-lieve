package com.sukaiyi.skylieve;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.sukaiyi.skylieve.api.lieve.service.FarmService;
import com.sukaiyi.skylieve.commonworks.WorkInvoker;
import com.sukaiyi.skylieve.commonworks.handler.utils.PanoramaUtils;
import com.sukaiyi.skylieve.config.service.ConfigService;
import com.sukaiyi.skylieve.items.entity.ItemProduceEntity;
import com.sukaiyi.skylieve.items.service.ItemProduceService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SkyLieveApplicationTests {

	@Autowired
	FarmService farmService;
	@Autowired
	WorkInvoker invoker;
	@Autowired
	ItemProduceService itemProduceService;
	@Autowired
	ConfigService configService;

	@Test
	public void test() throws IOException {
		String panorama = farmService.panorama("", "143").execute().body();
		ReadContext context = JsonPath.parse(panorama);
		Integer result = context.read("$.result");
		if (result != 1) {
			String errorMsg = context.read("$.error_msg");
			log.info(String.format("操作失败[%s]:%s", result, errorMsg));
			return;
		}
		List<Integer> buildings = context.read("$.messages[?(@.msg_type==2)].decorations[?(@.item_id == 111066 && @.status == 0)].id");
		buildings.forEach(e -> {
			try {
				String body = farmService.buildingSwitch(e.toString(), "111066", "1").execute().body();
				log.info(body);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}

	@Test
	public void testAdd() throws IOException {
		String panorama = farmService.panorama("", "143").execute().body();
		ReadContext context = JsonPath.parse(panorama);
		Integer result = context.read("$.result");
		if (result != 1) {
			String errorMsg = context.read("$.error_msg");
			log.info(String.format("操作失败[%s]:%s", result, errorMsg));
			return;
		}
		for (int i = 53; i >= 30; i--) {
			String body = farmService.buildingAdd("111065", i + "", "30", "0").execute().body();
			log.info(body);
		}
	}

	@Test
	public void testWorkInvoker() throws IOException {
		invoker.invoke();
	}

	@Test
	public void testPanoramaUtils() throws IOException {
		String panorama = farmService.panorama("", "143").execute().body();
		DocumentContext context = JsonPath.parse(panorama);
		PanoramaUtils.addWarehouses(context, "201001", 2);
	}

	@Test
	public void importItemProduce() throws IOException {
		File file = new File("D:\\Projects\\sublime projects\\oh_my_love\\data\\temp.py.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			DocumentContext context = JsonPath.parse(line);
			int r = context.read("$.result");
			Assert.assertEquals(r, 1);

			JSONArray arr = context.read("$.messages[?(@.msg_type==9)].product_item_id");
			String productItemId = arr.get(0).toString();
			List<Map<String, Integer>> cost = context.read("$.messages[?(@.msg_type==102)].warehouses[*].items_cost[*]");
			cost.forEach(e -> {
				int count = e.get("count");
				String itemId = e.get("item_id").toString();
				itemProduceService.save(new ItemProduceEntity(null, productItemId, itemId, count));
			});
		}
	}
}
