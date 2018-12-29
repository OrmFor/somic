package com.yinmimoney.web.p2pnew.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.util.ResultCode;
import com.yinmimoney.web.p2pnew.util.StatusCode;

@Controller
@RequestMapping(value = "/api/2.0")
public class Index extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResultCode index() {
		JSONObject json = new JSONObject(true);
		json.put("version", "2.0");
		json.put("company", "杭州银米互联网金融服务有限公司");
		json.put("site", "https://www.yinmimoney.com/");
		return new ResultCode(StatusCode.SUCCESS, json);
	}

}
