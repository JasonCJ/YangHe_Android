package com.yanghe.sujiu.activity;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.yanghe.sujiu.R;
/**
 * 主界面
 */
@SuppressWarnings("deprecation")
public class MainTabHost extends TabActivity implements OnClickListener{
	private MainTabHost mContext;
	private TabHost mTabHost;
	
	private LinearLayout mCommodityLayout;
	private RelativeLayout mOrderFormLayout;
	private LinearLayout mYangheLayout;
	private LinearLayout mPersonageLayout;
	private LinearLayout mScanLayout;
	
	private ImageView mCommodityImageView;
	private ImageView mOrderFormImageView;
	private ImageView mYangheImageView;
	private ImageView mPersonageImageView;
	private ImageView mScanImageView;
	
	private TextView mOrderFormCount;
	
	private static final String TAB_COMMODITY = "Commodity";
	private static final String TAB_ORDER_FORM = "OrderForm";
	private static final String TAB_YANG_HE = "Yanghe";
	private static final String TAB_PERSONAGE = "Personage";
	private static final String TAB_SCAN = "Scan";
	private static final int COMMODITY = 0;
	private static final int ORDER_FORM = 1;
	private static final int YANG_HE = 2;
	private static final int PERSONAGE = 3;
	private static final int SCAN = 4;
	
