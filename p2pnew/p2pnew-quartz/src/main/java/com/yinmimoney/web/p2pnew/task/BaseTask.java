package com.yinmimoney.web.p2pnew.task;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yinmimoney.web.p2pnew.pojo.SysTaskHandel;
import com.yinmimoney.web.p2pnew.pojo.SysTaskLog;
import com.yinmimoney.web.p2pnew.service.ISysTaskHandel;
import com.yinmimoney.web.p2pnew.service.ISysTaskLog;

public abstract class BaseTask {

	private static Logger log = LogManager.getLogger(BaseTask.class);

	@Autowired
	private ISysTaskHandel sysTaskHandel;
	@Autowired
	private ISysTaskLog sysTaskLog;

	public void run() {
		SysTaskHandel condition = new SysTaskHandel();
		condition.setModuleName(getModule());
		condition.setHostname(getLocalHostName());
		condition.setIsEnabled(true);
		List<SysTaskHandel> list = sysTaskHandel.getList(condition);
		if (list == null || list.size() < 1) {
			return;
		}
		SysTaskLog bean = new SysTaskLog();
		bean.setModuleName(getModule());
		bean.setHostname(getLocalHostName());
		bean.setIpAddress(getLocalHostIps());
		try {
			modifyThreadName();
			doTask();
		} catch (Exception e) {
			bean.setIsSuccess(false);
			sysTaskLog.insertSelective(bean);
			log.error("自动任务执行出错：", e);
		}
		bean.setIsSuccess(true);
		sysTaskLog.insertSelective(bean);
	}

	@PostConstruct
	public void rsgisterTask() throws Exception {
		SysTaskHandel condition = new SysTaskHandel();
		condition.setModuleName(getModule());
		condition.setHostname(getLocalHostName());
		List<SysTaskHandel> list = sysTaskHandel.getList(condition);
		if (list == null || list.size() < 1) {
			// 插入数据库
			SysTaskHandel bean = new SysTaskHandel();
			bean.setHostname(getLocalHostName());
			bean.setModuleName(getModule());
			bean.setIpAddress(getLocalHostIps());
			bean.setIsEnabled(false);

			sysTaskHandel.insertSelective(bean);
		}
	}

	// 获取类名
	public String getModule() {
		return this.getClass().getSimpleName();
	}

	public String getLocalHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			throw new RuntimeException("Unknown Host", e);
		}
	}

	public String getLocalHostIps() {
		List<String> ips = new ArrayList<String>();
		Enumeration<NetworkInterface> allNetInterfaces = null;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress inetAddress = addresses.nextElement();
				if (inetAddress != null && inetAddress instanceof Inet4Address) {
					String ip = inetAddress.getHostAddress();
					if (ip != null && !ip.equals("127.0.0.1")) {
						ips.add(ip);
					}
				}
			}
		}
		if (ips.isEmpty()) {
			return null;
		}
		StringBuffer strBuffer = new StringBuffer();
		for (String str : ips) {
			strBuffer.append("," + str);
		}
		return strBuffer.toString().substring(1);
	}

	abstract protected void doTask();
	abstract protected String modifyThreadName();
}
