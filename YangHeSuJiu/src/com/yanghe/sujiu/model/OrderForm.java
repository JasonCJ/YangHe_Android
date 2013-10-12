package com.yanghe.sujiu.model;

public class OrderForm {
	private String title, price, discount, integral;
	private boolean ischeck;
	private int count, imageUrl;
	
	public OrderForm(int imageUrl, 
			String title, String price, String discount, String integral,
			boolean ischeck, int count) {
		setImageUrl(imageUrl);
		setTitle(title);
		setPrice(price);
		setDiscount(discount);
		setIntegral(integral);
		setIscheck(ischeck);
		setCount(count);
	}
	
	public int getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(int imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	public boolean isIscheck() {
		return ischeck;
	}
	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
