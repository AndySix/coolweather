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
 * �����������������
 * 
 * @author zl
 *
 */
public class HttpUtil {
	/*
	 * sendHttpRequest()�����п�����һ���߳�������HTTP������ô��������Ӧ���������޷����з��صģ����еĺ�ʱ�߼����������߳�����еģ�
	 * sendHttpRequest()�������ڷ����������ü���Ӧ��ʱ���ִ�н����ˣ���ȻҲ���޷�������Ӧ������
	 */
	public static void sendHttpRequest(final String address,
			final HttpCallbackListener listener) {
		// ��Ϊ������������ʱ��ʱ������������Ҫ�������߳�
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
