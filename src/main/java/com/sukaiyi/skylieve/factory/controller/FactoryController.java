package com.sukaiyi.skylieve.factory.controller;

import com.sukaiyi.skylieve.common.vo.ResultVO;
import com.sukaiyi.skylieve.factory.service.FactoryService;
import com.sukaiyi.skylieve.factory.vo.FactoryVO;
import com.sukaiyi.skylieve.global.annotation.APIKeyRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sukaiyi
 */
@RestController
@RequestMapping("/factory")
public class FactoryController {

	@Autowired
	private FactoryService factoryService;

	@APIKeyRequired
	@RequestMapping(value = "clear", method = RequestMethod.POST)
	public ResultVO clear() {
		factoryService.clear();
		return ResultVO.ok("操作成功", null);
	}

	@APIKeyRequired
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResultVO add(@RequestBody List<FactoryVO> factories) {
		return ResultVO.ok("操作成功", factoryService.insert(factories));
	}

	@APIKeyRequired
	@RequestMapping(value = "all")
	public ResultVO findAll(@PageableDefault(value = 10) Pageable pageable) {
		return ResultVO.ok("查询成功", factoryService.findAll(pageable));
	}

}
