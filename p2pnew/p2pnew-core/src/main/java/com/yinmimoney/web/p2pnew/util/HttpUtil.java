package com.yinmimoney.web.p2pnew.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

/**
 * 工具类-Http请求
 */
public class HttpUtil {

	public static String getHttpResponse(String pageURL) {
		String pageContent = "";
		BufferedReader in = null;
		InputStreamReader isr = null;
		InputStream is = null;
		HttpURLConnection huc = null;
		try {
			URL url = new URL(pageURL);
			huc = (HttpURLConnection) url.openConnection();
			is = huc.getInputStream();
			isr = new InputStreamReader(is);
			in = new BufferedReader(isr);
			String line = null;
			while (((line = in.readLine()) != null)) {
				if (line.length() == 0)
					continue;
				pageContent += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (isr != null)
					isr.close();
				if (in != null)
					in.close();
				if (huc != null)
					huc.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pageContent;
	}

	public static <T> T getJson(String url, Class<T> clazz) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		httpGet.addHeader("Cookie", "");

		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httpGet);

			if (response == null) {
				return null;
			}
			String result = EntityUtils.toString(response.getEntity());
			return JSON.parseObject(result.toString().trim(), clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String postHttpResponse(String pageURL, String parameter) {
		String xmlreturn = "time_out";
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) (new URL(pageURL)).openConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "time_out";
		}
		if (connection != null) {
			try {
				connection.setRequestMethod("POST"); // POST为大写的方式才可以
				connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
				System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 设置超时时间
				System.setProperty("sun.net.client.defaultReadTimeout", "30000");
				connection.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(parameter);
				writer.flush();
				writer.close();
				BufferedReader read;
				read = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String str;
				String tmp = "";
				while ((str = read.readLine()) != null) {
					tmp = tmp + str;
				}
				xmlreturn = tmp;
				read.close();// 关闭读取流
			} catch (Exception ex) {
				ex.printStackTrace();
				return "time_out";
			}
		} else {
			return "time_out";
		}
		connection.disconnect();// 断开连接
		return xmlreturn;
	}

}
