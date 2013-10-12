package com.yanghe.sujiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yanghe.sujiu.R;

public class StyleListAdapter extends BaseAdapter {

	private String[] array;
	private Context mContext;

	public StyleListAdapter(String[] array, Context context) {
		this.array = array;
		this.mContext = context;
	}

	public StyleListAdapter() {
	}

	@Override
	public int getCount() {
		return null == array ? 0 : array.length;
	}

	@Override
	public Object getItem(int position) {
		return null == array ? null : array[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(null == view){
			view = LayoutInflater.from(mContext).inflate(R.layout.adapter_style_item, null);
		}
		TextView tv = (TextView)view.findViewById(R.id.style_item_textview);
		tv.setText(array[position]);
		return view;
	}
	
	private int selectIndex = 0;
	public void setSelectIndex(int index){
		this.selectIndex = index;
		this.notifyDataSetChanged();
	}

	public void refreshData(String[] array) {
		this.array = array;
		this.notifyDataSetChanged();
	}

}
