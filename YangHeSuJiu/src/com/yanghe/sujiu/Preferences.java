package com.yanghe.sujiu;

import android.content.SharedPreferences.Editor;

public class Preferences {

	public static final String USERID = "userId";
	public static final String HAS_USER = "has_user";

	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";


	
	public static final String VERSION = "version";

	/**
	 * 保存登录的token
	 * 
	 * @param userId
	 */
	public static void saveUserId(String userId) {
		Editor editor = MainApp.mSharedPreferences.edit();
		editor.putString(USERID, userId);
		editor.putBoolean(HAS_USER, true);
		editor.commit();
	}

	/**
	 * 保存username
	 * 
	 * @param
	 */
	public static void saveUserName(String username) {
		Editor editor = MainApp.mSharedPreferences.edit();
		editor.putString(USERNAME, username);
		editor.commit();
	}

	public static String getUserName() {
		return MainApp.mSharedPreferences.getString(USERNAME, "");
	}

	/**
	 * 获取登录token
	 * 
	 * @return
	 */
	public static String getUserId() {
		return MainApp.mSharedPreferences.getString(USERID, "");
	}

	/**
	 * 是否已经登录
	 * 
	 * @return true：已经登录 false 未登录
	 */
	public static boolean hasUser() {
		return MainApp.mSharedPreferences.getBoolean(HAS_USER, false);
	}

	/**
	 * 注销 删除token
	 */
	public static void deleteUser() {
		Editor editor = MainApp.mSharedPreferences.edit();
		editor.remove(USERID);
		editor.remove(HAS_USER);
		editor.commit();
	}

	public static void put(String key, String value) {
		Editor editor = MainApp.mSharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String get(String key) {
		return MainApp.mSharedPreferences.getString(key, "");
	}
	
}
