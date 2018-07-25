package com.sukaiyi.skylieve.commonworks.handler;

import com.jayway.jsonpath.DocumentContext;

/**
 * @author sukaiyi
 */
public interface MessageHandler {
	void handle(DocumentContext panorama, Object message);
}
