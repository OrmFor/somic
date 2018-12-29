package com.yinmimoney.web.p2pnew.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yinmimoney.web.p2pnew.controller.base.BaseController;

@Controller
public class Index extends BaseController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "redirect:/admin";
	}

	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
	public String toPage(@PathVariable String pageName) {
		return "admin/" + pageName;
	}
}
