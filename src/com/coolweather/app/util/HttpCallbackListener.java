package com.coolweather.app.util;

public interface HttpCallbackListener {
	/*
	 * ���������ɹ���Ӧ���������ʱ�����
	 */
	void onFinish(String response);

	/*
	 * ����������������ִ����ʱ�����
	 */
	void onError(Exception e);
}
