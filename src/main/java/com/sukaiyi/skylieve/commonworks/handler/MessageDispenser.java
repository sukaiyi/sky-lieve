package com.sukaiyi.skylieve.commonworks.handler;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sukaiyi
 */
@Slf4j
@Component
public class MessageDispenser {

	private Map<String, MessageHandler> handlers;
	private String[] handlerNames = new String[]{
			"com.sukaiyi.skylieve.commonworks.handler._9MsgHandler",
			"com.sukaiyi.skylieve.commonworks.handler._10MsgHandler",
			"com.sukaiyi.skylieve.commonworks.handler._17MsgHandler",
			"com.sukaiyi.skylieve.commonworks.handler._23MsgHandler",
			"com.sukaiyi.skylieve.commonworks.handler._73MsgHandler",
			"com.sukaiyi.skylieve.commonworks.handler._102MsgHandler",
			"com.sukaiyi.skylieve.commonworks.handler._103MsgHandler",
	};

	public MessageDispenser() {
		handlers = new LinkedHashMap<>();
		for (String handlerName : handlerNames)
			try {
				Class cls = Class.forName(handlerName);
				Class[] interfaces = cls.getInterfaces();
				for (Class c : interfaces) {
					if (c.getTypeName().equals("com.sukaiyi.skylieve.commonworks.handler.MessageHandler")) {
						String clsName = cls.getSimpleName();
						handlers.put(clsName, (MessageHandler) cls.newInstance());
						break;
					}
				}
			} catch (ClassNotFoundException | InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
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
