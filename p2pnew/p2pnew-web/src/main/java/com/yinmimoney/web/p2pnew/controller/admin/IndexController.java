package com.yinmimoney.web.p2pnew.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yinmimoney.web.p2pnew.controller.base.BaseController;

@Controller("admin_index")
public class IndexController extends BaseController {

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String index(Model model) {
		return "admin/index";
	}
}