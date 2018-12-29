package com.yinmimoney.web.p2pnew.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.constant.SysLogConstant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.enums.EnumAdminStatus;
import com.yinmimoney.web.p2pnew.enums.EnumUserCodeType;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.AdminRoles;
import com.yinmimoney.web.p2pnew.pojo.Roles;
import com.yinmimoney.web.p2pnew.service.IAdmin;
import com.yinmimoney.web.p2pnew.service.IAdminRoles;
import com.yinmimoney.web.p2pnew.service.IRoles;
import com.yinmimoney.web.p2pnew.util.UserCodeGenerator;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.util.AccountDigestUtils;

@Controller("admin_AdminController")
@RequestMapping("/admin/admin")
public class AdminController extends BaseController {

	@Autowired
	private IAdmin adminService;
	@Autowired
	private IAdminRoles adminRolesService;
	@Autowired
	private IRoles rolesService;
	@Autowired
	private UserCodeGenerator userCodeGenerator;

	@RequestMapping(value = "/list")
	public String list(Model model, Admin bean, Integer page) {
		model.addAttribute("bean", bean);

		if (page == null)
			page = 1;
		if (bean == null) {
			bean = new Admin();
		}

		bean.setPageNumber(page);
		bean.setPageSize(50);

		Page<Admin> pageBean = adminService.getPage(bean, getRequest().getParameterMap());
		model.addAttribute("pageBean", pageBean);
		return "admin/admin";
	}

	@RequestMapping(value = "/lock", method = RequestMethod.POST)
	@ResponseBody
	public String lock(java.lang.Integer id) {
		Admin bean = adminService.selectByPrimaryKey(id);
		if (bean == null) {
			return "不存在该管理员";
		}
		Admin condition = new Admin();
		condition.setId(id);
		condition.setStatus(EnumAdminStatus.STATUS_NORMAL.getStatus());
		Admin update = new Admin();
		update.setStatus(EnumAdminStatus.STATUS_LOCK.getStatus());
		int row = adminService.updateByCondition(update, condition);
		if (row != 1) {
			return "该状态不可锁定";
		}
		addSysLog(SysLogConstant.MODULE_ADMINS, "锁定", JSONObject.toJSONString(bean));
		return "success";
	}

	@RequestMapping(value = "/unlock", method = RequestMethod.POST)
	@ResponseBody
	public String unlock(java.lang.Integer id) {
		Admin bean = adminService.selectByPrimaryKey(id);
		if (bean == null) {
			return "不存在该管理员";
		}
		Admin condition = new Admin();
		condition.setId(id);
		condition.setStatus(EnumAdminStatus.STATUS_LOCK.getStatus());
		Admin update = new Admin();
		update.setStatus(EnumAdminStatus.STATUS_NORMAL.getStatus());
		int row = adminService.updateByCondition(update, condition);
		if (row != 1) {
			return "该状态不可解除锁定";
		}
		addSysLog(SysLogConstant.MODULE_ADMINS, "解除锁定", JSONObject.toJSONString(bean));
		return "success";
	}

	@RequestMapping(value = "/leave", method = RequestMethod.POST)
	@ResponseBody
	public String leave(java.lang.Integer id) {
		Admin bean = adminService.selectByPrimaryKey(id);
		if (bean == null) {
			return "不存在该管理员";
		}
		Admin condition = new Admin();
		condition.setId(id);
		condition.setStatus(EnumAdminStatus.STATUS_NORMAL.getStatus());
		Admin update = new Admin();
		update.setStatus(EnumAdminStatus.STATUS_LEAVE.getStatus());
		int row = adminService.updateByCondition(update, condition);
		if (row != 1) {
			return "该状态不可离职";
		}
		addSysLog(SysLogConstant.MODULE_ADMINS, "离职", JSONObject.toJSONString(bean));
		return "success";
	}

	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public String resetPwd(java.lang.Integer id, String pwd) {
		if (id == null) {
			return "noAdmin";
		}
		if (Strings.isNullOrEmpty(pwd)) {
			return "noPwd";
		}
		Admin bean = adminService.selectByPrimaryKey(id);
		if (bean == null) {
			return "noAdmin";
		}
		bean.setPwd(AccountDigestUtils.getMd5Pwd(bean.getUserName(), pwd.trim()));
		adminService.updateByPrimaryKeySelective(bean);
		addSysLog(SysLogConstant.MODULE_ADMINS, "重置密码", JSONObject.toJSONString(bean));
		return "success";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, java.lang.Integer id) {
		if (id != null) {
			Admin bean = adminService.selectByPrimaryKey(id);
			model.addAttribute("bean", bean);
			// 获取该管理员的角色
			AdminRoles condition = new AdminRoles();
			condition.setAid(id);
			List<AdminRoles> myRoles = adminRolesService.getList(condition);
			model.addAttribute("myRoles", myRoles);
		}
		// 取出所有角色
		List<Roles> roles = rolesService.getList();
		model.addAttribute("roles", roles);
		return "admin/admin_add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(Model model, Admin bean, Integer[] roleIds) {
		if (bean == null) {
			return "empty";
		}
		// username重复性检测
		Admin condition = new Admin();
		condition.setUserName(bean.getUserName());
		condition = adminService.getByCondition(condition);
		if (bean.getId() == null) {
			// 添加
			if (condition != null) {
				return "userNameExist";
			}
			bean.setPwd(AccountDigestUtils.getMd5Pwd(bean.getUserName(), bean.getPwd()));
			bean.setUserCode(userCodeGenerator.getUserCode(EnumUserCodeType.TYPE_ADMIN.getType()));
			adminService.insertSelective(bean);
			addSysLog(SysLogConstant.MODULE_ADMINS, SysLogConstant.OPRATE_ADD, JSONObject.toJSONString(bean));
		} else {
			// 修改
			if (condition != null && !condition.getId().equals(bean.getId())) {
				return "userNameExist";
			}
			adminService.updateByPrimaryKeySelective(bean);
			addSysLog(SysLogConstant.MODULE_ADMINS, SysLogConstant.OPRATE_EDIT, JSONObject.toJSONString(bean));
			// 删除原来的权限
			AdminRoles ar_condition = new AdminRoles();
			ar_condition.setAid(bean.getId());
			adminRolesService.delete(ar_condition);
		}
		// 添加角色
		if (roleIds != null && roleIds.length > 0) {
			for (Integer roleId : roleIds) {
				AdminRoles role = new AdminRoles();
				role.setAid(bean.getId());
				role.setRid(roleId);
				adminRolesService.insertSelective(role);
				addSysLog(SysLogConstant.MODULE_ADMINS, SysLogConstant.MODULE_DEFAULT, JSONObject.toJSONString(role));
			}
		}
		return "success";
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public String del(java.lang.Integer id) {
		Admin bean = adminService.selectByPrimaryKey(id);
		if (bean != null) {
			addSysLog(SysLogConstant.MODULE_ADMINS, SysLogConstant.OPRATE_DEL, JSONObject.toJSONString(bean));
			adminService.deleteByPrimaryKey(id);
			// 删除原来的权限
			AdminRoles ar_condition = new AdminRoles();
			ar_condition.setAid(id);
			adminRolesService.delete(ar_condition);
		}
		return "success";
	}
}