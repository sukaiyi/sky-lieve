package com.sukaiyi.skylieve.commonworks;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.sukaiyi.skylieve.api.lieve.service.FarmService;
import com.sukaiyi.skylieve.commonworks.handler.MessageDispenser;
import com.sukaiyi.skylieve.config.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 自动收获蜂蜜
 *
 * @author sukaiyi
 */
@Slf4j
@Component
public class BeeWork {

	@Autowired
	FarmService farmService;
	@Autowired
	ConfigService configService;
	@Autowired
	MessageDispenser messageDispenser;

	public void harvestBee(DocumentContext context) throws IOException {
		List<Map<String, Object>> beehivesWhitHoney = context.read("$.messages[?(@.msg_type==2)].beehives[?(@.collect_count >= 100)]");
		if (beehivesWhitHoney != null && beehivesWhitHoney.size() > 0) {
			for (Map<String, Object> e : beehivesWhitHoney) {
				DocumentContext beehiveHarvestResult = JsonPath.parse(farmService.beehiveHarvest("211001", e.get("id").toString()).execute().body());
				messageDispenser.dispense(context, beehiveHarvestResult);
			}
		}
	}
}

