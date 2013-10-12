package com.yanghe.sujiu.utils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StrUtil
{
    public static boolean isDate(String sdate)
    {
//    	boolean result = false;
    	//判断 XXXX年XX月XX日
    	if(sdate.contains("年") && sdate.contains("月") && sdate.contains("日"))
    	{
    		int indexY = sdate.indexOf("年");
    		int indexM = sdate.indexOf("月");
    		int indexD = sdate.indexOf("日");
    		
    		String yearStr = sdate.substring(0,indexY);
    		if(!isNumber(yearStr))
    		{
    			return false;
    		}
    		String monthStr = sdate.substring(indexY+1,indexM);
    		if(!isNumber(monthStr))
    		{
    			return false; 
    		}
    		else
    		{
    			if(Integer.valueOf(monthStr) > 12)
    				return false;
    		}
    		
    		String dayStr = sdate.substring(indexM+1,indexD);
    		if(!isNumber(dayStr))
    		{
    			return false;
    		}
    		else
    		{
    			if(Integer.valueOf(monthStr) > 31)//此处不完全，没有判断月份和年份
    				return false;
    		}
    		return true;
    	}
    	
    	//判断 XXXX/XX/XX
    	/*String[] dts = sdate.split("/");
    	if(dts.length == 3)
    	{
    		
    	}*/
    	//判断 XXXX-XX-XX
    	return false;
    }
    
    /**
	 * 日期时间显示两位数的方法
	 * 
	 * @param x
	 *            Utils.format(月+1)
	 * @return
	 */
	public final static String format(int x) {
		String s = "" + x;
		if (s.length() == 1)
			s = "0" + s;
		return s;
	}
    
    
    /**
     * 判断是否为一个正确的坐标字符串 
     * @param location
     * @return
     */
    public static boolean isLocation(String location)
    {
//        String tempStr = null;
        
        //非空
        if(isNull(location))
        {
            return false;
        }
        
        //分割逗号
        String[] lc = location.split(",");//英文逗号
        if(lc.length != 2)
        {
            return false;
        }
        
        //括号开头，结尾
        if(!lc[0].startsWith("("))
        {
            return false;
        }
        if(!lc[1].endsWith(")"))
        {
            return false;
        }
        return true;
    }
    
    
    public static String Replace(String sdate, String string, String string2)
    {
        return null;
    }
    
    /**
     * 
     * 去除某个字符串的前后空格 如果为空字符串 将返回null
     * @param str
     * @return
     *
     */
    public static String strTrim(String str)
    {
        if(!isNull(str))
        {
            return str.trim();
        }
        else
        {
            return null;
        }
        
    }
    
    /**
     * 
     * 判断某个字符串是否为null  空字符串也认为是null
     * @param str
     * @return
     *
     */
    public static boolean isNull(String str)
    {
        if(str == null)
        {
            return true;
        }
        if("".equals(str.trim()))
        {
            return true;
        }
        if("NULL".equals(str.trim().toUpperCase())){
        	return true;
        }
        return false;
    }
    
    /**
     * 判断这个字符串是不是数字字符串,只判断正整数
     * @param num
     * @return
     */
    public static boolean isNumber(String num)
    {
        String numberStr = "0123456789";
        String number = "";
    	for(char c : num.toCharArray())
    	{
    	    number = new Character(c).toString();
    		if(!numberStr.contains(number))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * 判断c是否为字母
     * @param c
     * @return
     */
    public static boolean isZimu(char c)
    {
        String zimu = "abcdefghijklmnopqrstuvwxyz";
        String str = new Character(c).toString().toLowerCase();
        return zimu.contains(str);
    }
    
    
    /**
     * 验证是否为邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email)
    {
        String strPattern = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证手机号码
     * @param mobilesNo
     * @return
     */
    public static boolean isPhoneNum(String mobilesNo)
    {
        //Pattern p = Pattern.compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
        //Pattern p = Pattern.compile("^(1(3|5|8|){1}\\d{9})|(\\d{3,4}-?\\d{8})$");
        //Matcher m = p.matcher(mobilesNo);
        //return m.matches();
    	
    	if(null != mobilesNo && mobilesNo.length() == 11 && mobilesNo.startsWith("1")){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 验证固定电话
     * @return
     */
    public static boolean isFixNumber(String fixNumber){
    	if(!isNumber(fixNumber)){//不能输入非数字字符
    		return false;
    	}
    	if(fixNumber.startsWith("00")){//不能00开头
    		return false;
    	}
    	return true;
    }
    
    /**
     * 专门给callLog用的方法 00:12:32 -> 12 minutes 32 seconds
     * @param time
     * @return
     */
    public static  String getCallTimeFormat(String time)
    {
        if(isNull(time) || time.equals("Unanswered"))
        {
            return time;
        }
        
        StringBuffer sb = new StringBuffer();
        
        if(time.contains("-"))
        {
           time = time.replace("-", "");
        }
        String[] times = time.split(":");
        int hours = Integer.parseInt(times[0]);
        int minutes = Integer.parseInt(times[1]);
        int seconds = Integer.parseInt(times[2]);
        
        
        if(hours > 0 && minutes == 0 && seconds > 0)//避免出现 1 hours 3 seconds这种情况
        {
            minutes++;
            seconds = 0;
        }
        
        if(hours>0)
        {
            sb.append(hours+" hours ");   
        }
        if(minutes>0)
        {
            sb.append(minutes+" minutes ");   
        }
        if(seconds>0)
        {
            sb.append(seconds+" seconds ");   
        }
        
        return sb.toString();
    }
    
    
    public static boolean isYesterday(Date date)
    {
        Date today = new Date();
        if((date.getYear() == today.getYear()) && (date.getMonth() == today.getMonth()))
        {
           if((today.getDay() - date.getDay()) ==1)
           {
               return true;
           }
        }
        return false;
    }
    /**
     * 
     * 获取字符串的长度，中文占两个字符,英文数字一个字符
     * @param value
     * @return
     *
     */
    public static int strlength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
         String temp = value.substring(i, i + 1);
         if (temp.matches(chinese)) {
          valueLength += 2;
         } else {
          valueLength += 1;
         }
        }
        return valueLength;
       }

	public static int getIntBool(String bool) {
		bool = bool.toLowerCase();
		if("true".equals(bool))
			return 1;
		if("false".equals(bool))
		return 0;
		return Integer.parseInt(bool);
	}
	
	public static boolean getBoolInt(String bool) {
		bool = bool.toLowerCase();
		if("1".equals(bool))
			return true;
		if("0".equals(bool))
		return false;
		return Boolean.parseBoolean(bool);
	}
}
