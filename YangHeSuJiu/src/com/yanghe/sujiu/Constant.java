package com.yanghe.sujiu;
/**
 * APP常量类
 */
public class Constant {
	public static final String IMAGE_CACHE_DIR = "meijia_thumbs";
	/**
	 * HTTP常量
	 */
	public static final int HTTP_RESULT_OK = 1;// 表示请求成功
	public static final int HTTP_STATUS_REQUESTED = 100;// 表示请求已经发出去(等待数据的返回)
	public static final int HTTP_STATUS_RESPONSED = 101;// 表示请求已经做出相应(已经接收到数据)
	public static final int HTTP_STATUS_HANDLE = 102;// 表示已经对Http相应做出处理(数据已经显示到界面)

}
