package com.yinmimoney.web.p2pnew.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;
import com.yinmimoney.web.p2pnew.constant.NoticeConstant;

import cc.s2m.util.HttpUtil;
import cc.s2m.web.utils.webUtils.StaticProp;

/**
 * 短信相关统一公用类
 */
public class SmsHandelUtils {
	private static final Logger LOGGER = LogManager.getLogger(SmsHandelUtils.class);

	private static final String SMS_URL = StaticProp.sysConfig.get("ZhuTong.url");
	private static final String SMS_USERNAME = StaticProp.sysConfig.get("ZhuTong.username");
	private static final String SMS_PWD = StaticProp.sysConfig.get("ZhuTong.pwd");
	private static final String SMS_PID = StaticProp.sysConfig.get("ZhuTong.productid");
	private static final String ZJYSMS_URL = StaticProp.sysConfig.get("ZhongJuYuan.url");
	private static final String ZJYSMS_USERNAME = StaticProp.sysConfig.get("ZhongJuYuan.username");
	private static final String ZJYSMS_PWD = StaticProp.sysConfig.get("ZhongJuYuan.pwd");
	private static final String ZJYSMS_YX_USERNAME = StaticProp.sysConfig.get("ZhongJuYuan.yx.username");
	private static final String ZJYSMS_YX_PWD = StaticProp.sysConfig.get("ZhongJuYuan.yx.pwd");
	// 以下梦网接口账户
	private static final String MWSMS_URL = StaticProp.sysConfig.get("MengWang.url");
	private static final String MWSMS_USERID = StaticProp.sysConfig.get("MengWang.userId");
	private static final String MWSMS_PASSWORD = StaticProp.sysConfig.get("MengWang.password");
	private static final String MWSMS_MSGID = StaticProp.sysConfig.get("MengWang.MsgId");
	private static final String MWSMS_ENVIRONMENT = StaticProp.sysConfig.get("MengWang.environment");
	// 以下梦网营销账户
	private static final String MWSMS_YX_URL = StaticProp.sysConfig.get("MengWang.yx.url");
	private static final String MWSMS_YX_USERID = StaticProp.sysConfig.get("MengWang.yx.userId");
	private static final String MWSMS_YX_PASSWORD = StaticProp.sysConfig.get("MengWang.yx.password");
	private static final String MWSMS_YX_MSGID = StaticProp.sysConfig.get("MengWang.yx.MsgId");
	private static final String MWSMS_YX_ENVIRONMENT = StaticProp.sysConfig.get("MengWang.yx.environment");

	private SmsHandelUtils() {
	}

	public static void sendSms(final String phone, final String content, final String channelType) {
		StaticProp.execute.submit(new Runnable() {
			public void run() {
				sendSmsByChannelType(phone, content, channelType, 0);
			}
		});
	}

