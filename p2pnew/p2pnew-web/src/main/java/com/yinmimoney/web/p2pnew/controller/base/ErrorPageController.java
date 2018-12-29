package com.yinmimoney.web.p2pnew.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController extends BaseController {

	@RequestMapping(value = "/404")
	public String error404(Model model) {
		return "404";
	}

	@RequestMapping(value = "/500")
	public String error500(Model model) {
		return "500";
	}
}
