package com.yanghe.sujiu.network;

/**
 * 监听器接口
 * 
 * @author linjin
 * 
 */
public interface HttpListener {

	void onProgress(int progress);

	void onResponse(int msgId, String result);

	void onError(int msgId, String result);

	void onCanel(int msgId);

}
