package com.yanghe.sujiu.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.yanghe.sujiu.MainApp;
import com.yanghe.sujiu.Preferences;
import com.yanghe.sujiu.R;

public class HttpUtils {

	public static String getServerUrl() {
		String url = null;
		url = Preferences.get("server_url");
		if (StrUtil.isNull(url)) {
			url = MainApp.mainApp.getResources().getString(R.string.server_url);
			Preferences.put("server_url", url);
		}

		return url;
	}

	public static String sendPost(String url, String param, String charSet) {
		String result = "";
		if (url == null || "".equals(url)) {
			return result;
		}
		try {
			URL httpurl = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) httpurl.openConnection();
			httpConn.setConnectTimeout(60000);
			httpConn.setReadTimeout(60000);
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			PrintWriter out = new PrintWriter(httpConn.getOutputStream());
			out.print(param);
			out.flush();
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), charSet));

			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

			in.close();
		} catch (Exception e) {
			Log.e("eeee", e.getMessage());
			Log.e("eeee", e.getLocalizedMessage());
			return "timeOut";
		}
		return result;
	}

	public static String sendGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse hp = new DefaultHttpClient().execute(httpGet);
			Log.e("====>", hp.getStatusLine().getStatusCode() + "");
			if (hp.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(hp.getEntity());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String sendLogin(String url, String param, String charSet) {
		String result = "";
		if (url == null || "".equals(url)) {
			return result;
		}
		try {
			URL httpurl = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) httpurl.openConnection();
			httpConn.setConnectTimeout(6000);
			httpConn.setReadTimeout(6000);
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			PrintWriter out = new PrintWriter(httpConn.getOutputStream());
			out.print(param);
			out.flush();
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), charSet));

			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

			in.close();
		} catch (Exception e) {
			return "timeOut";
		}
		return result;
	}

	/**
	 * 判断网络
	 * 
	 * @param context
	 */
	public static void checkNetwork(Context context) {
		if (!isNetworkActive(context)) {
			DialogUtil.showNoNetworkDialog(context);
		}
	}

	/**
	 * 检测是否有网络连接
	 * 
	 * @param inContext
	 * @return
	 */
	public static boolean isNetworkActive(Context inContext) {
		ConnectivityManager cm = (ConnectivityManager) inContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		}
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo == null) {
			return false;
		}
		if (netinfo.isConnected() && netinfo.isAvailable()) {
			return true;
		}
		return false;
	}

	public static String sendHttpPost(String url, List<NameValuePair> params, String charSet) {
		String result = "error";
		if (url == null || "".equals(url)) {
			return result;
		}
		try {

			HttpPost hp = new HttpPost(url);
			hp.setEntity(new UrlEncodedFormEntity(params, charSet));
			HttpResponse respons = new DefaultHttpClient().execute(hp);
			if (respons.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(respons.getEntity());
			}
		} catch (Exception e) {
			return "timeOut";
		}
		return result;
	}

	// 根据URL获取图片
	public static Bitmap getHttpBitmap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setConnectTimeout(0);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static String uploadPic(String url, byte[] picBytes) {
		String imageString = null;
		// imageString = new String(picBytes);
		imageString = Base64.encodeToString(picBytes, Base64.NO_WRAP);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		NameValuePair fileValue = new BasicNameValuePair("padFile", imageString);
		NameValuePair fileNamevalue = new BasicNameValuePair("padFileName", String.valueOf(System.currentTimeMillis()));
		params.add(fileValue);
		params.add(fileNamevalue);
		String response = sendHttpPost(url, params, HTTP.UTF_8);

		return response;
	}

	public static String uploadFile(String url, String fileName) throws Exception {
		File aFile = new File(fileName);
		/** 实例化一个HttpClient */
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		/** 设置文件上传服务器地址 */
		HttpPost httppost = null;
		ContentBody cbFile = null;
		/** 设置文件类型和文件路径 */
		MultipartEntity mpEntity = new MultipartEntity();

		/** 上传文件 */
		httppost = new HttpPost(url);
		// 具体还有别的格式，请看http://baike.baidu.com/view/160611.htm，里面很详细
		cbFile = new FileBody(aFile, "application/octet-stream");// 任意二进制文件
		mpEntity.addPart("file", cbFile);

		mpEntity.addPart("fileName", new StringBody(aFile.getName()));

		httppost.setEntity(mpEntity);

		/** 执行Http请求并获得返回的响应数据 */
		try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			if ((response.getStatusLine().toString()).equals("HTTP/1.1 200 OK")) {
				if (resEntity != null) {
					String result = EntityUtils.toString(resEntity).toString();
					resEntity.consumeContent();
					return result;
				}
			} else {
				return null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		/** 关闭文件上传的通道 */
		httpclient.getConnectionManager().shutdown();

		return null;

	}

	/**
	 * 根据错误码显示服务器返回的错误信息
	 * @param errorCode
	 * @param context
	 */
	public static void showErrorInfo(int errorCode, Context context) {
		String error = null;
		switch (errorCode) {
		case 403:// 密码错误
			error = context.getResources().getString(R.string.error_403);
			break;
		case 501:// 图片已经收藏过
			error = context.getResources().getString(R.string.error_501);
			break;
		case 502:// 用户已经 关注过
			error = context.getResources().getString(R.string.error_502);
			break;
		case 503:// 图片不存在
			error = context.getResources().getString(R.string.error_503);
			break;
		case 504:// 图片上传失败
			error = context.getResources().getString(R.string.error_504);
			break;
		case 601:// 已存在此用户名
			error = context.getResources().getString(R.string.error_601);
			break;
		case 602:// 用户名和密码有一个为空
			error = context.getResources().getString(R.string.error_602);
			break;
		case 603:// 用户不存在
			error = context.getResources().getString(R.string.error_603);
			break;
		case 604:// 自己无法关注自己
			error = context.getResources().getString(R.string.error_604);
			break;
		default:
			error = context.getResources().getString(R.string.error_null, errorCode);
			break;
		}
		Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
	}

}
