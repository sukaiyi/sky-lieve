package com.sukaiyi.skylieve.commonworks;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.sukaiyi.skylieve.api.lieve.service.FarmService;
import com.sukaiyi.skylieve.config.entity.ConfigEntity;
import com.sukaiyi.skylieve.config.service.ConfigService;
import com.sukaiyi.skylieve.config.utils.ConfigParser;
import com.sukaiyi.skylieve.produce.work.ProduceWork;
import com.sukaiyi.skylieve.purchase.work.PurchaseWork;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;

/**
 * @author sukaiyi
 */
@Slf4j
@Component
public class WorkInvoker {

	@Autowired
	FarmService farmService;

	@Autowired
	BeeWork beeWork;
	@Autowired
	StallWork stallWork;
	@Autowired
	PurchaseWork purchaseWork;
	@Autowired
	ProduceWork produceWork;
	@Autowired
	PurchaseExtinctCropWork purchaseExtinctCropWork;

	@Autowired
	ConfigService configService;

	@Scheduled(fixedDelay = 1000 * 60 * 2)
	@Async
	public void invoke() throws IOException {
		ConfigEntity configEntity = configService.getConfig();
		if (configEntity == null) {
			log.error("未查询到配置项");
			return;
		}
		String panorama = farmService.panorama("", "143").execute().body();
		ReadContext context = JsonPath.parse(panorama);
		Integer result = context.read("$.result");
		if (result != 1) {
			String errorMsg = context.read("$.error_msg");
			log.info(String.format("获取详细信息失败，本次操作取消[%s]:%s", result, errorMsg));
			return;
		}
		DocumentContext panoramaContext = JsonPath.parse(panorama);
		if (ConfigParser.getBoolean(configEntity, "enableStallWork")) {
			stallWork.autoAd(panoramaContext);
		}
		if (ConfigParser.getBoolean(configEntity, "enableBeeWork")) {
			beeWork.harvestBee(panoramaContext);
		}
		if (ConfigParser.getBoolean(configEntity, "enableProduceWork")) {
			produceWork.produce(panoramaContext);
		}
		if (ConfigParser.getBoolean(configEntity, "enablePurchaseWork")) {
			purchaseWork.purchase(panoramaContext);
		}
		if (ConfigParser.getBoolean(configEntity, "enablePurchaseExtinctCropWork")) {
			purchaseExtinctCropWork.work(panoramaContext);
		}

	}
}
