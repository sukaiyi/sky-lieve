package com.sukaiyi.skylieve.commonworks.handler;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sukaiyi
 */
@Slf4j
@Component
public class MessageDispenser {

	private Map<String, MessageHandler> handlers;

	public MessageDispenser() {
		handlers = new LinkedHashMap<>();
		String basePack = "com.sukaiyi.skylieve.commonworks.handler";
		doSearch(basePack);
	}

	private void doSearch(String pkgName) {
		URI url = null;
		try {
			url = MessageDispenser.class.getClassLoader().getResource(pkgName.replace(".", "/")).toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if (url == null) {
			return;
		}
		File[] files = new File(url).listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				doSearch(pkgName + "." + file.getName());
			}
			if (file.getName().endsWith(".class")) {
				try {
					Class<?> clazz = Class.forName(String.format("%s.%s", pkgName, file.getName().replace(".class", "")));
					Class[] interfaces = clazz.getInterfaces();
					for (Class cls : interfaces) {
						if (cls.getTypeName().equals("com.sukaiyi.skylieve.commonworks.handler.MessageHandler")) {
							handlers.put(clazz.getSimpleName(), (MessageHandler) clazz.newInstance());
						}
					}
				} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void dispense(DocumentContext panorama, ReadContext messages) {
		int resultCode = messages.read("$.result");
		if (resultCode != 1) {
			String errorMsg = messages.read("$.error_msg");
			String caller = Thread.currentThread().getStackTrace()[3].getClassName();
			log.error(String.format("%s:[%s]%s", caller, resultCode, errorMsg));
			return;
		}

		JSONArray messagesArr = messages.read("$.messages[*]");
		messagesArr.forEach(msg -> {
			Map map = (Map) msg;
			String msgType = ((Map) msg).get("msg_type").toString();
			MessageHandler handler = handlers.get(String.format("_%sMsgHandler", msgType));
			if (handler != null) {
				handler.handle(panorama, map);
			} else {
				log.info(String.format("未找到%s消息处理器，已忽略消息:%s", msgType, JsonPath.parse(map).jsonString()));
			}
		});
	}

}
