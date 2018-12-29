package com.yinmimoney.web.p2pnew.interceptors;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.enums.EnumApiTokenStatus;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.pojo.SConfig;
import com.yinmimoney.web.p2pnew.service.IApiToken;
import com.yinmimoney.web.p2pnew.service.ISConfig;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.util.ResultCode;
import com.yinmimoney.web.p2pnew.util.StatusCode;

public class TokenInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LogManager.getLogger(TokenInterceptor.class);

	@Autowired
	private ISConfig sConfigService;
	@Autowired
	private IApiToken apiTokenService;
	private List<String> canNoLogin_urls;// 可以不需要登录的链接，但是登录情况下又需要过token，保存用户信息

	public List<String> getCanNoLogin_urls() {
		return canNoLogin_urls;
	}

	public void setCanNoLogin_urls(List<String> canNoLogin_urls) {
		this.canNoLogin_urls = canNoLogin_urls;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("token");
		logger.info("token:" + token);
		String uri = request.getRequestURI();
		if (!Strings.isNullOrEmpty(uri) && uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}
		logger.info("uri:" + uri);
		if (canNoLogin_urls != null && canNoLogin_urls.contains(uri) && Strings.isNullOrEmpty(token)) {
			return true;
		}
		if (Strings.isNullOrEmpty(token)) {
			writeJson(response, new ResultCode(StatusCode.REQUIRE_TOKEN, "REQUIRE_TOKEN"));
			return false;
		}
		String jsonStr = new String(Base64.decodeBase64(token), "UTF-8");
		JSONObject json = JSONObject.parseObject(jsonStr);
		if (json == null || json.isEmpty()) {
			writeJson(response, new ResultCode(StatusCode.REQUIRE_TOKEN, "REQUIRE_TOKEN"));
			return false;
		}
		// 校验用户
		String deviceId = json.getString("device_id");
		String t = json.getString("token");
		if (Strings.isNullOrEmpty(deviceId) || Strings.isNullOrEmpty(t)) {
			writeJson(response, new ResultCode(StatusCode.REQUIRE_TOKEN, "REQUIRE_TOKEN"));
			return false;
		}
		ApiToken apiToken = new ApiToken();
		apiToken.setDeviceId(deviceId);
		apiToken.setToken(t);
		apiToken = apiTokenService.getByCondition(apiToken);
		if (apiToken == null) {
			writeJson(response, new ResultCode(StatusCode.ERROR_USER_OTHER_DEVICE_IS_LOGIN, "ERROR_USER_OTHER_DEVICE_IS_LOGIN"));
			return false;
		}
		if (apiToken.getStatus().intValue() == EnumApiTokenStatus.STATUS_EXPIRED.getStatus()) {// 过期
			writeJson(response, new ResultCode(StatusCode.ERROR_USER_TOKEN_EXPIRED, "ERROR_USER_TOKEN_EXPIRED"));
			return false;
		} else if (apiToken.getStatus().intValue() == EnumApiTokenStatus.STATUS_DISABLED.getStatus()) {// 失效
			writeJson(response, new ResultCode(StatusCode.ERROR_USER_TOKEN_DISABLED, "ERROR_USER_TOKEN_DISABLED"));
			return false;
		} else if (apiToken.getStatus().intValue() == EnumApiTokenStatus.STATUS_OTHER_DEVICE_LOGIN.getStatus()) {// 另外设备登录
			writeJson(response, new ResultCode(StatusCode.ERROR_USER_OTHER_DEVICE_IS_LOGIN, "ERROR_USER_OTHER_DEVICE_IS_LOGIN"));
			return false;
		}
		SConfig sConfig = sConfigService.getByNid("APP_EXPIRED_TIMES");
		if (sConfig != null) {
			Long appExpiredTimes = Long.parseLong(sConfig.getValue()) * 1000;
			if (DateUtil.getNow().getTime() - apiToken.getDateUpdate().getTime() >= appExpiredTimes) {// token过期
				// 更新token状态为过期
				apiTokenService.updateApiTokenStatus(apiToken, EnumApiTokenStatus.STATUS_EXPIRED);
				writeJson(response, new ResultCode(StatusCode.ERROR_USER_TOKEN_EXPIRED, "ERROR_USER_TOKEN_EXPIRED"));
				return false;
			}
		}
		// 把用户数据信息保存进去
		request.setAttribute("apiUser", apiToken);
		return true;
	}

	private void writeJson(HttpServletResponse response, ResultCode resultCode) {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(JSONObject.toJSONString(resultCode));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
}
