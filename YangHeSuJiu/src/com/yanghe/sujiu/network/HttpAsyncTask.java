package com.yanghe.sujiu.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.net.Proxy;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;

import com.yanghe.sujiu.MainApp;
import com.yanghe.sujiu.R;
import com.yanghe.sujiu.model.MyLog;
import com.yanghe.sujiu.utils.HttpUtils;

public class HttpAsyncTask extends AsyncTask<Message, Integer, String> {
	/**
	 * 监听器对象
	 */
	private HttpListener IListener;
	/**
	 * 请求消息标识
	 */
	private int msg;
	/**
	 * Http Client对象
	 */
	private DefaultHttpClient httpClient;

	private static final String TAG = "HttpAsyncTask";

	/**
	 * 构造函数
	 * 
	 * @param IListener
	 *            http请求监听器
	 * @param msg
	 *            消息id
	 * @param needSetProxy
	 *            是否需要设置代理网关
	 */
	@SuppressWarnings("deprecation")
	public HttpAsyncTask(HttpListener IListener, int msg, boolean needSetProxy) {
		this.IListener = IListener;
		this.msg = msg;

		// 设置联网超时
		int timeout = MainApp.mainApp.getResources().getInteger(
				R.integer.time_out);
		HttpParams myParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(myParams, timeout);
		HttpConnectionParams.setSoTimeout(myParams, timeout);
		// 每次与服务器交互需要判断当前网络状态，是否需要设置网关
		if (needSetProxy) {
			String host = Proxy.getDefaultHost();
			int port = Proxy.getDefaultPort();
			HttpHost httpHost = new HttpHost(host, port);
			myParams.setParameter(ConnRouteParams.DEFAULT_PROXY, httpHost);
		}
		httpClient = new DefaultHttpClient(myParams);
		MyLog.d(TAG, "请求初始化完成");
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		MyLog.d(TAG, "请求进度=" + values[0]);
		this.IListener.onProgress(values[0]);
		super.onProgressUpdate(values);
	}

