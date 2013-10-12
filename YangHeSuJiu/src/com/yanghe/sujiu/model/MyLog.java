package com.yanghe.sujiu.model;


import android.util.Log;

public class MyLog {
	
	/**
	 * 该变量控制是否打印log,还有当debug = false的时候也是不打印日志�?
	 */
	static boolean PRINT_LOG = true;

	public static void i(String value) {
		if (PRINT_LOG) {
			Log.i("MyLog", value);
		}
	}

	public static void i(String Tag, String value) {
		if (PRINT_LOG) {
			Log.i(Tag, value);
		}
	}
	
	public static void i(String Tag, byte[] array){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<array.length; i++){
			if(i == (array.length-1)){
				sb.append(array[i]+"");
			}
			else{
				sb.append(array[i]+",");
			}
		}
		if (PRINT_LOG) {
			Log.i(Tag, "buffer:"+sb.toString());
		}
	}

	public static void d(String value) {
		if (PRINT_LOG) {
			Log.d("MyLog", value);
		}
	}
	public static void d(String Tag, String value) {
		if (PRINT_LOG) {
			Log.d(Tag, value);
		}
	}
}
