package com.yanghe.sujiu.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.yanghe.sujiu.R;

public class DialogUtil {
	
	/**
	 * 显示dialog，该方法只适合显示提示作用的dialog
	 * @param context
	 * @param title
	 * @param message
	 */
	public static void showDialog(Context context, String message){
		new AlertDialog.Builder(context)
		.setTitle(R.string.dialog_title_tip)
		.setMessage(message)
		.setPositiveButton(R.string.dialog_ok, null)
		.show();
	}
	
	public static void showNoNetworkDialog(Context context){
		new AlertDialog.Builder(context)
		.setTitle(R.string.dialog_title_tip)
		.setMessage(R.string.dialog_no_netword)
		.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//跳转至设置网络
			}
		})
		.setNegativeButton(R.string.dialog_cancle, null)
		.show();
	}
}