	@Override
	protected String doInBackground(Message... params) {
		publishProgress(10);
		Message param = params[0];
		HttpResponse response = null;
		String result = null;
		// requestMethod = param.what;
		if (param.what == R.id.post) {
			response = webPost(param);
		} else if (param.what == R.id.get) {
			response = webGet(param);
		}
		if (response != null) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				try {
					result = EntityUtils
							.toString(response.getEntity(), "utf-8");
					MyLog.d(TAG, result);
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// BOCLearning.showToast("code--"
				// + response.getStatusLine().getStatusCode());
			}

		}
		return result;
	}

	/**
	 * 封装HTTP POST请求参数
	 * 
	 * @param methodName
	 *            请求方式
	 * @param params
	 *            请求携带的参数
	 * 
	 */
	public HttpResponse webPost(Message params) {
		
		List<NameValuePair> pairs = getListNameValuePair(params);
		String tempUrl = getRequestUrl(params);
		
		MyLog.i(TAG, "post_url:" + tempUrl);
		
//		String signText = getSignText(params);//获取签名字符串
//		String sign = signText + "&signKey="+KEY;//将signKey加到后面
//		MyLog.i("HttpTask", "签名未加密----"+sign);
//		//sign = LocalCryptUtil.SHA1(sign);//签名加密
//		sign = LocalCryptUtil.sha1Hash(sign);//签名加密
		
//		MyLog.i("httpUrl", "post_url:"+tempUrl+"&sign="+sign);
		// 请求加密
//		if (tempUrl.contains("?")) {
//			pairs.add(new BasicNameValuePair("sign", sign));
//		}
		
		HttpPost httpPost = new HttpPost(HttpUtils.getServerUrl() + params.obj.toString());

		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("charset", HTTP.UTF_8);

		try {
			HttpEntity entity = new UrlEncodedFormEntity(pairs, "UTF-8");
			MyLog.i(TAG, httpPost.getURI() + EntityUtils.toString(entity));
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			return response;

		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static List<NameValuePair> getListNameValuePair(Message params){
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		Bundle bundle = params.getData();
		for (String key : bundle.keySet()) {
			pairs.add(new BasicNameValuePair(key, bundle.getString(key)));
		}
		return pairs;
	}
	
	/**
	 * 执行HTTP GET请求
	 * 
	 * @param methodName
	 *            请求方式
	 * @param params
	 *            请求携带的参数
	 * 
	 */
	public HttpResponse webGet(Message params) {
		String getUrl = webGetUrl(params);
//		if(msg == R.id.cancel_focus){
//			getUrl = getUrl.replaceFirst("nail_art", "user");
//		}
		MyLog.i("httpUrl", "get_url:"+getUrl);
		HttpGet httpGet = new HttpGet(getUrl);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

//	private static String hmac_sha1(String datas) {
//		String reString = "";
//		try {
//			byte[] data = KEY.getBytes("UTF-8");
//			// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
//			SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
//			// 生成一个指定 Mac 算法 的 Mac 对象
//			Mac mac = Mac.getInstance("HmacSHA1");
//			// 用给定密钥初始化 Mac 对象
//			mac.init(secretKey);
//			byte[] text = datas.getBytes("UTF-8");
//			// 完成 Mac 操作
//			byte[] text1 = mac.doFinal(text);
//			reString = getHexString(text1);
//			// reString = Base64.encodeToString(text1, Base64.DEFAULT);
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}
//		return reString;
//	}

	public static String webGetUrl(Message params) {
		String getUrl = null;
		String url = getRequestUrl(params);//未加签名的url
//		String signText = getSignText(params);//未签名明文
//		String sign = signText + "&signKey="+KEY;//将signKey加到后面
//		//String encrySign = LocalCryptUtil.SHA1(sign);//经过签名
//		String encrySign = LocalCryptUtil.sha1Hash(sign);//经过签名
//		getUrl = url + "&sign=" + encrySign;
		
		getUrl = url;
		return getUrl;
	}

	/***
	 * 获取签名字符串(未加密)
	 * @param params
	 * @return
	 */
//	private static String getSignText(Message params){
//		StringBuffer signSb = new StringBuffer();
//		Bundle bundle = params.getData();
//		//对key列排序(a-z)
//		List<String> keys = new ArrayList<String>();
//		for (String key : bundle.keySet()) {
//			/*if(!key.equals("token")){
//			}*/
//			keys.add(key);
//		}
//		keys.add("t");
//		Collections.sort(keys,new Comparator<String>() {
//			@Override
//			@SuppressLint("DefaultLocale")
//			public int compare(String s1, String s2) {
//				s1 = s1.toUpperCase();
//				s2 = s2.toUpperCase();
//				return s1.compareTo(s2);
//			};
//		});
//		
//		
//		//对拍过序的key依次排好
//		String value = null;
//		String key = null;
//		for(int i=0; i<keys.size(); i++){
//			key = keys.get(i);
//			value = "t".equals(key) ? params.obj.toString() : bundle.getString(key);
//			if(i==0){
//				signSb.append(key+"=").append(value);//key=value
//			}else{
//				signSb.append("&"+key+"=").append(value);//&key=value
//			}
//		}
//		return signSb.toString();
//		
//		//return "GuestUserID=d29ca010-d6e4-4c45-91da-56b73afada68&ip=192.168.1.248&password=123456&t=login&username=15950458231&signKey=5@egweIU&-gewgw32136nht*gwe%g42";
//	}
	
	/**
	 * 获取请求的url
	 * @param params
	 * @return
	 */
	private static String getRequestUrl(Message params){
		String url = HttpUtils.getServerUrl()+ params.obj.toString();
		StringBuffer sb = new StringBuffer(url);
		Bundle bundle = params.getData();
		
		boolean flag = false;
		for (String key : bundle.keySet()) {
			if(!flag){
				flag = true;
				sb.append("?"+key+"=").append(bundle.get(key));//?key=value
			}else{
				sb.append("&"+key+"=").append(bundle.get(key));//&key=value
			}
			
		}
		return sb.toString();
	}
	
	public static String getHexString(byte[] b) throws Exception {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	@Override
	protected void onCancelled() {
		httpClient.getConnectionManager().shutdown();
		IListener.onCanel(msg);
		super.onCancelled();

	}

	public static final String KEY = "5@egweIU&-gewgw32136nht*gwe%g42";
	//public static final String KEY = "123456";

	@Override
	protected void onPostExecute(String result) {
		// 如果得到结果为空，正常返回数据，否则提示错误异常
		if (result != null) {
			IListener.onResponse(msg, result);
		} else {
			IListener.onError(msg, result);
		}
		super.onPostExecute(result);
	}

}
