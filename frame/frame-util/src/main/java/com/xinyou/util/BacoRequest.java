package com.xinyou.util;

/** web service 请求的参数类 */
public class BacoRequest<T> {
	
	public BacoRequest(){
	}
	
	public BacoRequest(T data){
		this();
		this.data=data;
	}
	private T data;
	private String memtoken;
	private String shoptoken;
	private String token;
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getMemtoken() {
		return memtoken;
	}
	public void setMemtoken(String memtoken) {
		this.memtoken = memtoken;
	}
	public String getShoptoken() {
		return shoptoken;
	}
	
	public void setShoptoken(String shoptoken) {
		this.shoptoken = shoptoken;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}

