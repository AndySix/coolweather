package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 与服务器交互工具类
 * 
 * @author zl
 *
 */
public class HttpUtil {
	/*
	 * sendHttpRequest()方法中开启了一个线程来发起HTTP请求，那么服务器响应的数据是无法进行返回的，所有的耗时逻辑都是在子线程里进行的，
	 * sendHttpRequest()方法会在服务器还来得及响应的时候就执行结束了，当然也就无法返回响应的数据
	 */
	public static void sendHttpRequest(final String address,
			final HttpCallbackListener listener) {
		// 因为发送网络请求时耗时操作，所以需要开启新线程
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}

					if (listener != null) {
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
