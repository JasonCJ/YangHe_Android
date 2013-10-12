package com.yanghe.sujiu.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Advert {
	private int id;// 广告图片的ID
	private String name;//图片标题
	private double ratio;//宽高比
	private String img_url;// 广告图片url
	private int user_id;//发表此图片的用户ID
	private String user_name;//用户名
	
	public Advert(JSONObject jsonObj) {
		try {
			this.id = jsonObj.optInt("id");
			this.name = jsonObj.optString("name");
			this.setRatio(jsonObj.getDouble("ratio"));
			this.img_url = jsonObj.optString("url");
			this.setUser_id(jsonObj.optInt("user_id"));
			this.setUser_name(jsonObj.optString("user_name"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Advert() {
	}

	public int getId() {
		return id;
	}

	public void setId(int advert_id) {
		this.id = advert_id;
	}

	public String getImage_url() {
		return img_url;
	}

	public void setImage_url(String image_url) {
		this.img_url = image_url;
	}

	public String getAdvName() {
		return name;
	}

	public void setAdvName(String name) {
		this.name = name;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
