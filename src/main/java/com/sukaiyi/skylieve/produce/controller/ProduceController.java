package com.sukaiyi.skylieve.produce.controller;

import com.sukaiyi.skylieve.common.vo.ResultVO;
import com.sukaiyi.skylieve.global.annotation.APIKeyRequired;
import com.sukaiyi.skylieve.produce.service.ProduceService;
import com.sukaiyi.skylieve.produce.vo.ProduceVO;
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
@RequestMapping("/produce")
public class ProduceController {

	@Autowired
	private ProduceService purchaseService;

	@APIKeyRequired
	@RequestMapping(value = "clear", method = RequestMethod.POST)
	public ResultVO clear() {
		purchaseService.clear();
		return ResultVO.ok("操作成功", null);
	}

	@APIKeyRequired
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResultVO add(@RequestBody List<ProduceVO> items) {
		return ResultVO.ok("操作成功", purchaseService.insert(items));
	}

	@APIKeyRequired
	@RequestMapping(value = "all")
	public ResultVO findAll(@PageableDefault(value = 10) Pageable pageable) {
		return ResultVO.ok("查询成功", purchaseService.findAll(pageable));
	}

}
