package com.sukaiyi.skylieve.config.controller;

import com.sukaiyi.skylieve.common.vo.ResultVO;
import com.sukaiyi.skylieve.config.entity.ConfigEntity;
import com.sukaiyi.skylieve.config.service.ConfigService;
import com.sukaiyi.skylieve.global.annotation.APIKeyRequired;
import com.sukaiyi.skylieve.global.exception.exceptions.code.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukaiyi
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

	@Autowired
	private ConfigService configService;

	@APIKeyRequired
	@RequestMapping(value = "turnOffAll", method = RequestMethod.GET)
	public ResultVO turnOffAll() {
		return ResultVO.ok("操作成功", configService.turnOffAll());
	}

	@APIKeyRequired
	@RequestMapping(value = "turnOnAll", method = RequestMethod.GET)
	public ResultVO turnOnAll() {
		return ResultVO.ok("操作成功", configService.turnOnAll());
	}
}
