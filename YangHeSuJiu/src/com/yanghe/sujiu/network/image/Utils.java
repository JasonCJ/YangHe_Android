/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yanghe.sujiu.network.image;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import com.yanghe.sujiu.Preferences;
import com.yanghe.sujiu.R;

/**
 * Class containing some static utility methods.
 */
public class Utils {
    private Utils() {};
    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
    
    public static String getVersionName(Context context)
    {
            // 获取packagemanager的实�?
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名�?代表是获取版本信�?
            PackageInfo packInfo = null;
			try {
				packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            String version = packInfo.versionName;
            return version;
    }

    /**
     * 判断该请求是不是用户相关的请求，user_id必填
     * @param msgId
     * @return
     */
//	public static boolean isUserRequest(int msgId) {
//		List<Integer> list = new ArrayList<Integer>();
//		list.add(R.id.login);
//		list.add(R.id.register);
//		list.add(R.id.add_collection);
//		list.add(R.id.delete_collection);
//		list.add(R.id.focus);
//		list.add(R.id.cancel_focus);
//		list.add(R.id.leave_message);
//		return list.contains(msgId);
//	}
    
	/**
     * 判断该请求是不是用户相关的请求，user_id可选
     * @param msgId
     * @return
     */
//	public static boolean isUserChooseRequest(int msgId) {
//		List<Integer> list = new ArrayList<Integer>();
//		list.add(R.id.show_pic);
//		return list.contains(msgId);
//	}
	
	/**
	 * 验证是否已经登录
	 * @param context
	 * @return
	 */
//	public static boolean hasLogin(Context context){
//		if(Preferences.hasUser()){
//			return true;
//		}else{
//			Intent intent = new Intent(context, ImpowerLoginActivity.class);
//			context.startActivity(intent);
//			return false;
//		}
//	}
	
	 /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, int dpValue) {  
        float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, int pxValue) {  
        float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
}
