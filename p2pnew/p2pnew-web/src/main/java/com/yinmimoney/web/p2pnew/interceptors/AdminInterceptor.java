package com.yinmimoney.web.p2pnew.interceptors;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.AdminActions;
import com.yinmimoney.web.p2pnew.pojo.AdminRoleActions;
import com.yinmimoney.web.p2pnew.pojo.AdminRoles;
import com.yinmimoney.web.p2pnew.service.IAdminActions;
import com.yinmimoney.web.p2pnew.service.IAdminRoleActions;
import com.yinmimoney.web.p2pnew.service.IAdminRoles;

import cc.s2m.util.CookieUtil;
import cc.s2m.web.utils.webUtils.StaticProp;
import cc.s2m.web.utils.webUtils.util.AccountDigestUtils;
import cc.s2m.web.utils.webUtils.vo.Expressions;

/**
 * @ClassName: AdminInterceptor
 * @Description: 管理员权限过滤判断
 * @author 龚国君
 * @date 2015年2月8日 下午3:02:25
 *
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

	private List<String> ignore_urls;// 忽略权限判断的URI链接数组

	@Autowired
	private IAdminActions adminActionsService;
	@Autowired
	private IAdminRoleActions adminRoleActionsService;
	@Autowired
	private IAdminRoles adminRolesService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String adminLoginUrl = "/admin/login";
		StringBuilder redirectURL = new StringBuilder();
		redirectURL.append(request.getRequestURI());
		if (!Strings.isNullOrEmpty(request.getQueryString())) {
			redirectURL.append("?");
			redirectURL.append(request.getQueryString());
		}
		adminLoginUrl += "?redirectURL=" + java.net.URLEncoder.encode(redirectURL.toString(), "UTF-8");

		CookieUtil cookie = new CookieUtil(request, response);
		String cookieAdminStr = cookie.getCookie(StaticProp.cookieID);
		if (cookieAdminStr == null || cookieAdminStr.trim().isEmpty()) {
			response.sendRedirect(adminLoginUrl);
			return false;
		}
		Admin admin = AccountDigestUtils.unserialize(Admin.class, cookieAdminStr, StaticProp.sysConfig.get("cookie.secret.key"));
		if (admin == null) {
			response.sendRedirect(adminLoginUrl);
			return false;
		}
		// 判断是否过期
		String sessionTimeoutStr = StaticProp.sysConfig.get("session.time.out");
		int sessionTimeout = Integer.parseInt(sessionTimeoutStr);
		Date lastVisitDate = admin.getLoginTime();
		if (lastVisitDate == null) {
			response.sendRedirect(adminLoginUrl);
			return false;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(lastVisitDate);
		c.add(Calendar.MINUTE, sessionTimeout);
		if (c.getTime().before(new Date())) {
			response.sendRedirect(adminLoginUrl);
			return false;
		}
		// 重写cookie
		admin.setLoginTime(new Date());
		cookieAdminStr = AccountDigestUtils.serialize(admin, StaticProp.sysConfig.get("cookie.secret.key"), null);
		cookie.setCookie(StaticProp.cookieID, cookieAdminStr, -1, null);
		// 去掉末尾的‘/’符号
		String uri = request.getRequestURI();
		if (!Strings.isNullOrEmpty(uri) && uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}
		// 无条件放行的页面，直接返回
		if (ignore_urls != null && ignore_urls.contains(uri)) {
			request.setAttribute("sessionAdmin", admin);// 放入request
			putOwnerActions(request, admin);
			return true;
		}
		AdminActions actions = new AdminActions();
		actions.setUrl(uri);
		actions = adminActionsService.getByCondition(actions);
		if (actions == null) {
			actions = new AdminActions();
			actions.setUrl(uri);
			actions.setName("未分配");
			actions.setLevel(1);
			adminActionsService.insertSelective(actions);
		}
		// 超级管理员全部放行
		if (admin.getUserName().equals("admin")) {
			request.setAttribute("sessionAdmin", admin);// 放入request
			putOwnerActions(request, admin);
			return true;
		}
		// 进行权限判断
		AdminRoleActions roleActions = new AdminRoleActions();
		roleActions.setAid(actions.getId());
		List<AdminRoleActions> roleActionsList = adminRoleActionsService.getList(roleActions);
		if (roleActionsList.isEmpty()) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain; charset=UTF-8");
			response.getWriter().println("您无权进行该操作！");
			return false;
		}
		boolean pass = false;// 是否放行
		for (AdminRoleActions roleActions_ : roleActionsList) {
			AdminRoles adminRole = new AdminRoles();
			adminRole.setAid(admin.getId());
			adminRole.setRid(roleActions_.getRid());
			adminRole = adminRolesService.getByCondition(adminRole);
			if (adminRole != null) {
				pass = true;
				break;
			}
		}
		if (!pass) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain; charset=UTF-8");
			response.getWriter().println("您无权进行该操作！");
			return false;
		}
		request.setAttribute("sessionAdmin", admin);// 放入request
		putOwnerActions(request, admin);
		return true;
	}

	private void putOwnerActions(HttpServletRequest request, Admin admin) {
		AdminActions adminActions = new AdminActions();
		adminActions.setIsMenu(true);
		adminActions.setOrderBy("paixu ASC");
		if (admin.getUserName().equals("admin")) {
			request.setAttribute("ownerAdminActions", adminActionsService.getList(adminActions));
			return;
		}
		AdminRoles adminRoles = new AdminRoles();
		adminRoles.setAid(admin.getId());
		List<AdminRoles> adminRolesS = adminRolesService.getList(adminRoles);
		List<Integer> roles = Lists.newArrayList(-1);
		for (AdminRoles ar : adminRolesS) {
			roles.add(ar.getRid());
		}
		AdminRoleActions adminRoleActions = new AdminRoleActions();
		adminRoleActions.and(Expressions.in("rid", roles));
		List<AdminRoleActions> adminRoleActionsS = adminRoleActionsService.getList(adminRoleActions);
		List<Integer> actions = Lists.newArrayList(-1);
		for (AdminRoleActions roleActions : adminRoleActionsS) {
			actions.add(roleActions.getAid());
		}
		adminActions.and(Expressions.in("id", actions));
		request.setAttribute("ownerAdminActions", adminActionsService.getList(adminActions));
	}

	public List<String> getIgnore_urls() {
		return ignore_urls;
	}

	public void setIgnore_urls(List<String> ignore_urls) {
		this.ignore_urls = ignore_urls;
	}
}
