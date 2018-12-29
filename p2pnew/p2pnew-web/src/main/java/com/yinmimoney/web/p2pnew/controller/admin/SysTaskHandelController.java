package com.yinmimoney.web.p2pnew.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.constant.SysLogConstant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.SysTaskHandel;
import com.yinmimoney.web.p2pnew.service.ISysTaskHandel;

@Controller("admin_SysTaskHandelController")
@RequestMapping("/admin/sysTaskHandel")
public class SysTaskHandelController extends BaseController {

	@Autowired
	private ISysTaskHandel sysTaskHandelService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		SysTaskHandel condition = new SysTaskHandel();
		condition.setOrderBy("module_name ASC , hostname ASC");
		List<SysTaskHandel> list = sysTaskHandelService.getList(condition);
		model.addAttribute("list", list);

		return "admin/sysTaskHandel";
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public String del(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) Integer id) {
		SysTaskHandel bean = sysTaskHandelService.selectByPrimaryKey(id);
		if (bean != null) {
			addSysLog(SysLogConstant.MODULE_TASK, SysLogConstant.OPRATE_DEL, JSONObject.toJSONString(bean));
			sysTaskHandelService.deleteByPrimaryKey(id);
		}
		return "success";
	}

	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@ResponseBody
	public String enable(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) Integer id) {
		SysTaskHandel bean = sysTaskHandelService.selectByPrimaryKey(id);
		if (bean != null) {
			bean.setIsEnabled(!bean.getIsEnabled());
			sysTaskHandelService.updateByPrimaryKey(bean);
			addSysLog(SysLogConstant.MODULE_TASK, SysLogConstant.OPRATE_EDIT, JSONObject.toJSONString(bean));
		}
		return "success";
	}
}
