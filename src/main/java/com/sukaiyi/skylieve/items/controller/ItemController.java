package com.sukaiyi.skylieve.items.controller;

import com.sukaiyi.skylieve.common.vo.ResultVO;
import com.sukaiyi.skylieve.global.annotation.APIKeyRequired;
import com.sukaiyi.skylieve.items.service.ItemService;
import com.sukaiyi.skylieve.items.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sukaiyi
 */
@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@APIKeyRequired
	@RequestMapping(value = "clear", method = RequestMethod.POST)
	public ResultVO clear() {
		itemService.clear();
		return ResultVO.ok("操作成功", null);
	}

	@APIKeyRequired
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResultVO add(@RequestBody List<ItemVO> items) {
		return ResultVO.ok("操作成功", itemService.insert(items));
	}

	@APIKeyRequired
	@RequestMapping(value = "all")
	public ResultVO findAll(@PageableDefault(value = 10) Pageable pageable) {
		return ResultVO.ok("查询成功", itemService.findAll(pageable));
	}

}
