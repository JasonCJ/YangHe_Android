package com.yanghe.sujiu.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yanghe.sujiu.BaseActivity;
import com.yanghe.sujiu.R;
import com.yanghe.sujiu.model.Advert;
import com.yanghe.sujiu.view.AdvGallery;
import com.yanghe.sujiu.view.AdvGallery.IOnItemCallback;
import com.yanghe.sujiu.view.AdvImageAdapter;
/**
 * 商品详情界面
 */
public class DetailActivity extends BaseActivity implements OnClickListener{
	private DetailActivity mContext;
	private LinearLayout mIndicatorLayout;// 显示器，所有的小圆点
	private AdvGallery mAdvGallery;// 广告控件
	private ProgressBar mAdProgress;// 加载进度条
	private FrameLayout bannerFrame;// 广告容器
	private AdvImageAdapter adapter;// 广告位image适配器
	private List<Advert> mAdvertList;
	private Button mBackBtn, mShareBtn;
	private ImageButton mDetailTelephone, mDetailAddOrderForm, mDetailBuy;
	
	private TextView mContentTitle, mContentPrice, mContentOther, mContentDetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		mContext = DetailActivity.this;
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		final int h = displayMetrics.heightPixels;
		final int w = displayMetrics.widthPixels;
		final int longest = (h > w ? h : w) / 2;
		setImageFecher(longest, longest);

		initView();
		handle(1,"");
	}
	
	/**
	 * 申明控件
	 */
	private void initView() {
		mBackBtn = (Button) findViewById(R.id.back_btn);
		mShareBtn = (Button) findViewById(R.id.share_btn);
		
		mDetailTelephone = (ImageButton) findViewById(R.id.detail_telephone);
		mDetailAddOrderForm = (ImageButton) findViewById(R.id.detail_add_order_form);
		mDetailBuy = (ImageButton) findViewById(R.id.detail_buy);
		
		mContentTitle = (TextView) findViewById(R.id.content_title_textView);
		mContentPrice = (TextView) findViewById(R.id.content_price_textView);
		mContentOther = (TextView) findViewById(R.id.content_other_textView);
		mContentDetail = (TextView) findViewById(R.id.content_detail_textView);
		
		mAdProgress = (ProgressBar) findViewById(R.id.ad_progress);
		mIndicatorLayout = (LinearLayout) findViewById(R.id.adv_indicator);
		mAdvGallery = (AdvGallery) findViewById(R.id.activity_main_gallery);
		bannerFrame = (FrameLayout) findViewById(R.id.banner);
		setAdvPicWH();
		
		mBackBtn.setOnClickListener(mContext);
		mShareBtn.setOnClickListener(mContext);
		mDetailTelephone.setOnClickListener(mContext);
		mDetailAddOrderForm.setOnClickListener(mContext);
		mDetailBuy.setOnClickListener(mContext);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_btn:
			DetailActivity.this.finish();
			break;
		case R.id.share_btn:
			break;
		case R.id.detail_telephone:
			break;
		case R.id.detail_add_order_form:
			break;
		case R.id.detail_buy:
			break;
		}
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
		int h = (w * 135) / 200;// 640x674是图片实际的宽高比
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
}
