package com.xinyou.util;

import java.util.HashMap;
import java.util.Map;

/** 
 * web service 请求返回的对象
 * */
public class BacoResponse<T> {

	private String status;
	private String info;
	private T data;
	private Integer count;
	private Map<String, String> bag;
	
	public void add(String key, String value){
		if(bag==null){
			bag = new HashMap<String, String>();
		}
		bag.put(key, value);
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Map<String, String> getBag() {
		return bag;
	}
	public void setBag(Map<String, String> bag) {
		this.bag = bag;
	}
}
