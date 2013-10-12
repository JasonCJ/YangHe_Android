package com.yanghe.sujiu;

import java.io.File;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

public class MainApp extends Application {
	public static MainApp mainApp;
	public static String ROOTPATH = "/YangHeSuJiu";
	public static String DOWNLOADPATH = "/download";
	public static String IMAGEPATH = "/image";
	/**
	 * 用于本地数据存储
	 */
	public static SharedPreferences mSharedPreferences;
	@Override
	public void onCreate() {
		super.onCreate();
		mainApp = MainApp.this;
		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		initPath();
	}
	
	/**
	 * 建立用户数据文件夹
	 */
	public void initPath() {
		String ROOT;
		// 判断SD卡是否插入
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			ROOT = Environment.getExternalStorageDirectory().getPath();
		} else {
			Log.v("MKDIR", "No SD card!!!");
			ROOT = "/data/data";
		}

		if (ROOTPATH.equals("/YangHeSuJiu")) {
			ROOTPATH = ROOT + ROOTPATH;
			DOWNLOADPATH = ROOTPATH + DOWNLOADPATH;
			IMAGEPATH = ROOTPATH + IMAGEPATH;
		}

		File rootPath = new File(ROOTPATH);
		if (!rootPath.exists()) {
			rootPath.mkdirs();
			Log.d("INITPATH", "ROOT");
		}

		File downloadPath = new File(DOWNLOADPATH);
		if (!downloadPath.exists()) {
			downloadPath.mkdirs();
			Log.d("INITPATH", "DOWNLOAD");
		}

		File imagePath = new File(IMAGEPATH);
		if (!imagePath.exists()) {
			imagePath.mkdirs();
			Log.d("INITPATH", "IMAGE");
		}
	}

	public String getDownloadPath() {
		return DOWNLOADPATH;
	}

	public String getImagePath() {
		return IMAGEPATH;
	}
}