	private Dialog exitDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost_main);
		mContext = MainTabHost.this;
		initView();
	}
	
	/**
	 * 声明视图控件
	 */
	private void initView() {
		mTabHost = mContext.getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec(TAB_COMMODITY)
				.setIndicator(TAB_COMMODITY)
				.setContent(new Intent(mContext, CommodityActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_ORDER_FORM)
				.setIndicator(TAB_ORDER_FORM)
				.setContent(new Intent(mContext, OrderFormActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_YANG_HE)
				.setIndicator(TAB_YANG_HE)
				.setContent(new Intent(mContext, YangheActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSONAGE)
				.setIndicator(TAB_PERSONAGE)
				.setContent(new Intent(mContext, PersonageActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_SCAN)
				.setIndicator(TAB_SCAN)
				.setContent(new Intent(mContext, ScanActivity.class)));
		
		mCommodityLayout = (LinearLayout) findViewById(R.id.commodity_Layout);
		mOrderFormLayout = (RelativeLayout) findViewById(R.id.order_form_Layout);
		mYangheLayout = (LinearLayout) findViewById(R.id.yanghe_Layout);
		mPersonageLayout = (LinearLayout) findViewById(R.id.personage_Layout);
		mScanLayout = (LinearLayout) findViewById(R.id.scan_Layout);
		
		mCommodityImageView = (ImageView) findViewById(R.id.commodity_ImageView);
		mOrderFormImageView = (ImageView) findViewById(R.id.order_form_ImageView);
		mYangheImageView = (ImageView) findViewById(R.id.yanghe_ImageView);
		mPersonageImageView = (ImageView) findViewById(R.id.personage_ImageView);
		mScanImageView = (ImageView) findViewById(R.id.scan_ImageView);
		
		mOrderFormCount = (TextView) findViewById(R.id.order_form_count);
		mOrderFormCount.setText("99+");
		
		mCommodityLayout.setOnClickListener(mContext);
		mOrderFormLayout.setOnClickListener(mContext);
		mYangheLayout.setOnClickListener(mContext);
		mPersonageLayout.setOnClickListener(mContext);
		mScanLayout.setOnClickListener(mContext);
		
		/**
		 * 页面加载默认使用分页下标为2的子分页(洋河)
		 */
		mTabHost.setCurrentTab(2);
		changeButtonStatus(YANG_HE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commodity_Layout:
			mTabHost.setCurrentTabByTag(TAB_COMMODITY);
			changeButtonStatus(COMMODITY);
			break;
		case R.id.order_form_Layout:
			mTabHost.setCurrentTabByTag(TAB_ORDER_FORM);
			changeButtonStatus(ORDER_FORM);
			break;
		case R.id.yanghe_Layout:
			mTabHost.setCurrentTabByTag(TAB_YANG_HE);
			changeButtonStatus(YANG_HE);
			break;
		case R.id.personage_Layout:
			mTabHost.setCurrentTabByTag(TAB_PERSONAGE);
			changeButtonStatus(PERSONAGE);
			break;
		case R.id.scan_Layout:
			mTabHost.setCurrentTabByTag(TAB_SCAN);
			changeButtonStatus(SCAN);
			break;
			default:
				break;
		}
	}
	
	private void changeButtonStatus(int labelPage) {
		switch (labelPage) {
		case COMMODITY:
			mCommodityImageView.setBackgroundResource(R.drawable.tabbar_commodity_click);
			mOrderFormImageView.setBackgroundResource(R.drawable.tabbar_order_form);
			mYangheImageView.setBackgroundResource(R.drawable.tabbar_yanghe);
			mPersonageImageView.setBackgroundResource(R.drawable.tabbar_personage);
			mScanImageView.setBackgroundResource(R.drawable.tabbar_order);
			break;
		case ORDER_FORM:
			mCommodityImageView.setBackgroundResource(R.drawable.tabbar_commodity);
			mOrderFormImageView.setBackgroundResource(R.drawable.tabbar_order_form_click);
			mYangheImageView.setBackgroundResource(R.drawable.tabbar_yanghe);
			mPersonageImageView.setBackgroundResource(R.drawable.tabbar_personage);
			mScanImageView.setBackgroundResource(R.drawable.tabbar_order);
			break;
		case YANG_HE:
			mCommodityImageView.setBackgroundResource(R.drawable.tabbar_commodity);
			mOrderFormImageView.setBackgroundResource(R.drawable.tabbar_order_form);
			mYangheImageView.setBackgroundResource(R.drawable.tabbar_yanghe_click);
			mPersonageImageView.setBackgroundResource(R.drawable.tabbar_personage);
			mScanImageView.setBackgroundResource(R.drawable.tabbar_order);
			break;
		case PERSONAGE:
			mCommodityImageView.setBackgroundResource(R.drawable.tabbar_commodity);
			mOrderFormImageView.setBackgroundResource(R.drawable.tabbar_order_form);
			mYangheImageView.setBackgroundResource(R.drawable.tabbar_yanghe);
			mPersonageImageView.setBackgroundResource(R.drawable.tabbar_personage_click);
			mScanImageView.setBackgroundResource(R.drawable.tabbar_order);//tabbar_scan
			break;
		case SCAN:
			mCommodityImageView.setBackgroundResource(R.drawable.tabbar_commodity);
			mOrderFormImageView.setBackgroundResource(R.drawable.tabbar_order_form);
			mYangheImageView.setBackgroundResource(R.drawable.tabbar_yanghe);
			mPersonageImageView.setBackgroundResource(R.drawable.tabbar_personage);
			mScanImageView.setBackgroundResource(R.drawable.tabbar_order_clicked);
			break;
			default:
				break;
		}
	}
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && !exitDialog.isShowing()) {
//			exitDialog();
//		}
//		return false;
//	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			exitDialog();
		}
		return super.dispatchKeyEvent(event);
	}
	
	/**
	 * 退出dialog
	 * 退出布局:dialog_exit
	 */
	private void exitDialog() {
		View layout = LayoutInflater.from(mContext).inflate(R.layout.dialog_exit, null);
		exitDialog = new Dialog(mContext, R.style.CustomeDialog);
		exitDialog.setContentView(layout);
		exitDialog.setCanceledOnTouchOutside(false);
		exitDialog.show();
		Button ok_button = (Button) layout.findViewById(R.id.ok_button);
		Button cancel_button = (Button) layout.findViewById(R.id.cancel_button);
		ok_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exitDialog.dismiss();
				mContext.finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		cancel_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exitDialog.dismiss();
			}
		});
	}
}