	public static Boolean sendSmsByChannelType(final String phone, final String content, final String channelType, final int type) {
		if ("test".equals(StaticProp.sysConfig.get("sms.send.environment"))) {
			return true;
		}
		try {
			Map<String, Object> paramMap = Maps.newHashMap();
			if (NoticeConstant.SMS_ZJY.equals(channelType)) {
				paramMap.put("name", ZJYSMS_YX_USERNAME);
				paramMap.put("pwd", ZJYSMS_YX_PWD);
				paramMap.put("dst", phone);
				paramMap.put("msg", content + "退订回T");
				paramMap.put("time", "");
				paramMap.put("sender", "");
				paramMap.put("txt", "");
				LOGGER.debug(paramMap);
				String s = postMethodUrlForZjy(ZJYSMS_URL, paramMap);
				LOGGER.debug(new String(s.getBytes("iso8859_1"), "gb2312"));
				if (s.contains("num=1")) {
					return true;
				}
			} else if (NoticeConstant.SMS_ZT.equals(channelType)) {
				paramMap.put("username", SMS_USERNAME);
				paramMap.put("password", SMS_PWD);
				if (type == 0) {
					paramMap.put("productid", "727727");
				} else {
					paramMap.put("productid", "411623");
				}
				paramMap.put("mobile", phone);
				paramMap.put("content", content);
				paramMap.put("dstime", "");
				paramMap.put("xh", 0);
				LOGGER.debug(paramMap);
				String s = HttpUtil.postMethodUrl(SMS_URL, null, paramMap, null);
				LOGGER.debug(s);
				if (s.contains("1,")) {// 发送成功
					return true;
				}
			} else if (NoticeConstant.SMS_MW.equals(channelType)) {
				paramMap.put("userId", MWSMS_YX_USERID);
				paramMap.put("password", MWSMS_YX_PASSWORD);
				paramMap.put("pszMobis", phone);
				if ("test".equals(MWSMS_YX_ENVIRONMENT)) {
					paramMap.put("pszMsg", "同事您好，感谢您对此次测试的配合。");
				} else {
					paramMap.put("pszMsg", content);
				}
				paramMap.put("iMobiCount", 1);
				paramMap.put("pszSubPort", "*");
				paramMap.put("MsgId", MWSMS_YX_MSGID);
				LOGGER.info(paramMap);
				String s = HttpUtil.postMethodUrl(MWSMS_YX_URL, null, paramMap, null);
				LOGGER.info(s);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
			return false;
		}
		return false;
	}

	public static void sendCodeSms(final String phone, final String content, final String channelType, final String code) {
		if ("test".equals(StaticProp.sysConfig.get("sms.send.environment"))) {
			return;
		}
		StaticProp.execute.submit(new Runnable() {
			public void run() {
				try {
					Map<String, Object> paramMap = Maps.newHashMap();

					if (NoticeConstant.SMS_ZJY.equals(channelType)) {
						paramMap.put("name", ZJYSMS_USERNAME);
						paramMap.put("pwd", ZJYSMS_PWD);
						paramMap.put("dst", phone);
						paramMap.put("msg", "验证码为" + code);
						paramMap.put("time", "");
						paramMap.put("sender", "");
						paramMap.put("txt", "");
						LOGGER.debug(paramMap);
						String s = postMethodUrlForZjy(ZJYSMS_URL, paramMap);
						LOGGER.debug(new String(s.getBytes("iso8859_1"), "gb2312"));
					} else if (NoticeConstant.SMS_ZT.equals(channelType)) {
						paramMap.put("username", SMS_USERNAME);
						paramMap.put("password", SMS_PWD);
						paramMap.put("productid", "712712");
						paramMap.put("mobile", phone);
						paramMap.put("content", content);
						paramMap.put("dstime", "");
						paramMap.put("xh", 0);
						LOGGER.debug(paramMap);
						String s = HttpUtil.postMethodUrl(SMS_URL, null, paramMap, null);
						LOGGER.debug(s);
					} else if (NoticeConstant.SMS_MW.equals(channelType)) {
						paramMap.put("userId", MWSMS_USERID);
						paramMap.put("password", MWSMS_PASSWORD);
						paramMap.put("pszMobis", phone);
						if ("test".equals(MWSMS_ENVIRONMENT)) {
							paramMap.put("pszMsg", "同事您好，感谢您对此次测试的配合。");
						} else {
							paramMap.put("pszMsg", content);
						}
						paramMap.put("iMobiCount", 1);
						paramMap.put("pszSubPort", "*");
						paramMap.put("MsgId", MWSMS_MSGID);
						LOGGER.info(paramMap);
						String s = HttpUtil.postMethodUrl(MWSMS_URL, null, paramMap, null);
						LOGGER.info(s);
					}
				} catch (Exception e) {
					LOGGER.error("", e);
				}
			}
		});
	}

	public static String postMethodUrlForZjy(String url, Map<String, Object> paramMap) {
		try {
			CloseableHttpResponse execute;
			CloseableHttpClient client;

			HttpPost post = new HttpPost(url);
			if (paramMap != null && paramMap.size() > 0) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (String key : paramMap.keySet()) {
					Object value = paramMap.get(key);
					if (value != null) {
						nvps.add(new BasicNameValuePair(key, String.valueOf(value)));
					}
				}
				post.setEntity(new UrlEncodedFormEntity(nvps, "gbk"));
			}

			client = HttpClients.custom().setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0")
					.setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).setConnectionRequestTimeout(60000).build()).build();

			execute = client.execute(post);
			String content = EntityUtils.toString(execute.getEntity());
			LOGGER.debug(url);
			Charset charset = ContentType.getOrDefault(execute.getEntity()).getCharset();
			LOGGER.debug(charset);
			LOGGER.debug(execute.getStatusLine().getStatusCode());
			LOGGER.debug(content);
			execute.close();
			client.close();
			return content;
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		}
	}
}
