package com.yanghe.sujiu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
/**
 * 设备相关信息的获取
 * 
 * @author gongyj.kai7
 *
 */
public class DeviceInfo{
	//获取手机IMEI
	public String getInfo(Context ctx){  
	    TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
	    return tm.getDeviceId();
	}
	//获取手机系统版本号
	public String getOsVersion(){ 
		return android.os.Build.VERSION.RELEASE;
	}
	//获取应用软件VersionCode 如1
	public int getVersion(Context ctx){
		PackageManager pm = ctx.getPackageManager();
		PackageInfo pinfo = null;
		try {
			pinfo = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return pinfo.versionCode;
	}
	
	/**
	 * 获取版本名称如 1.1
	 * @param ctx
	 * @return
	 */
	public String getVersionName(Context ctx){
		PackageManager pm = ctx.getPackageManager();
		PackageInfo pinfo = null;
		try {
			pinfo = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return pinfo.versionName;
	}
	
	/**
	 * 获取屏幕宽度最大dp值
	 * @param activity
	 * @return
	 */
	public int getWidthMaxDp(Activity activity){
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int wMaxDP = (160*dm.widthPixels/dm.densityDpi);
		//Toast.makeText(activity, "Device width max dp"+wMaxDP, Toast.LENGTH_LONG).show();
		return wMaxDP;
	}
}
