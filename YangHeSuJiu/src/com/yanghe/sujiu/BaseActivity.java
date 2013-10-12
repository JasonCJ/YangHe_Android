package com.yanghe.sujiu;

import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

import com.yanghe.sujiu.model.MyLog;
import com.yanghe.sujiu.network.HttpAsyncTask;
import com.yanghe.sujiu.network.HttpListener;
import com.yanghe.sujiu.network.image.ImageCache.ImageCacheParams;
import com.yanghe.sujiu.network.image.ImageFetcher;
import com.yanghe.sujiu.utils.HttpUtils;
import com.yanghe.sujiu.utils.StrUtil;
import com.yanghe.sujiu.view.LoadingDialog;

/**
 * 
 * 
 * activity 基类 继承它用于网络请求 需要网络请求的activity 要集成此类 子类一个完成的HTTP请求流程 ：
 * request()、onProgress()、handle()、onError() 子类用法实例： 1.HTTP请求参数：Bundle bundle =
 * new Bundle(); bundle.putString("paramkey1","paramvalue1");
 * bundle.putString("paramkey2","paramvalue2"); request = new Message();
 * 2.HTTP请求类型 request.what = R.id.post /R.id.get; 3.HTTP接口名称 request.obj =
 * "interfce_name"; request.setData(bundle); 4.调用父类请求 super.request(msgId) or
 * super.request(msgId,true)//第二个代表不带加载对话框的请求 调用此方法后 会新建一个异步任务执行http请求
 * 
 * 5.解析数据 handle方法 处理请求异常信息，开始解析数据，解析完成后 将需要显示的数据 显示出来 到此整个http请求结束
 * 
 * @author linjin
 * 
 */
public class BaseActivity extends FragmentActivity implements HttpListener {
	/**
	 * 请求参数
	 */
	protected Message request = null;
	/**
	 * 异步加载任务实例
	 */
	protected HttpAsyncTask mHttpAsyncTask;
	/**
	 * 加载中对话框
	 */
	protected LoadingDialog mLoadingDialog;

	protected ImageFetcher mImageFetcher;

	protected Map<Integer, Integer> mHttpStatusMap;// 存http请求状态

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		mHttpStatusMap = new HashMap<Integer, Integer>();
	}

	protected void setImageFecher(int width, int height) {
		// int mImageThumbSize =
		// getResources().getDimensionPixelSize(R.dimen.common_list_image_width_height);

		ImageCacheParams cacheParams = new ImageCacheParams(this, Constant.IMAGE_CACHE_DIR);

		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
													// app memory

		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously
		mImageFetcher = new ImageFetcher(this, width, height);
		// mImageFetcher.setLoadingImage(R.drawable.brand_icon_bg);
		mImageFetcher.addImageCache(this.getSupportFragmentManager(), cacheParams);
	}

	/**
	 * 请求，默认弹出对话框、对话框可取消
	 * 
	 * @param msg
	 *            消息标识
	 */
	protected void request(int msg) {
		request(msg, false);
	}

	/**
	 * 请求，请求可取消
	 * 
	 * @param msg
	 *            消息标识
	 * @param noDialog
	 *            是否弹框
	 * 
	 */
	protected void request(int msg, boolean noDialog) {
		request(msg, noDialog, true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mImageFetcher != null) {
			mImageFetcher.setExitTasksEarly(false);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mImageFetcher != null) {
			mImageFetcher.setPauseWork(false);
			mImageFetcher.setExitTasksEarly(true);
			mImageFetcher.flushCache();
		}
	}

	/**
	 * 发送请求
	 * 
	 * @param msg
	 *            消息标识
	 * @param noDialog
	 *            是否有对话框
	 * @param cancelable
	 *            请求是否可被取消
	 */
	protected void request(final int msgId, boolean noDialog, boolean cancelable) {
		if (!HttpUtils.isNetworkActive(this)) {// 没有网络
			showToast(MainApp.mainApp.getResources().getString(R.string.common_network_exception));
			return;
		}
		mHttpStatusMap.put(msgId, Constant.HTTP_STATUS_REQUESTED);
		if (!noDialog) {
			mLoadingDialog = new LoadingDialog(this, R.style.LoadingDialog);
			mLoadingDialog.show();
			mLoadingDialog.setCancelable(false);// 加载进度条不可取消
			mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					if (mHttpAsyncTask != null) {
						mHttpAsyncTask.cancel(false);
					}
				}
			});
			mLoadingDialog.show();
			// 显示dialog
		}
		
//		if (Utils.isUserRequest(msgId)) {
//			if (Preferences.hasUser()) {
//				request.getData().putString("user_id", Preferences.getUserId());
//			} else {
//				// 跳转到登录界面
//				/*Intent intent = new Intent(this, LoginActivity.class);
//				intent.putExtra("come", "BaseActivity");
//				startActivity(intent);*/
//			}
//		}
//		if (Utils.isUserChooseRequest(msgId)) {
//			if (Preferences.hasUser()) {
//				request.getData().putString("user_id", Preferences.getUserId());
//			} 
//		}
		

		// 执行异步任务
		mHttpAsyncTask = new HttpAsyncTask(this, msgId, needSetProxy());
		mHttpAsyncTask.execute(request);

	}

	@Override
	public void onProgress(int progress) {
		// if (mLoadingDialog != null) {
		// mLoadingDialog.setProgress(progress);
		// }
	}

	/**
	 * 处理数据
	 * 
	 * @param msg
	 *            消息标识
	 * @param data
	 *            数据
	 */
	public void handle(int msgId, String data) throws JSONException {
		mHttpStatusMap.put(msgId, Constant.HTTP_STATUS_HANDLE);
	}

	@Override
	public void onResponse(int msg, String result) {
		mHttpStatusMap.put(msg, Constant.HTTP_STATUS_RESPONSED);
		MyLog.i("BaseActivity", "---result:" + result);

		try {
			JSONObject object = new JSONObject(result);
			if (object.getInt("status") == Constant.HTTP_RESULT_OK) {
				handle(msg, result);
			} else {
				int error = object.getInt("error");
				HttpUtils.showErrorInfo(error, this);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mHttpAsyncTask = null;
		request = null;
		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
			try {
				mLoadingDialog.dismiss();
			} catch(IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 是否需要代理
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private boolean needSetProxy() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectivityManager.getActiveNetworkInfo();
		if (mobNetInfo == null || "WIFI".equals(mobNetInfo.getTypeName())) {
			return false;
		}
		if (mobNetInfo.getSubtypeName().toLowerCase(Locale.getDefault()).contains("cdma")) {
			// 电信网络
			if (android.net.Proxy.getDefaultHost() != null && android.net.Proxy.getDefaultPort() != -1) {
				return true;
			}
		} else if (mobNetInfo.getExtraInfo().contains("wap")) {
			// 移动或联通网络
			return true;
		}
		return false;
	}

	@Override
	public void onError(int msgId, String result) {
		if (StrUtil.isNull(result)) {
			showToast(MainApp.mainApp.getResources().getString(R.string.common_request_timeout));
		} else {
			showToast(MainApp.mainApp.getResources().getString(R.string.common_request_error));
		}
		if (mLoadingDialog != null) {
			mLoadingDialog.dismiss();
		}
	}

	@Override
	public void onCanel(int msgId) {
		showToast(MainApp.mainApp.getResources().getString(R.string.common_network_cancel));
	}

	public void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
}
