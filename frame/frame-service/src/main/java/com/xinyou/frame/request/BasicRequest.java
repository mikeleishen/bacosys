package com.xinyou.frame.request;

import java.math.BigDecimal;
import java.util.List;

public class BasicRequest<T,T1> {
	
	  public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getData_char() {
		return data_char;
	}
	public void setData_char(String data_char) {
		this.data_char = data_char;
	}
	public String getData_char2() {
		return data_char2;
	}
	public void setData_char2(String data_char2) {
		this.data_char2 = data_char2;
	}
	public String getData_char3() {
		return data_char3;
	}
	public void setData_char3(String data_char3) {
		this.data_char3 = data_char3;
	}
	public String getData_char4() {
		return data_char4;
	}
	public void setData_char4(String data_char4) {
		this.data_char4 = data_char4;
	}
	public String getData_char5() {
		return data_char5;
	}
	public void setData_char5(String data_char5) {
		this.data_char5 = data_char5;
	}
	public String getData_char6() {
		return data_char6;
	}
	public void setData_char6(String data_char6) {
		this.data_char6 = data_char6;
	}
	public BigDecimal getData_decimal() {
		return data_decimal;
	}
	public void setData_decimal(BigDecimal data_decimal) {
		this.data_decimal = data_decimal;
	}
	public int getData_int() {
		return data_int;
	}
	public void setData_int(int data_int) {
		this.data_int = data_int;
	}
	public int getData_int2() {
		return data_int2;
	}
	public void setData_int2(int data_int2) {
		this.data_int2 = data_int2;
	}
	public long getData_long() {
		return data_long;
	}
	public void setData_long(long data_long) {
		this.data_long = data_long;
	}
	public long getData_long2() {
		return data_long2;
	}
	public void setData_long2(long data_long2) {
		this.data_long2 = data_long2;
	}
	public int getPage_no() {
		return page_no;
	}
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	public T getData_entity() {
		return data_entity;
	}
	public void setData_entity(T data_entity) {
		this.data_entity = data_entity;
	}
	public List<T1> getData_list() {
		return data_list;
	}
	public void setData_list(List<T1> data_list) {
		this.data_list = data_list;
	}
	private String token;
	  private String data_char;
	  private String data_char2;
	  private String data_char3;
	  private String data_char4;
	  private String data_char5;
	  private String data_char6;
	  private BigDecimal data_decimal;
	  private int data_int;
	  private int data_int2;
	  private long data_long;
	  private long data_long2;
	  private int page_no;
	  private int page_size;
	  private T   data_entity;
	  private List<T1> data_list;
	  

}
