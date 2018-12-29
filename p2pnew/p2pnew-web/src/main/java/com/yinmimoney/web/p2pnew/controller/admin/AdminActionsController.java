package com.yinmimoney.web.p2pnew.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.AdminActions;
import com.yinmimoney.web.p2pnew.service.IAdminActions;

@Controller("admin_AdminActionsController")
@RequestMapping("/admin/adminActions")
public class AdminActionsController extends BaseController {

	@Autowired
	private IAdminActions adminActionsService;

	@RequestMapping(value = "/list")
	public String list(Model model, AdminActions bean, Integer page) {
		model.addAttribute("bean", bean);

		if (page == null)
			page = 1;
		if (bean == null) {
			bean = new AdminActions();
		}

		bean.setOrderBy("paixu ASC");

		List<AdminActions> list = adminActionsService.getList(bean);
		model.addAttribute("list", list);
		return "admin/adminActions";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, java.lang.Integer id) {
		if (id != null) {
			AdminActions bean = adminActionsService.selectByPrimaryKey(id);
			model.addAttribute("bean", bean);
		}
		AdminActions condition = new AdminActions();
		condition.setOrderBy("paixu ASC");
		List<AdminActions> list = adminActionsService.getList(condition);
		model.addAttribute("list", list);
		return "admin/adminActions_add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(Model model, AdminActions bean) {
		if (bean == null) {
			return "empty";
		}
		if (bean.getPid() != null && bean.getPid() != 0) {
			AdminActions parent = adminActionsService.selectByPrimaryKey(bean.getPid());
			if (parent != null) {
				bean.setLevel(parent.getLevel() + 1);
			}
		} else {
			bean.setLevel(0);
			bean.setPid(0);
			// 生成平台编号
			AdminActions condition = new AdminActions();
			condition.setLevel(0);
			condition.setPid(0);
		}
		if (bean.getId() == null) {
			// 添加
			adminActionsService.insertSelective(bean);
		} else {
			// 修改
			adminActionsService.updateByPrimaryKeySelective(bean);
		}
		return "success";
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public String del(java.lang.Integer id) {
		AdminActions bean = adminActionsService.selectByPrimaryKey(id);
		if (bean != null) {
			adminActionsService.deleteByPrimaryKey(id);
		}
		return "success";
	}
}