package com.xinyou.util;

import java.util.List;
public class DataListEntity<T> {
	private List<T> datalist;
	private Integer count;
	public List<T> getDatalist() {
		return datalist;
	}
	public void setDatalist(List<T> datalist) {
		this.datalist = datalist;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}