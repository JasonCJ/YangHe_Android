package com.yanghe.sujiu.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.yanghe.sujiu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * 启动页面
 * @author mac_os
 */
public class StartActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				startActivity(new Intent(StartActivity.this, MainTabHost.class));
//				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				overridePendingTransition(R.anim.fade, R.anim.hold);
				finish();
			}
		};
		timer.schedule(task, 2300);
	}
}
