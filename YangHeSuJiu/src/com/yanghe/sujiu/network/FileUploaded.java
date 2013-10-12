package com.yanghe.sujiu.network;
/**
 * 上传完成的文件
 * @author gongyjkai7
 */
public class FileUploaded {

	private boolean	is_succ;
	private String url;
	
	public FileUploaded(boolean is_succ, String url) {
		super();
		this.is_succ = is_succ;
		this.url = url;
	}
	public boolean isIs_succ() {
		return is_succ;
	}
	public void setIs_succ(boolean is_succ) {
		this.is_succ = is_succ;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
