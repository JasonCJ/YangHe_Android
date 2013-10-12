package com.yanghe.sujiu.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yanghe.sujiu.R;

/**
 * 自定义 加载dialog
 * 
 * @author linjin
 * 
 */
public class LoadingDialog extends Dialog {
	Context context;

	public LoadingDialog(Context context) {
		super(context);
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		init();
	}

	void init() {
		/**
		 * "加载项"布局，此布局被添加到ListView的Footer中。
		 */
		LinearLayout contentView = new LinearLayout(context);
		contentView.setMinimumHeight(60);
		contentView.setGravity(Gravity.CENTER);
		contentView.setOrientation(LinearLayout.HORIZONTAL);
		contentView.setBackgroundResource(R.drawable.dialog_box);

		/**
		 * 向"加载项"布局中添加一个圆型进度条。
		 */
		ImageView image = new ImageView(context);
		int width = context.getResources().getDimensionPixelSize(R.dimen.dialog_loadimage_width);
		int height = context.getResources().getDimensionPixelSize(R.dimen.dialog_loadimage_height);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
		image.setLayoutParams(params);
		image.setImageResource(R.drawable.loading_ios);
		Animation anim = AnimationUtils.loadAnimation(context,
				R.anim.rotate_repeat);
		LinearInterpolator lir = new LinearInterpolator();
		anim.setInterpolator(lir);
		image.setAnimation(anim);
		//TextView text = new TextView(context);
		//text.setText(context.getResources().getString(R.string.loading));
		//text.setTextColor(context.getResources().getColor(android.R.color.black));
		contentView.addView(image);
		//contentView.addView(text);
		setContentView(contentView);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.dismiss();
		}
		return true;
	}

}
