package com.yanghe.sujiu.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanghe.sujiu.BaseActivity;
import com.yanghe.sujiu.R;
import com.yanghe.sujiu.adapter.StyleListAdapter;
/**
 * 商品列表Tab
 * @author mac_os
 */
public class CommodityActivity extends BaseActivity 
	implements OnClickListener, OnItemClickListener, OnScrollListener{
	private CommodityActivity mContext;
	private LinearLayout mTypeSelectLayout;
	private ImageView mArrowsImageView;
	private TextView mTitleTextView;
	private ListView mCommodityList;
	private BusinessAdapter adapter;
	private int visibleItemCount;
	private int visibleLastIndex;
	
	private View typeView;// 类型view
	private ListView typeListView;// 类型listview
	private StyleListAdapter typeAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commodity);
		mContext = CommodityActivity.this;
		initView();
	}
	
	/**
	 * 声明视图控件
	 */
	private void initView() {
		mTypeSelectLayout = (LinearLayout) findViewById(R.id.type_select_Layout);
		mTypeSelectLayout.setOnClickListener(mContext);
		
		mArrowsImageView = (ImageView) findViewById(R.id.arrows_ImageView);
		mTitleTextView = (TextView) findViewById(R.id.title_TextView);
		
		mCommodityList = (ListView) findViewById(R.id.commodity_list);
		adapter = new BusinessAdapter(mContext);
		mCommodityList.setAdapter(adapter);
		
		
		typeView = findViewById(R.id.commodity_list_type_linearlayout);
		typeListView = (ListView) findViewById(R.id.commodity_list_type_listview);
		typeAdapter = new StyleListAdapter(new String[] { "苏酒", "老字号", "洋河大曲", "梦之蓝" }, mContext);
		typeListView.setAdapter(typeAdapter);
		
		
		mCommodityList.setOnItemClickListener(mContext);
		mCommodityList.setOnScrollListener(mContext);
		typeListView.setOnItemClickListener(mContext);
//		mCommodityList.setOnScrollListener(new ListView.OnScrollListener(){
//			boolean mGetMore = false;
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,
//					int visibleItemCount, int totalItemCount) {
//				mGetMore = false;
//				if (firstVisibleItem + visibleItemCount == totalItemCount) {
//					mGetMore = true;
//				}
//			}
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//				if (mGetMore
//						&& scrollState == OnScrollListener.SCROLL_STATE_IDLE
//						&& isNextPage) {
//					top++;
//					GetMsdsList(corpname, String.valueOf(top));
//				}
//			}});
		
	}
	
	/**
	 * 商品适配器
	 */
	private class BusinessAdapter extends BaseAdapter{
		/** 预定map **/
		private Map<Integer, Boolean> bookMap;
		private LayoutInflater mInflater;
		public BusinessAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			bookMap = new HashMap<Integer, Boolean>();
		}
		@Override
		public int getCount() {
			return 10;
		}
		@Override
		public Object getItem(int position) {
			return position;
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder mHolder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_business, null);
				mHolder = new ViewHolder();
				mHolder.item_business_icon = (ImageView) convertView.findViewById(R.id.item_business_icon);
				mHolder.item_order_Button = (Button) convertView.findViewById(R.id.item_order_Button);
				mHolder.item_title = (TextView) convertView.findViewById(R.id.item_title);
				mHolder.item_price = (TextView) convertView.findViewById(R.id.item_price);
				mHolder.item_discount = (TextView) convertView.findViewById(R.id.item_discount);
				mHolder.item_integral = (TextView) convertView.findViewById(R.id.item_integral);
				//订购按钮弹出的订购编辑布局
				mHolder.item_order_do_layout = (RelativeLayout) convertView.findViewById(R.id.item_order_do_layout);
				mHolder.item_minus_order_Button = (Button) convertView.findViewById(R.id.item_minus_order_Button);
				mHolder.item_add_order_Button = (Button) convertView.findViewById(R.id.item_add_order_Button);
				mHolder.item_ok_order_Button = (Button) convertView.findViewById(R.id.item_ok_order_Button);
				mHolder.item_order_count_EditText = (EditText) convertView.findViewById(R.id.item_order_count_EditText);
				convertView.setTag(mHolder);
			} else {
				mHolder = (ViewHolder) convertView.getTag();
			}
			mHolder.item_order_Button.setOnClickListener(new ButtonClickListener(position, mHolder.item_order_do_layout));
			
			mHolder.item_business_icon.setBackgroundResource(R.drawable.list_image_1);
			mHolder.item_title.setText("洋河蓝色经典M6");
			mHolder.item_price.setText("¥" + "766");
			mHolder.item_discount.setText("八折优惠");
			mHolder.item_integral.setText("会员购买可获" + "766" + "积分");
			
			showOrderButton(bookMap.get(position), mHolder.item_order_do_layout);
			return convertView;
		}
		
		class ButtonClickListener implements OnClickListener {
			int position = 0;
			RelativeLayout item_order_do_layout;
			ButtonClickListener(int position, RelativeLayout item_order_do_layout) {
				this.position = position;
				this.item_order_do_layout = item_order_do_layout;
			}
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.item_order_Button:
					boolean hasBooked = false;// 是否已经订购
					if (bookMap.containsKey(position)) {
						// 不是第一次点击订购
						hasBooked = bookMap.get(position);
						hasBooked = !hasBooked;
					} else {
						// 第一次点击订购
						hasBooked = true;
					}
					bookMap.put(position, hasBooked);
					// 在这里添加动画
					showOrderButton(hasBooked, item_order_do_layout);
					break;
				case R.id.item_minus_order_Button:
					break;
				case R.id.item_add_order_Button:
					break;
				case R.id.item_ok_order_Button:
					break;
				}
			}
		}
		/**
		 * 显示点击订购的Relativelayout
		 * @param show
		 * @param item_order_do_layout
		 */
		private void showOrderButton(Boolean show, RelativeLayout item_order_do_layout) {
			if (null == show) {
				item_order_do_layout.setVisibility(View.GONE);
			} else {
				if (show) {
					item_order_do_layout.setVisibility(View.VISIBLE);
				} else {
					item_order_do_layout.setVisibility(View.GONE);
				}
			}
		}
	}
	
	private class ViewHolder{
		/** 商品图片 */
		ImageView item_business_icon;
		/** 订购按钮 */
		Button item_order_Button;
		/** 标题、价格、折扣、可获积分*/
		TextView item_title, item_price, item_discount, item_integral;
		/** 订购按钮出现的布局及控件 */
		RelativeLayout item_order_do_layout;
		Button item_minus_order_Button, item_add_order_Button, item_ok_order_Button;
		EditText item_order_count_EditText;
	}
	
	private void showStyleView(int visible) {
		if (visible == View.VISIBLE) {
			mArrowsImageView.setBackgroundResource(R.drawable.icon_arrow_up);
			typeView.setVisibility(View.VISIBLE);
		} else {
			mArrowsImageView.setBackgroundResource(R.drawable.icon_arrow_down);
			typeView.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.type_select_Layout:
			int vis = (typeView.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE;
			showStyleView(vis);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		showStyleView(View.GONE);
		switch (parent.getId()) {
		case R.id.commodity_list:
			startActivity(new Intent(mContext, DetailActivity.class));
			break;
		case R.id.commodity_list_type_listview:
			break;
		}
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		// 当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1；
		// 由于用户的操作，屏幕产生惯性滑动时为2
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {
			// 如果是自动加载,可以在这里放置异步加载数据的代码
			Log.i("LOADMORE", "loading...");
			// loadMoreButton.setText("load more ....");
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// visibleItemCount表示当前屏幕可以见到的ListItem(部分显示的ListItem也算)总数
		this.visibleItemCount = visibleItemCount;
		// firstVisibleItem表示当前屏幕可见的第一个ListItem(部分显示的ListItem也算)在整个ListView的位置（下标从0开始）
		this.visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
		// visibleItemCount表示当前屏幕可以见到的ListItem(部分显示的ListItem也算)总数

		// listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem(最后ListItem要完全显示出来才算)在整个ListView的位置（下标从0开始）
		Log.i("firstVisibleItem", String.valueOf(firstVisibleItem));
		Log.i("visibleItemCount", String.valueOf(visibleItemCount));
		Log.i("totalItemCount", String.valueOf(totalItemCount));

		Log.i("firstPosition",
				String.valueOf(mCommodityList.getFirstVisiblePosition()));

		Log.i("lasPosition", String.valueOf(mCommodityList.getLastVisiblePosition()));
	}
}
