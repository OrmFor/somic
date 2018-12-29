package com.yinmimoney.web.p2pnew.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.SysTaskLog;
import com.yinmimoney.web.p2pnew.service.ISysTaskLog;

import cc.s2m.util.Page;

@Controller("admin_SysTaskLogController")
@RequestMapping("/admin/sysTaskLog")
public class SysTaskLogController extends BaseController {

	@Autowired
	private ISysTaskLog sysTaskLogService;

	@RequestMapping(value = "/list")
	public String list(Model model, Integer page, SysTaskLog bean) {
		model.addAttribute("bean", bean);

		if (page == null)
			page = 1;
		if (bean == null) {
			bean = new SysTaskLog();
		}

		bean.setPageNumber(page);
		bean.setPageSize(50);

		Page<SysTaskLog> pageBean = sysTaskLogService.getPage(bean, getRequest().getParameterMap());
		model.addAttribute("pageBean", pageBean);

		return "admin/sysTaskLog";
	}
}
