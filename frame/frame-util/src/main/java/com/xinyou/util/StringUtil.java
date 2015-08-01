package com.xinyou.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Formatter;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

public class StringUtil {

	/**
	 * 使用URLEncoder编码字符串，并将“+”替换成“%20”
	 * @param str 待编码字符串
	 * @return 编码后的字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String Encode(String str) throws UnsupportedEncodingException {
		if (str == null)
			return null;
		return URLEncoder.encode(str, "UTF-8").replace("+", "%20");
	}

	/**
	 * 将“+”替换成“%2B”，然后使用URLDecoder解码字符串
	 * @param str 待解码字符串
	 * @return 解码后的字符串
	 * @throws IOException
	 */
	public static String Decode(String str) throws IOException {
		if (str == null)
			return null;
		return URLDecoder.decode(str.replace("+", "%2B"), "UTF-8");
	}
	

	/**
	 * 将字符串转换成Unicode字符串
	 * @param input 待处理字符串
	 * @return Unicode字符串
	 */
	public static String encodeUnicode(String input) {
		if (input == null)
			return null;

		StringBuilder b = new StringBuilder(input.length());
		Formatter f = new Formatter(b);
		for (char c : input.toCharArray()) {
			//字母和数字不转义
			if (c > 122 || c < 48 || c > 57 && c < 65 || c > 90 && c < 97) {
				f.format("\\u%04x", (int) c);
			} else {
				b.append(c);
			}
		}
		f.close();
		return b.toString();
	}
	
	/**
	 * 将{@link java.util.Map Map}转换成URL查询字符串
	 * @param map URL<参数-值>对
	 * @return URL查询字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String toUrlSearchString(Map<?,?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        if(map!=null){
	        for (Map.Entry<?,?> entry : map.entrySet()) {
	            if (sb.length() > 0) {
	                sb.append("&");
	            }
	            sb.append(String.format("%s=%s",
	        		URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
	        		URLEncoder.encode(entry.getValue().toString(), "UTF-8")
	            ));
	        }
        }
        return sb.toString();
    }

	/**
	 * 将{@link org.apache.commons.httpclient.NameValuePair NameValuePair}数组转换成URL查询字符串
	 * @param pairs URL<参数-值>对
	 * @return URL查询字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String toUrlSearchString(NameValuePair[] pairs) throws UnsupportedEncodingException {
	    StringBuilder sb = new StringBuilder();
	    if(pairs!=null){
		    for (NameValuePair entry : pairs) {
		        if (sb.length() > 0) {
		            sb.append("&");
		        }
		        sb.append(String.format("%s=%s",
		    		URLEncoder.encode(entry.getName().toString(), "UTF-8"),
		    		URLEncoder.encode(entry.getValue().toString(), "UTF-8")
		        ));
		    }
	    }
	    return sb.toString();
	}

	/**
	 * 将{@link java.io.InputStream InputStream}转换成字符串
	 * @param is {@link java.io.InputStream InputStream}
	 * @param charset 字符编码
	 * @return
	 * @throws IOException
	 */
	public static String toString(InputStream is, String charset) throws IOException {
		if(is==null)
			return null;
		BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
		StringBuffer sb = new StringBuffer();
		String temp = "";
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		return sb.toString();
	}
	
	/**
	 * 将{@link java.io.InputStream InputStream}转换成字符串
	 * @param is {@link java.io.InputStream InputStream}
	 * @return
	 * @throws IOException
	 */
	public static String toString(InputStream is) throws IOException {
		return toString(is, "UTF-8");
	}

	/**
	 * 生成一个长度固定为n的随机数字字符串。
	 * 最大有效长度为16。
	 * @param n 长度
	 * @return
	 */
	public static String randomNumFw(int n){
		if(n<1){
			return "";
		}
		long ln = (long) (Math.random()* Math.pow(10, n));
		
		return String.format("%0"+n+"d", ln);
	}
	/**
	 * 将字符串重复N次输出
	 * @param str 字符串
	 * @param n 字符串重复次数N
	 * @return
	 */
	public static String repeat(String str, int n){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<n;i++){
			sb.append(str);
		}
		return sb.toString();
	}
	/**
	 * 将字符重复N次输出
	 * @param c 字符
	 * @param n 字符串重复次数N
	 * @return
	 */
	public static String repeat(char c, int n){
		StringBuffer sb = new StringBuffer(n);
		for(int i=0;i<n;i++){
			sb.append(c);
		}
		return sb.toString();
	}
}

