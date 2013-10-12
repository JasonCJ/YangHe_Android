package com.yanghe.sujiu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanghe.sujiu.R;
import com.yanghe.sujiu.model.MyLog;

public class OrderFormAdapter extends BaseAdapter {
	private static final int NOT_OrderForm = 0;
	private static final int YES_OrderForm = 1;
	private static final int ING_OrderForm = 2;
	private static final int FINISH_OrderForm = 3;
	private LayoutInflater mInflater;
	boolean[] itemStatus;
	int typeStatus;
	public OrderFormAdapter(ISelectedCallback callback, Context context, int typeStatus) {
		this.mInflater = LayoutInflater.from(context);
		this.itemStatus = new boolean[10];
		this.callback = callback;
		this.typeStatus = typeStatus;
	}

	// 单项勾选或者取消单项勾选
	public void toggle(int position) {
		if (itemStatus[position] == true) {
			itemStatus[position] = false;
		} else {
			itemStatus[position] = true;
		}
		isDisplaySubmitButton();
		this.notifyDataSetChanged();// date changed and we should refresh
									// the view
	}

	// 根据是否有勾选项来判断是否显示批量提交订单按钮
	private void isDisplaySubmitButton() {
		boolean hasSelected = false;
		boolean selectedAll = true;
		for (int i = 0; i < itemStatus.length; i++) {
			if (itemStatus[i]) {
				hasSelected = true;// 有一个为真，则代表有被选中的
			} else {
				selectedAll = false;// 有一个为假，则代表不是全选中
			}
		}
		
		if(null != callback){
			callback.hasItemSelected(hasSelected);
			callback.selectedAllCallback(selectedAll);
		}
	}

	/**
	 * 是全部选中
	 * 
	 * @return
	 */
	public boolean isAllSelected() {
		boolean isAllSelected = true;
		for (int i = 0; i < itemStatus.length; i++) {
			if (!itemStatus[i]) {
				isAllSelected = false;
				break;
			}
		}
		return isAllSelected;
	}

	/**
	 * 是全部未选中
	 * 
	 * @return
	 */
	public boolean isAllUnSelected() {
		boolean isAllUnSelected = true;
		for (int i = 0; i < itemStatus.length; i++) {
			if (itemStatus[i]) {
				isAllUnSelected = false;
				break;
			}
		}
		return isAllUnSelected;
	}

	// 全部勾选或者取消勾选
	public void allCheckedStatus(boolean isChecked) {
		for (int i = 0; i < itemStatus.length; i++) {
			itemStatus[i] = isChecked;
		}
		isDisplaySubmitButton();
		this.notifyDataSetChanged();// date changed and we should refresh
									// the view
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
			convertView = mInflater.inflate(R.layout.list_item_order_form, null);
			mHolder = new ViewHolder();
			mHolder.item_business_icon = (ImageView) convertView.findViewById(R.id.item_business_icon);
			mHolder.item_order_Button = (Button) convertView.findViewById(R.id.item_order_Button);
			mHolder.item_title = (TextView) convertView.findViewById(R.id.item_title);
			mHolder.item_price = (TextView) convertView.findViewById(R.id.item_price);
			mHolder.item_discount = (TextView) convertView.findViewById(R.id.item_discount);
			mHolder.item_integral = (TextView) convertView.findViewById(R.id.item_integral);
			mHolder.item_select_checkBox = (CheckBox) convertView.findViewById(R.id.item_select_checkBox);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.item_business_icon.setBackgroundResource(R.drawable.list_image_1);
		mHolder.item_title.setText("洋河蓝色经典M6");
		mHolder.item_price.setText("¥" + "766");
		mHolder.item_discount.setText("八折优惠");
		mHolder.item_integral.setText("会员购买可获" + "766" + "积分");

		if (typeStatus != NOT_OrderForm) {
			mHolder.item_order_Button.setVisibility(View.VISIBLE);
			mHolder.item_select_checkBox.setVisibility(View.VISIBLE);
			mHolder.item_select_checkBox.setOnCheckedChangeListener(new MyCheckBoxChangedListener(position));
			mHolder.item_select_checkBox.setChecked(itemStatus[position]);
			mHolder.item_order_Button.setOnClickListener(new ButtonClickListener(position));
		} else {
			mHolder.item_order_Button.setVisibility(View.VISIBLE);
			mHolder.item_select_checkBox.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	class ViewHolder {
		/** 商品图片 */
		ImageView item_business_icon;
		/** 订购按钮 */
		Button item_order_Button;
		/** 标题、价格、折扣、可获积分 */
		TextView item_title, item_price, item_discount, item_integral;
		/** 选择checkbox */
		CheckBox item_select_checkBox;
	}

	class ButtonClickListener implements OnClickListener {
		int position = 0;

		ButtonClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
		}
	}

	class MyCheckBoxChangedListener implements OnCheckedChangeListener {
		int position;

		MyCheckBoxChangedListener(int position) {
			this.position = position;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				itemStatus[position] = true;
			} else {
				itemStatus[position] = false;
			}
			MyLog.d("" + itemStatus[position]);
			isDisplaySubmitButton();
		}
	}

	public List<Integer> getSelectedItemIndexes() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < itemStatus.length; i++) {
			if (itemStatus[i]) {
				list.add(i);
			}
		}
		return list;
	}
	
	private ISelectedCallback callback;
	public void setSelectedCallback(ISelectedCallback callback){
		this.callback = callback;
	}
	
	public interface ISelectedCallback{
		/**
		 * 全选中回调
		 */
		public void selectedAllCallback(boolean selectedAll);
		
		/**
		 * 有被选中的项回调
		 */
		public void hasItemSelected(boolean hasSelected);
	}
}
