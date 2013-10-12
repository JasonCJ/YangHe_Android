 package com.yanghe.sujiu.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.yanghe.sujiu.network.image.ImageFetcher;
import com.yanghe.sujiu.network.image.RecyclingImageView;

public class AdvImageAdapter extends BaseAdapter {
//	private LayoutInflater mInflater;
	private ImageFetcher loader;
	private String[] urls;
	private int[] picIds;
	private Context mContext;

	public AdvImageAdapter(Context context, String[] url, ImageFetcher loader) {
//		this.mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.urls = url;
		this.loader = loader;
	}

	public void setPicIds(int[] picIds){
		this.picIds = picIds;
	}
	
	@Override
	public int getCount() {
		return 40;
	}

	/**
	 * 获取数据的长度
	 * 
	 * @return
	 */
	public int getDataCount() {
		return null != urls ? urls.length : 0;
	}

	@Override
	public Object getItem(int arg0) {
		return urls[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		int index = position % getDataCount();
		Holder holder = null;
		if (view == null) {
			RecyclingImageView imageView = new RecyclingImageView(this.mContext);
			imageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			imageView.setScaleType(ScaleType.FIT_XY);
			holder = new Holder();
			holder.iv = imageView;
			view = imageView;
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
		//loader.loadImage(urls[index], holder.iv);
		
		//切换到真实数据的时候删掉这一句，用上面注释那句就好了
		loadImage(picIds[index], holder.iv);
		return view;
	}

	private void loadImage(int img_id, ImageView iv){
		iv.setBackgroundResource(img_id);
	}
	
	static class Holder {
		ImageView iv;
	}
}
