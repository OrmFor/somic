package com.yinmimoney.web.p2pnew.controller.base;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.yinmimoney.web.p2pnew.enums.EnumRedisKeys;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.AdminActions;
import com.yinmimoney.web.p2pnew.pojo.SysLog;
import com.yinmimoney.web.p2pnew.service.IAdminActions;
import com.yinmimoney.web.p2pnew.service.ISysLog;
import com.yinmimoney.web.p2pnew.util.RedisStringManager;

import cc.s2m.util.HtmlParsePlaintText;
import cc.s2m.util.IpUtil;
import cc.s2m.web.utils.webUtils.StaticProp;

public class BaseController {

	private static final Logger LOGGER = LogManager.getLogger(BaseController.class);

	@Autowired
	protected RedisStringManager redisStringManager;
	@Autowired
	private IAdminActions adminActionsService;
	@Autowired
	private ISysLog sysLogService;

	public boolean checkCodeIsEqual(HttpServletRequest request, String picCode) {
		if (picCode == null || picCode.trim().isEmpty()) {
			return false;
		}
		String sessionId = request.getSession(true).getId();
		String code_m = (String) redisStringManager.find(EnumRedisKeys.PIC_CODE.getKey() + sessionId);
		redisStringManager.delete(EnumRedisKeys.PIC_CODE.getKey() + sessionId);
		if (code_m == null || code_m.trim().isEmpty()) {
			return false;
		}
		if (!picCode.equalsIgnoreCase(code_m)) {
			return false;
		}
		return true;
	}

	public Map<String, String> upload(MultipartFile upfile, boolean isLocal) {
		Map<String, String> resultJson = new HashMap<String, String>();
		if (upfile == null || upfile.getSize() < 1) {
			resultJson.put("msg", "未包含文件上传域");
			return resultJson;
		}
		String fileName = upfile.getOriginalFilename();
		Iterator<String> type = Arrays.asList(StaticProp.allowFiles).iterator();
		boolean allowFilesFlag = false;
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				allowFilesFlag = true;
			}
		}
		if (!allowFilesFlag) {
			resultJson.put("msg", "不允许的文件格式");
			return resultJson;
		}
		String url = StaticProp.upYunPath + DigestUtils.md5Hex(UUID.randomUUID().toString());
		url += fileName.substring(fileName.lastIndexOf("."));
		if (isLocal) {
			try {
				url = "/upload/" + url;
				LOGGER.info("本地url：" + url);
				File file = new File(StaticProp.SERVLET_CONTEXT.getRealPath(url));
				Files.createParentDirs(file);
				Files.write(upfile.getBytes(), file);
			} catch (Exception e) {
				e.printStackTrace();
				resultJson.put("msg", e.getMessage());
				return resultJson;
			}
		} else {
			try {
				LOGGER.info("又拍云url：" + url);
				boolean a = StaticProp.UP_YUN.writeFile(url, upfile.getBytes(), true);
				if (!a) {
					resultJson.put("msg", "上传到upyun失败");
					return resultJson;
				}
			} catch (Exception e) {
				e.printStackTrace();
				resultJson.put("msg", e.getMessage());
				return resultJson;
			}
		}

		resultJson.put("msg", "SUCCESS");
		resultJson.put("size", String.valueOf(upfile.getSize()));
		resultJson.put("originalName", fileName);
		resultJson.put("name", url);
		if (isLocal) {
			resultJson.put("url", "/" + url);
		} else {
			resultJson.put("url", StaticProp.sysConfig.get("upyun.domain") + "/" + url);
		}
		resultJson.put("type", fileName.substring(fileName.lastIndexOf(".")));
		return resultJson;
	}

	public Map<String, String> uploadFromUrl(String remoteUrl, String ext) {
		Map<String, String> resultJson = new HashMap<String, String>();
		if (Strings.isNullOrEmpty(remoteUrl)) {
			resultJson.put("msg", "远程地址不能为空");
			return resultJson;
		}
		List<String> types = Arrays.asList(StaticProp.allowFiles);
		if (!types.contains(ext)) {
			resultJson.put("msg", "不允许的文件格式");
			return resultJson;
		}
		String url = StaticProp.upYunPath + DigestUtils.md5Hex(UUID.randomUUID().toString());
		url += ext;
		byte[] bytes = null;
		List<Byte> byteList = Lists.newArrayList();
		try {
			InputStream inputStream = new URL(remoteUrl).openStream();
			int b;
			while ((b = inputStream.read()) != -1) {
				byteList.add((byte) b);
			}
			bytes = new byte[byteList.size()];
			for (int i = 0; i < byteList.size(); i++) {
				bytes[i] = byteList.get(i);
			}
			boolean a = StaticProp.UP_YUN.writeFile(url, bytes, true);
			if (!a) {
				resultJson.put("msg", "上传到upyun失败");
				return resultJson;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("msg", e.getMessage());
			return resultJson;
		}
		resultJson.put("msg", "SUCCESS");
		resultJson.put("size", String.valueOf(bytes.length));
		resultJson.put("originalName", remoteUrl);
		resultJson.put("name", url);
		resultJson.put("url", "http://" + StaticProp.sysConfig.get("upyun.domain") + "/" + url);
		resultJson.put("type", ext);
		return resultJson;
	}

	public String changeTextAreaToHtml(String textArea) {
		return HtmlParsePlaintText.changeTextAreaToHtml(textArea);
	}

	public Admin getSessionAdmin() {
		return (Admin) getRequest().getAttribute("sessionAdmin");
	}

	public String getIp() {
		return IpUtil.getIp(getRequest());
	}

	@InitBinder
	public void setDisallowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("pageNumber", "pageSize", "pageBeginIndex", "expressionChainList", "orderBy");
	}

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	public void addSysLog(final String module, final String oprate, final String msg) {
		String uri = getRequest().getRequestURI().trim();
		uri = uri.trim();
		if (uri.endsWith("/") && uri.length() > 1) {
			uri = uri.substring(0, uri.length() - 1);
		}
		final String uri_ = uri;
		AdminActions condition = new AdminActions();
		condition.setUrl(uri_);
		AdminActions adminActions = adminActionsService.getByCondition(condition);
		if (adminActions == null) {
			adminActions = new AdminActions();
			adminActions.setName("-");
		}
		final AdminActions adminActions_ = adminActions;
		final Admin admin = getSessionAdmin();
		final String ip = getIp();
		StaticProp.execute.execute(new Runnable() {
			public void run() {

				SysLog sysLog = new SysLog();
				sysLog.setAdminId(admin.getId());
				sysLog.setIp(ip);
				sysLog.setModuleType(module);
				sysLog.setOprateType(oprate);
				sysLog.setMsg(msg);
				sysLog.setName(adminActions_.getName());
				sysLog.setUri(uri_);
				sysLogService.insertSelective(sysLog);
			}
		});
	}

}
