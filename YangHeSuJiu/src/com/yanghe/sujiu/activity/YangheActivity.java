package com.yanghe.sujiu.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.yanghe.sujiu.BaseActivity;
import com.yanghe.sujiu.R;
import com.yanghe.sujiu.model.Advert;
import com.yanghe.sujiu.view.AdvGallery;
import com.yanghe.sujiu.view.AdvGallery.IOnItemCallback;
import com.yanghe.sujiu.view.AdvImageAdapter;

/**
 * 洋河股份Tab
 * 
 * @author mac_os
 */
public class YangheActivity extends BaseActivity implements OnClickListener {

	private LinearLayout mIndicatorLayout;// 显示器，所有的小圆点
	private AdvGallery mAdvGallery;// 广告控件
	private ProgressBar mAdProgress;// 加载进度条
	private FrameLayout bannerFrame;// 广告容器
	private AdvImageAdapter adapter;// 广告位image适配器
	
	private ImageButton activityBtn;
	private ImageButton productBtn;
	private ImageButton yangheBtn;
	private ImageButton aboutBtn;

	private List<Advert> mAdvertList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yanghe);

		final DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		final int h = displayMetrics.heightPixels;
		final int w = displayMetrics.widthPixels;
		final int longest = (h > w ? h : w) / 2;
		setImageFecher(longest, longest);

		initView();
		handle(1,"");
	}

	@Override
	protected void request(int msg) {
		// TODO Auto-generated method stub
		super.request(msg);
	}
	
	@Override
	public void handle(int msgId, String data){
		switch (msgId) {
		case 1:
			mAdvertList = getDataList();
			showAdvInfo();
			break;
		}
	}
	
	private List<Advert> getDataList() {
		List<Advert> list = new ArrayList<Advert>();
		list.add(new Advert());
		list.add(new Advert());
		list.add(new Advert());
		list.add(new Advert());
		return list;
	}

	private void initView() {
		activityBtn = (ImageButton) findViewById(R.id.yanghe_activity_imgbtn);
		activityBtn.setOnClickListener(this);
		productBtn = (ImageButton) findViewById(R.id.yanghe_product_imgbtn);
		productBtn.setOnClickListener(this);
		yangheBtn = (ImageButton) findViewById(R.id.yanghe_yanghe_imgbtn);
		yangheBtn.setOnClickListener(this);
		aboutBtn = (ImageButton) findViewById(R.id.yanghe_about_imgbtn);
		aboutBtn.setOnClickListener(this);
		LayoutParams lp = aboutBtn.getLayoutParams();
		lp.height = lp.width;
		activityBtn.setLayoutParams(lp);
		productBtn.setLayoutParams(lp);
		yangheBtn.setLayoutParams(lp);
		aboutBtn.setLayoutParams(lp);
		
		mAdProgress = (ProgressBar) findViewById(R.id.ad_progress);
		mIndicatorLayout = (LinearLayout) findViewById(R.id.adv_indicator);
		mAdvGallery = (AdvGallery) findViewById(R.id.activity_main_gallery);
		bannerFrame = (FrameLayout) findViewById(R.id.banner);
		setAdvPicWH();
	}

	/**
	 * 设置广告图片的宽高
	 */
	private void setAdvPicWH() {
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		int s_w = outMetrics.widthPixels;
		int s_h = outMetrics.heightPixels;

		LayoutParams param = bannerFrame.getLayoutParams();
		int w = s_w < s_h ? s_w : s_h;// 屏幕的宽度
		int h = (w * 674) / 640;// 640x674是图片实际的宽高比
		param.width = w;
		param.height = h;
		bannerFrame.setLayoutParams(param);
	}

	/**
	 * 显示广告信息
	 */
	private void showAdvInfo() {
		// 下载广告条的个数
		int size = mAdvertList.size();
		String[] paths = new String[size];

		mAdProgress.setVisibility(View.GONE);
		mIndicatorLayout.removeAllViews();
		int padding = getResources().getDimensionPixelSize(R.dimen.home_adview_bottom_ind_point_padding);
		for (int i = 0; i < size; i++) {
			paths[i] = mAdvertList.get(i).getImage_url();
			ImageView img = new ImageView(this);
			img.setPadding(0, padding, padding, 0);
			if (i == 0) {
				img.setImageResource(R.drawable.point_in);
			} else {
				img.setImageResource(R.drawable.point_on);
			}
			mIndicatorLayout.addView(img);
		}

		mAdvGallery.boundEvent();
		mAdvGallery.registCallback(itemSelectedCallback);

		adapter = new AdvImageAdapter(this, paths, mImageFetcher);
		adapter.setPicIds(getPics());
		mAdvGallery.setDataAdataper(adapter);
		mAdvGallery.lunchTimer();
	}

	private int[] getPics(){
		int[] pics = new int[4];
		pics[0] = R.drawable.list_image_1;
		pics[1] = R.drawable.list_image_2;
		pics[2] = R.drawable.list_image_3;
		pics[3] = R.drawable.list_image_4;
		return pics;
	}
	
	private IOnItemCallback itemSelectedCallback = new IOnItemCallback() {

		@Override
		public void onItemSelected(int position) {
			for (int i = 0; i < mIndicatorLayout.getChildCount(); i++) {
				ImageView tmpImage = (ImageView) mIndicatorLayout.getChildAt(i);
				tmpImage.setImageResource(R.drawable.point_on);
			}
			ImageView img = (ImageView) mIndicatorLayout.getChildAt(position);
			img.setImageResource(R.drawable.point_in);
			Advert advert = mAdvertList.get(position);
		}

		@Override
		public void onItemClick(int position) {
			if (null != mAdvertList && mAdvertList.size() >= position) {
				Advert advert = mAdvertList.get(position);
				startToDetailActivity(advert.getId());
			}
		}
	};

	private void startToDetailActivity(int id) {
		/*Intent intent = new Intent(this, NailDetailActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);*/
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
