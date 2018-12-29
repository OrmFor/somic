package com.yinmimoney.web.p2pnew.controller.admin;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.constant.SysLogConstant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.enums.EnumAdminStatus;
import com.yinmimoney.web.p2pnew.enums.EnumUserCodeType;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.SysLog;
import com.yinmimoney.web.p2pnew.service.IAdmin;
import com.yinmimoney.web.p2pnew.service.ISysLog;
import com.yinmimoney.web.p2pnew.util.UserCodeGenerator;

import cc.s2m.util.CookieUtil;
import cc.s2m.util.IpUtil;
import cc.s2m.web.utils.webUtils.StaticProp;
import cc.s2m.web.utils.webUtils.util.AccountDigestUtils;

@Controller("admin_login")
@RequestMapping("/admin")
public class LoginController extends BaseController {

	@Autowired
	private IAdmin adminService;
	@Autowired
	private ISysLog sysLogService;
	@Autowired
	private UserCodeGenerator userCodeGenerator;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String redirectURL) {
		if (!Strings.isNullOrEmpty(redirectURL)) {
			redirectURL = redirectURL.replace("&amp;", "&");
		}
		model.addAttribute("redirectURL", redirectURL);
		return "admin/login";
	}

	@RequestMapping(value = "/login/check", method = RequestMethod.POST)
	@ResponseBody
	public String check(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password, @RequestParam String picCode) {
		// 表没有记录插入默认记录
		Admin admin = adminService.getByCondition(null);
		if (admin == null) {
			admin = new Admin();
			admin.setId(1);
			admin.setUserCode(userCodeGenerator.getUserCode(EnumUserCodeType.TYPE_ADMIN.getType()));
			admin.setUserName("admin");
			admin.setRealName("超级管理员");
			admin.setMobilePhone("13500000000");
			admin.setPwd(AccountDigestUtils.getMd5Pwd(admin.getUserName(), "admin"));
			adminService.insertSelective(admin);
		}
		if (!checkCodeIsEqual(request, picCode)) {
			return "errorPicCode";
		}
		Admin condition = new Admin();
		condition.setUserName(username);
		admin = adminService.getByCondition(condition);
		if (admin == null) {
			return "noRecord";
		}
		if (!admin.getPwd().equals(AccountDigestUtils.getMd5Pwd(admin.getUserName(), password))) {
			Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,16}$");
			Matcher isNum = pattern.matcher(password);
			if (!isNum.matches()) {
				return "samplePassword";
			} else {
				return "errorPassword";
			}
		}
		if (admin.getStatus().intValue() != EnumAdminStatus.STATUS_NORMAL.getStatus()) {
			if (admin.getStatus().intValue() != EnumAdminStatus.STATUS_LOCK.getStatus())
				return "isLock";
			else if (admin.getStatus().intValue() != EnumAdminStatus.STATUS_LEAVE.getStatus())
				return "isLeave";
			else
				return "accountError";
		}
		admin.setLoginTime(new Date());
		admin.setLoginIp(IpUtil.getIp(request));
		adminService.updateByPrimaryKey(admin);

		String cookieAdminStr = AccountDigestUtils.serialize(admin, StaticProp.sysConfig.get("cookie.secret.key"), null);
		CookieUtil cookie = new CookieUtil(request, response);
		cookie.setCookie(StaticProp.cookieID, cookieAdminStr, -1, null);

		SysLog sysLog = new SysLog();
		sysLog.setAdminId(admin.getId());
		sysLog.setIp(getIp());
		sysLog.setModuleType("登录");
		sysLog.setOprateType(SysLogConstant.OPRATE_LOGIN);
		sysLog.setMsg(admin.getUserName());
		sysLog.setName("登录");
		String uri = getRequest().getRequestURI().trim();
		uri = uri.trim();
		if (uri.endsWith("/") && uri.length() > 1) {
			uri = uri.substring(0, uri.length() - 1);
		}
		sysLog.setUri(uri);
		sysLogService.insertSelective(sysLog);
		return "success";
	}

	@RequestMapping(value = "/loginout", method = RequestMethod.GET)
	public String out(HttpServletRequest request, HttpServletResponse response) {
		Admin admin = getSessionAdmin();
		addSysLog("退出", SysLogConstant.OPRATE_LOGIN_OUT, admin.getUserName());
		CookieUtil cookie = new CookieUtil(request, response);
		cookie.removeCookie(StaticProp.cookieID, null);
		return "redirect:/admin/login";
	}
}
